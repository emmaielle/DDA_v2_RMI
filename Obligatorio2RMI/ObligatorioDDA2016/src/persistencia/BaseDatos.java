/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Moi
 */
public class BaseDatos {
   private Connection conexion;
    private Statement stmt;
    private static BaseDatos instancia = new BaseDatos(); 

    public static BaseDatos getInstancia() {
        return instancia;
    }
    private BaseDatos() {
    }
    public void conectar(String url,String user,String pass){
        try {
            conexion = DriverManager.getConnection(url, user, pass);
            stmt = conexion.createStatement();
        } catch (SQLException ex) {
            System.out.println("Error al conectar:" + ex.getMessage());
        }
    }
    public void desconectar(){
        try {
            stmt.close();
            conexion.close();
        } catch (SQLException ex) {
            
        }
    }
    public int modificar(String sql){
        try {
            return stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println("Error al modificar:" + ex.getMessage());
            System.out.println("SQL:" + sql);
            return -1;
        }
    }
    public ResultSet consultar(String sql){
        try {
            return stmt.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println("Error al consultar:" + ex.getMessage());
            System.out.println("SQL:" + sql);
            return null;
        }
    }
    public int proximoOid(){
        int oid=-1;
        ResultSet rs = consultar("SELECT valor from parametros where nombre='oid'");
        try {
            if(rs.next()){
                oid = rs.getInt("valor");
                rs.close();
                modificar("UPDATE parametros set valor=" + (oid+1) + " where nombre='oid'");
            }
        } catch (SQLException ex) {
                System.out.println("Error al obtener proximo oid");
                return oid;
        }
        return oid;
    }
    ///// Metodos especificos para persistencia
    
    public void guardar(Persistente p){
        if(p.getOid()==0) insertar(p);
        else actualizar(p);
    }

    private void insertar(Persistente p) {
        int oid = proximoOid();
        p.setOid(oid);
        ArrayList<String> sqls = p.getSqlInsert();
        if(!transaccion(sqls)){
            p.setOid(0);
        }
    }
    public boolean transaccion(ArrayList<String> sqls){
        for(String sql:sqls){
            modificar(sql);
        }
        return true;
    }
    private void actualizar(Persistente p) {
        ArrayList<String> sqls = p.getSqlUpdate();
        transaccion(sqls);
    }
    public void eliminar(Persistente p){
        ArrayList<String> sqls= p.getSqlDelete();
        if(transaccion(sqls)){
            p.setOid(0);
        }
    }
    public void restaurar(Persistente p){
        String sql = p.getSqlSelect();
        ResultSet rs = consultar(sql);
        try {
            if(rs.next()){
               p.leer(rs);
            }
        } catch (SQLException ex) {
            System.out.println("Error al restaurar:"+ ex.getMessage());
        }
    }
    
    public ArrayList<Object> obtenerTodos(Persistente p){
        return consultar(p,"");
    }
    public ArrayList<Object> consultar(Persistente p,String where){
        String sql = p.getSqlSelect() + " " + where;
        ResultSet rs = consultar(sql);
        ArrayList<Object> resultado = new ArrayList();
        int oid, oidAnt=-1;
        try {
            while(rs.next()){
                oid=rs.getInt("oid");
                if(oid!=oidAnt){
                    p.crearNuevo();
                    p.setOid(oid);        
                    resultado.add(p.getObjeto());
                    oidAnt=oid;
                }
                p.leer(rs);
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener todos:" + ex.getMessage());
        }
        return resultado;
    }
}
