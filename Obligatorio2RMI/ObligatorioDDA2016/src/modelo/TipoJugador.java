/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import exceptions.InvalidUserActionException;
import java.awt.Color;

/**
 *
 * @author Moi
 */
public interface TipoJugador {
    public void setMesa(Mesa mesa);
    public Jugador getJugador();
    public void setJugador(Jugador jugador);
    public int getRondasSinApostarAnterior();
    public void setRondasSinApostarAnterior(int rondasSinApostarAnterior);
    public MesaRemoto getMesa();
    public Color getColor();
    public void setColor(Color color);
    public void setApostado(boolean apostado);
    public boolean isApostado();
    public int getRondasSinApostar();
    public void setRondasSinApostar(int rondasSinApostar);
    public void expulsado() throws InvalidUserActionException;
    public void sinApostarTresVeces() throws InvalidUserActionException;
}
