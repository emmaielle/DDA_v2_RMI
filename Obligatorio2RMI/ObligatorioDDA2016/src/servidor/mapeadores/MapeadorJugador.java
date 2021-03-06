/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor.mapeadores;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidor.modelo.Jugador;
import servidor.modelo.Ronda;
import servidor.persistencia.Persistente;

/**
 *
 * @author Euge
 */
public class MapeadorJugador implements Persistente {
    Jugador j;

    public MapeadorJugador() {
    }

    public MapeadorJugador(Jugador j) {
        this.j = j;
    }

    public void setJ(Jugador j) {
        this.j = j;
    }
    
    @Override
    public int getOid() {
        if(j==null) return 0;
        else return j.getOid();
    }

    @Override
    public void setOid(int oid) {
        j.setOid(oid);
    }
    
    @Override
    public ArrayList<String> getSqlInsert() {
        ArrayList<String> sqls = new ArrayList();
        sqls.add(
            "INSERT INTO usuario (oid,nombre,password,saldo,nombreUsuario) VALUES " +
              "(" + getOid() + ",'" + j.getNombre()+  
              "','"  +j.getPassword()+"',"+j.getSaldo()+",'"+j.getNombreCompleto()+"')");
        return sqls;
    }

    @Override
    public ArrayList<String> getSqlUpdate() {
        ArrayList<String> sqls = new ArrayList();
        sqls.add("UPDATE usuario set nombre='" + j.getNombre() + "', password='" + j.getPassword()+"',saldo="+j.getSaldo()+ ",nombreUsuario='"+j.getNombreCompleto()+"' WHERE oid = " + j.getOid());
        return sqls;
    }
    
    @Override
    public ArrayList<String> getSqlDelete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getSqlSelect() {
        String sql =
//                ,SUM(monto) apostado, SUM(montoGanado) cobrado
        "SELECT * FROM usuario u LEFT JOIN apuesta a ON u.oid=a.oidJugador "
                + "LEFT JOIN ronda r ON a.oidRonda=r.oid";
        if(j!=null) sql+= " where u.oid=" + getOid();
        sql+=" ORDER BY u.oid,r.oid";
        return sql;
    }

    @Override
    public void leer(ResultSet rs) {
        try {
            if(j.getNombre()==null){
                j.setOid(rs.getInt("oid"));
                j.setNombre(rs.getString("nombre"));
                j.setPassword(rs.getString("password"));
                j.setNombreCompleto(rs.getString("nombreUsuario"));
                j.setSaldo(rs.getInt("saldo"));
            }
            int oidRonda = rs.getInt("oidRonda");
            if (oidRonda != 0){
                String num = rs.getString("numero");
                int monto = rs.getInt("monto");
                int ganado = rs.getInt("montoGanado");
                Date fecha = new Timestamp(rs.getTimestamp("fechaHoraCreacion").getTime());
                Ronda r;
                    r = new Ronda();
                r.setOid(oidRonda);
                j.agregarApuesta(num,monto,r,ganado, fecha);
            } 
        } catch (SQLException ex) {
            System.out.println("Error al leer usuario:" + ex.getMessage());
        }
         catch (RemoteException ex) {
            Logger.getLogger(MapeadorJugador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void crearNuevo() {
        j = new Jugador();
    }

    @Override
    public Object getObjeto() {
        return j;
    }
    
}
