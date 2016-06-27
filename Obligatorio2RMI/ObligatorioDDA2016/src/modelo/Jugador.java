/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.awt.Color;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Euge
 */
public class Jugador {
    private int oid;
    private String nombre;
    private String password;
    private String nombreCompleto;
    private long saldo;
    private long totalCobrado;
    private long totalApostado;
    private boolean enJuego;
    private boolean enMesa;
    private boolean statsOn;
    private boolean apuestasOn;
    private ArrayList<Apuesta> apuestas = new ArrayList<>();
    
    // <editor-fold defaultstate="collapsed" desc="Constructor">   
    
    public Jugador(String nombre, String password, String nombreCompleto, int saldo) {
        this.nombre = nombre;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.saldo = saldo;
    }
    //agregue para la persistencia ver....
    public Jugador() {
    }
    
    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Getters y setters"> 
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isStatsOn() {
        return statsOn;
    }

    public void setStatsOn(boolean statsOn) {
        try {
            this.statsOn = statsOn;
            new Modelo().notificar(Modelo.EVENTO_STATSWINDOW);
        } catch (RemoteException ex) {
            Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean isEnMesa() {
        return enMesa;
    }

    public void setEnMesa(boolean enMesa) {
        this.enMesa = enMesa;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnJuego() {
        return enJuego;
    }

    public void setEnJuego(boolean enJuego) {
        try {
            this.enJuego = enJuego;
            new Modelo().notificar(Modelo.EVENTO_JUEGO_CERRADO);
        } catch (RemoteException ex) {
            Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public long getSaldo() {
        return saldo;
    }

    //agregue para la persistencia... ver
    public void setSaldo(long saldo) {
        this.saldo = saldo;
    }

    public long getTotalCobrado() {
        return totalCobrado;
    }

    public void setTotalCobrado(long totalCobrado) {
        this.totalCobrado = totalCobrado;
    }

    public void setTotalApostado(long totalApostado) {
        this.totalApostado = totalApostado;
    }
    
    public long getTotalApostado() {
        return totalApostado;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }
    public ArrayList<Apuesta> getApuestas() {
        return apuestas;
    }

    public boolean isApuestasOn() {
        return apuestasOn;
    }

    public void setApuestasOn(boolean apuestasOn) {
        try {
            this.apuestasOn = apuestasOn;
            new Modelo().notificar(Modelo.EVENTO_APUESTASWINDOW);
        } catch (RemoteException ex) {
            Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Metodos"> 
    @Override
    public String toString(){
        return getNombreCompleto();
    }

    public void modificarSaldo(boolean agregar, int monto) {
        // a esta altura, ya se verifico que la cantidad restada no llegue a ser menor que lo 
        // que actualmente tiene, porque sino no se haria la apuesta
        if (agregar){
            saldo += monto;
        }
        else {
            saldo -= monto;
        }
    }
    
    // </editor-fold>

    public void agregar(String string, int aInt, int aInt0, Date date, String string0, int aInt1,int montGan) {
        Ronda r = new Ronda(aInt0, new Mesa(string0));
        r.setNroGanador(new Numero(aInt1));
        if(string.contains("Pleno")){
            ApuestaPleno a= new ApuestaPleno(aInt, new JugadorRuleta(Color.yellow, null, this), string, null, r, date);
            a.setMontoGanado(montGan);            
            apuestas.add(a);
        }
        if(string.contains("Docena")){
            ApuestaDocena b=new ApuestaDocena(aInt1, new JugadorRuleta(Color.yellow, null, this), string, r, date);
            b.setMontoGanado(montGan);
            //r.agregar(b);
            apuestas.add(b);
        }
        if(string.contains("Color")){
            ApuestaColor c=new ApuestaColor(aInt1, new JugadorRuleta(Color.yellow, null, this), string, r, date);
            c.setMontoGanado(montGan);
            //r.agregar(c);
            apuestas.add(c);
        }
        //no anda así hay q buscar otra solucion
        for(Apuesta a:apuestas){
            if(a.getRonda()==r)
                r.setApuestas(apuestas);
        }
        
    }



}
