/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Euge
 */
public class JugadorRuleta {
    private Color color;
    private Mesa mesa;
    private Jugador jugador;
    private boolean apostado;
    private int rondasSinApostar;
    private int rondasSinApostarAnterior;
    
    // <editor-fold defaultstate="collapsed" desc="Constructor">       
    public JugadorRuleta(Color color, Mesa mesa, Jugador jugador) {
        this.color = color;
        this.mesa = mesa;
        this.jugador = jugador;
        this.apostado = false;
    }
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters y setters"> 
    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public int getRondasSinApostarAnterior() {
        return rondasSinApostarAnterior;
    }

    public void setRondasSinApostarAnterior(int rondasSinApostarAnterior) {
        this.rondasSinApostarAnterior = rondasSinApostarAnterior;
    }
    
    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setApostado(boolean apostado) {
        this.apostado = apostado;
    }

    public boolean isApostado() {
        return apostado;
    }

    public int getRondasSinApostar() {
        return rondasSinApostar;
    }

    public void setRondasSinApostar(int rondasSinApostar) {
        this.rondasSinApostar = rondasSinApostar;
    }
    
    
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Metodos">
    public void agregarApuesta(Apuesta a){
        if(a!=null) jugador.getApuestas().add(a);
    }

    public void quitarApuesta(Apuesta a){
        if (jugador.getApuestas().contains(a)) jugador.getApuestas().remove(a);
    }

    public boolean expulsado() {
        return jugador.getSaldo() == 0;
    }
    
    public boolean sinApostarTresVeces(){
        return this.rondasSinApostar > 2;
    }
    
    // </editor-fold>


}
