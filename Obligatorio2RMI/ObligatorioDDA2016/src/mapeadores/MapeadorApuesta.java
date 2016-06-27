/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapeadores;

import java.sql.ResultSet;
import java.util.ArrayList;
import modelo.Apuesta;
import persistencia.Persistente;

/**
 *
 * @author Euge
 */
public class MapeadorApuesta implements Persistente{
    Apuesta a;

    public MapeadorApuesta() {
    }

    public MapeadorApuesta(Apuesta a) {
        this.a = a;
    }

    public void setA(Apuesta a) {
        this.a = a;
    }
    
    @Override
    public int getOid() {
        if(a==null) return 0;
        else return a.getOid();
    }

    @Override
    public void setOid(int oid) {
        a.setOid(oid);
    }

    @Override
    public ArrayList<String> getSqlInsert() {
        ArrayList<String> sqls = new ArrayList();
        sqls.add(
                "INSERT INTO apuesta (oid,numero,monto,oidjugador,oidronda) VALUES " +
                  "(" + getOid() + "," + a.getNumero()+  
                  "," +a.getMonto()+","+a.getJugador().getJugador().getOid()+","+a.getRonda().getOid()+")");
        return sqls;
    }

    @Override
    public ArrayList<String> getSqlUpdate() {
        ArrayList<String> sqls = new ArrayList();
        sqls.add("UPDATE apuesta set numero=" + a.getNumero()+ ", monto=" + a.getMonto()+",oidjugador="+a.getJugador().getJugador().getOid()+ ",oidronda="+a.getRonda().getOid()+"WHERE oid = " + a.getOid());
        return sqls;
    }

    @Override
    public ArrayList<String> getSqlDelete() {
        ArrayList<String> sqls = new ArrayList();
        sqls.add(
             "DELETE FROM apuesta WHERE oid=" + a.getOid());
        return sqls;
    }

    @Override
    public String getSqlSelect() {
        String sql = "SELECT * FROM apuesta";
        if(a!=null) sql+= " where oid=" + getOid();
        return sql;
    }

    @Override
    public void leer(ResultSet rs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void crearNuevo() {
        //a = new Apuesta();
    }

    @Override
    public Object getObjeto() {
        return a;
    }
    
}
