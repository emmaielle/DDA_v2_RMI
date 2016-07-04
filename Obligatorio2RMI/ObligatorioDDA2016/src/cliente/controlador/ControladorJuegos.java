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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidor.modelo.Jugador;
import servidor.modelo.Modelo;
import servidor.observadorRemoto.ObservableRemoto;
import servidor.observadorRemoto.ObservadorRemoto;
import servidor.modelo.ModeloRemoto;

/**
 *
 * @author Moi
 */
public class ControladorJuegos extends UnicastRemoteObject implements ObservadorRemoto {

    private ModeloRemoto modelo;
    private VistaJuegos vista;
    private Jugador jugador;
    
    public ControladorJuegos(VistaJuegos v ,Jugador j)throws RemoteException{
        try {
            this.vista = v;
            //System.setProperty("java.rmi.server.hostname","186.54.152.199");
            this.modelo=(ModeloRemoto)Naming.lookup("rmi://localhost/modelo");
            jugador = j;
            modelo.agregar(this);
        } catch (NotBoundException | MalformedURLException ex) {
            Logger.getLogger(ControladorJuegos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void listarJuegos(){
        try {
            ArrayList<String> juegos = modelo.getJuegos();
            vista.mostrarJuegos(juegos);
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorJuegos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ingresarAjuego(String j){
        Object juego;
        try {
            juego = modelo.getJuego(j);
            vista.abrirJuego(juego);
            jugador.setEnJuego(true);
            vista.habilitarIrAJuego(false);
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorJuegos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void logout(){
        try {
            modelo.logout(jugador);
            jugador.setEnJuego(false);
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorJuegos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarObservador() {
        try {
            modelo.quitar(this);
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorJuegos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actualizar(ObservableRemoto origen, Serializable param) throws RemoteException {
        if (param.equals(Modelo.EVENTO_JUEGO_CERRADO)){
            jugador = modelo.getJugador(jugador.getOid());
            if(!jugador.isEnJuego()) vista.habilitarIrAJuego(true);
        }
        else if (param.equals(Modelo.EVENTO_NUEVA_MESA) || (param.equals(Modelo.EVENTO_SALIR_MESA))){
            this.listarJuegos();
        }
        else if (param.equals(Modelo.EVENTO_STATSWINDOW)){
            jugador = modelo.getJugador(jugador.getOid());
            if(jugador.isStatsOn()) {
                vista.habilitarEstadisticas(false);
            }
            else vista.habilitarEstadisticas(true);
        }
    }
}
