/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import exceptions.InvalidUserActionException;
import java.awt.Color;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Euge
 */
public class JugadorRuleta implements Serializable, TipoJugador{
    private Color color;
    private transient Mesa mesa;
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


    @Override
    public void expulsado() throws InvalidUserActionException {
        if (jugador.getSaldo() == 0) throw new InvalidUserActionException("Se le acabo el saldo");
        
    }
    
    @Override
    public void sinApostarTresVeces() throws InvalidUserActionException {
        if (this.rondasSinApostar > 2) throw new InvalidUserActionException("Ha pasado 3 rondas sin apostar");
    }
    
    
    
    // </editor-fold>

    @Override
    public boolean equals(Object obj) {
        return ((JugadorRuleta)obj).getJugador().equals(this.getJugador());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.jugador);
        return hash;
    }


}
