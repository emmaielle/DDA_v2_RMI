/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;

/**
 *
 * @author Moi
 */
public interface VistaJuegos {
    
    public void mostrarJuegos(ArrayList<String> juegos);
    public void abrirJuego(Object juego);
    public void habilitarIrAJuego(boolean habilitar);
    public void mostrarEstadisticas();
    public void logout();
    public void habilitarEstadisticas(boolean b);

}
