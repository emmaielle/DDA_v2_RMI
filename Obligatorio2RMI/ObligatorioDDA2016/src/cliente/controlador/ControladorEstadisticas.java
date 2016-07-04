/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.controlador;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidor.modelo.Jugador;
import servidor.modelo.Modelo;
import servidor.observadorRemoto.ObservableRemoto;
import servidor.observadorRemoto.ObservadorRemoto;
import servidor.modelo.ModeloRemoto;

/**
 *
 * @author Euge
 */
public class ControladorEstadisticas extends UnicastRemoteObject implements ObservadorRemoto{
    public ModeloRemoto modelo;
    public VistaEstadisticas vista;
    public Jugador jugador;

    public ControladorEstadisticas(VistaEstadisticas vista, Jugador jugador) throws RemoteException{
        try {
            this.vista = vista;
            //System.setProperty("java.rmi.server.hostname","186.54.152.199");
            this.modelo = (ModeloRemoto)Naming.lookup("rmi://localhost/modelo");
            this.jugador = jugador;
            modelo.agregar(this);
        } catch (NotBoundException | MalformedURLException ex) {
            Logger.getLogger(ControladorEstadisticas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrarTotalApostadoTodos(){
        try {
            vista.mostrarTodosApostado(modelo.totalApostadoTodos());
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorEstadisticas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void mostrarTotalCobradoTodos() {
        try {
            vista.mostrarTodosCobrado(modelo.totalCobradoTodos());
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorEstadisticas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void mostrarTotalApostado() {
        vista.mostrarTotalApostado(jugador.getTotalApostado());
    }

    public void mostrarTotalCobrado() {
        vista.mostrarTotalCobrado(jugador.getTotalCobrado());
    }

    public void habilitarStats(boolean habilitar) throws RemoteException {
        modelo.setStatsOn(jugador, habilitar);
    }

    public void eliminarObservador() {
        try {
            modelo.quitar(this);
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorEstadisticas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actualizar(ObservableRemoto origen, Serializable param) throws RemoteException {
        if(param.equals(Modelo.EVENTO_ACTUALIZA_SALDOS)){
            vista.mostrarTodosApostado(modelo.totalApostadoTodos());
            vista.mostrarTodosCobrado(modelo.totalCobradoTodos());
            vista.mostrarTotalApostado(jugador.getTotalApostado());
            vista.mostrarTotalCobrado(jugador.getTotalCobrado());
        }
    }
}
