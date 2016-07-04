/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor.modelo;

import servidor.exceptions.InvalidUserActionException;
import java.awt.Color;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Euge
 */
public class JugadorRuleta implements Serializable, TipoJugador{
    private Color color;
    private MesaRemoto mesa;
    private Jugador jugador;
    private boolean apostado;
    private int rondasSinApostar;
    private int rondasSinApostarAnterior;
    
    // <editor-fold defaultstate="collapsed" desc="Constructor">       
    public JugadorRuleta(Color color, MesaRemoto mesa, Jugador jugador) {
        this.color = color;
        this.mesa = mesa;
        this.jugador = jugador;
        this.apostado = false;
    }
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters y setters"> 
    @Override
    public Jugador getJugador() {
        return jugador;
    }

    @Override
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    @Override
    public int getRondasSinApostarAnterior() {
        return rondasSinApostarAnterior;
    }

    @Override
    public void setRondasSinApostarAnterior(int rondasSinApostarAnterior) {
        this.rondasSinApostarAnterior = rondasSinApostarAnterior;
    }
    
    @Override
    public MesaRemoto getMesa() {
        return mesa;
    }

    @Override
    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }
    
    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void setApostado(boolean apostado) {
        this.apostado = apostado;
    }

    @Override
    public boolean isApostado() {
        return apostado;
    }

    @Override
    public int getRondasSinApostar() {
        return rondasSinApostar;
    }

    @Override
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
    
    // </editor-fold>

}
