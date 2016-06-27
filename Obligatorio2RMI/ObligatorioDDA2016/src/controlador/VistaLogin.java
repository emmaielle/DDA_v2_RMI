/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.Jugador;

/**
 *
 * @author Euge
 */
public interface VistaLogin {
    public void errorLogin();
    public void ingresarJugador(Jugador j);
    public void habilitar(boolean b);
    public void finalizar();
    public void login();
}
