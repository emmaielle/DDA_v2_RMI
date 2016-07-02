/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapeadores;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Apuesta;
import modelo.Mesa;
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
                "INSERT INTO ronda (oid,numRonda,fechaYhoraFin,nomMesa,nroSorteado) VALUES " +
                  "(" + getOid() + "," + r.getNroRonda() + ",'" + new Timestamp(r.getFechaYhoraFin().getTime()) + "','" 
                        + r.getMesa().getNombre()+
                  "'," + r.getNroGanador().getValor() + ")");
        agregarApuestas(sqls);
        return sqls;
    }

    @Override
    public ArrayList<String> getSqlUpdate() {
        ArrayList<String> sqls = new ArrayList();
        sqls.add(
             "UPDATE ronda set fechaYhoraFin='" + new Timestamp(r.getFechaYhoraFin().getTime()) + "'"  +
               ", nomMesa='" + r.getMesa().getNombre() +"', nroSorteado=" + r.getNroGanador().getValor() +
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
         String sql = "SELECT * FROM ronda r"; //, apuesta a where r.oid = a.oidRonda
        if(r!=null) sql+= " where oid=" + getOid();
        return sql;
    }

    @Override
    public void leer(ResultSet rs) {
         try {
            if (r.getOid() != 0){
                r.setOid(rs.getInt("oid"));
                r.setFechaYhoraFin(new Date(rs.getTimestamp("fechaYhoraFin").getTime()));
                r.setMesa(new Mesa());
                r.getMesa().setNombre(rs.getString("nomMesa"));
                int valor = rs.getInt("nroSorteado");
                r.setNroGanador(new Numero(valor));
            }
        } catch (SQLException ex) {
            System.out.println("Error al leer la ronda:" + ex.getMessage());
        } catch (RemoteException ex) {
            Logger.getLogger(MapeadorRonda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void crearNuevo() {
        try {
            r = new Ronda();
        } catch (RemoteException ex) {
            Logger.getLogger(MapeadorRonda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Object getObjeto() {
        return r;
    }

    private void agregarApuestas(ArrayList<String> sqls) {
        ArrayList<Apuesta> apuestas = r.getApuestas();
        for(Apuesta a:apuestas){
            sqls.add("INSERT INTO apuesta (oidRonda,fechaHoraCreacion,numero,monto,montoGanado,oidJugador) values ("+getOid()+",'"
                    + new Timestamp(a.getFechaHora().getTime()) + "','"+ a.getNumero() + "',"+a.getMonto()+","+a.getMontoGanado()+
                    ","+a.getJugador().getOid()+")");
        }
    }
    
}
