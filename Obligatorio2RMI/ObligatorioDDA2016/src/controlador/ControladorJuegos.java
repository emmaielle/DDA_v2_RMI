/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.IModelo;
import modelo.Jugador;
import modelo.Modelo;
import observadorRemoto.ObservableRemoto;
import observadorRemoto.ObservadorRemoto;

/**
 *
 * @author Moi
 */
public class ControladorJuegos extends UnicastRemoteObject implements ObservadorRemoto {

    private IModelo modelo;
    private VistaJuegos vista;
    private Jugador jugador;
    
    public ControladorJuegos(VistaJuegos v ,Jugador j)throws RemoteException{
        try {
            this.vista = v;
            this.modelo=(Modelo)Naming.lookup("rmi://localhost/modelo");
            modelo.agregar(this);
            jugador = j;
        } catch (NotBoundException ex) {
            Logger.getLogger(ControladorJuegos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ControladorJuegos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void listarJuegos(){
        ArrayList<String> juegos = modelo.getJuegos();
        vista.mostrarJuegos(juegos);
    }
    
    public void ingresarAjuego(String j){
        Object juego = modelo.getJuego(j);
        vista.abrirJuego(juego);
        jugador.setEnJuego(true);
        vista.habilitarIrAJuego(false);
    }
    
    public void logout(){
        modelo.logout(jugador);
        jugador.setEnJuego(false);
    }

//    public void eliminarObservador() {
//        modelo.deleteObserver(this);
//    }

    @Override
    public void actualizar(ObservableRemoto origen, Serializable param) throws RemoteException {
        if (param.equals(Modelo.EVENTO_JUEGO_CERRADO)){
            if(!jugador.isEnJuego()) vista.habilitarIrAJuego(true);
        }
        else if (param.equals(Modelo.EVENTO_STATSWINDOW)){
            if(jugador.isStatsOn()) vista.habilitarEstadisticas(false);
            else vista.habilitarEstadisticas(true);
        }
        else if (param.equals(Modelo.EVENTO_NUEVA_MESA)){
            this.listarJuegos();
        }
    }
}
