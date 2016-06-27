/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import modelo.Apuesta;

/**
 *
 * @author Moi
 */
public interface VistaApuestas {
    public void mostrarApuestas(ArrayList<Apuesta> apuestas);
    public void mostrarApuestasPorRonda(ArrayList<Apuesta> apuestas);
    public void salirDeApuestas();
}
