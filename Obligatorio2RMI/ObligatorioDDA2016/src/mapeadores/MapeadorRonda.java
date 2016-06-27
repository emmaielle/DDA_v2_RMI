/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapeadores;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import modelo.Apuesta;
import modelo.Numero;
import modelo.Ronda;
import persistencia.Persistente;

/**
 *
 * @author Euge
 */
public class MapeadorRonda implements Persistente{
    Ronda r;

    public MapeadorRonda() {
    }

    public MapeadorRonda(Ronda r) {
        this.r = r;
    }

    public void setR(Ronda r) {
        this.r = r;
    }
    
    
    @Override
    public int getOid() {
        if(r==null) return 0;
        else return r.getOid();
    }

    @Override
    public void setOid(int oid) {
        r.setOid(oid);
    }

    @Override
    public ArrayList<String> getSqlInsert() {
        ArrayList<String> sqls = new ArrayList();
        sqls.add(
                "INSERT INTO ronda (oid,fechaYhoraFin,nomMesa,nroSorteado) VALUES " +
                  "(" + getOid() + ",'" + new Timestamp(r.getFechaYhoraFin().getTime()) + "','" + r.getMesa().getNombre()+
                  "'," + r.getNroGanador().getValor() + ")");
        agregarApuestas(sqls);
        return sqls;
    }

    @Override
    public ArrayList<String> getSqlUpdate() {
        ArrayList<String> sqls = new ArrayList();
        sqls.add(
             "UPDATE ronda set fechaYhoraFin='" + new Timestamp(r.getFechaYhoraFin().getTime()) + "'"  +
               ", nomMesa='" + r.getMesa().getNombre() +"', nroSorteado=" + r.getNroGanador() +
               " WHERE oid = " + r.getOid());
        return sqls;
    }

    @Override
    public ArrayList<String> getSqlDelete() {
        ArrayList<String> sqls = new ArrayList();
        sqls.add(
             "DELETE FROM ronda WHERE oid=" + r.getOid());
        return sqls;
    }

    @Override
    public String getSqlSelect() {
        String sql = "SELECT * FROM ronda";
        if(r!=null) sql+= " where oid=" + getOid();
        return sql;
    }

    @Override
    public void leer(ResultSet rs) {
        try {
            r.setOid(rs.getInt("oid"));
            r.setFechaYhoraFin(new Date(rs.getTimestamp("fechaYhoraFin").getTime()));
            r.getMesa().setNombre(rs.getString("nomMesa")); // esto va a estar mal creo
            int valor = Integer.getInteger(rs.getString("nroGanador"));
            r.setNroGanador(new Numero(valor));
        } catch (SQLException ex) {
            System.out.println("Error al leer la ronda:" + ex.getMessage());
        }
    }

    @Override
    public void crearNuevo() {
        //r = new Ronda(numRonda, null);
    }

    @Override
    public Object getObjeto() {
        return r;
    }

    private void agregarApuestas(ArrayList<String> sqls) {
        ArrayList<Apuesta> apuestas = r.getApuestas();
        for(Apuesta a:apuestas){
            sqls.add("INSERT INTO apuesta (oidRonda,numero,monto,montoGanado,oidJugador) values ("+getOid()+",'"
                    +a.getNumero()+"',"+a.getMonto()+","+a.getMontoGanado()+","+a.getJugador().getJugador().getOid()+")");
        }
    }
    
}
