/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.controlador;

import java.util.ArrayList;
import servidor.modelo.Jugador;
import servidor.modelo.MesaRemoto;

/**
 *
 * @author Euge
 */
public interface VistaListaMesas {
    public void mostrar(ArrayList<MesaRemoto> lista);
    public void abrirMesa(MesaRemoto m,Jugador jr, boolean enEspera);
    public void salirDeJuego();
    public void errorCrearMesa(String msg);
    public void ingresarMesa();
    public void crearMesa();
    public void eliminarObservador();
    public void verApuestas(Jugador j);
    public void habilitarApuestas(boolean b);
}
