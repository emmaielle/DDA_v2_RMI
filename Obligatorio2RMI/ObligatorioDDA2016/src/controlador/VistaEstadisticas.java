/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

/**
 *
 * @author Euge
 */
public interface VistaEstadisticas {
    
    public void mostrarTotalApostado(long m);
    public void mostrarTotalCobrado(long m);
    public void mostrarTodosApostado(long m);
    public void mostrarTodosCobrado(long m);
    public void habilitarBotonStats(boolean habilitar);
    public void eliminarObservador();
}
