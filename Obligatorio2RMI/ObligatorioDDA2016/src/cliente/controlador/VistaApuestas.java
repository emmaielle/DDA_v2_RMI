/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.controlador;

import java.util.ArrayList;
import servidor.modelo.Apuesta;

/**
 *
 * @author Moi
 */
public interface VistaApuestas {
    public void mostrarApuestas(ArrayList<Apuesta> apuestas);
    public void mostrarApuestasPorRonda(ArrayList<Apuesta> apuestas);
    public void salirDeApuestas();
}
