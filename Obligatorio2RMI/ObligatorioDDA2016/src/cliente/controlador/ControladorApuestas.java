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
import servidor.modelo.Apuesta;
import servidor.modelo.Jugador;
import servidor.modelo.Modelo;
import servidor.observadorRemoto.ObservableRemoto;
import servidor.observadorRemoto.ObservadorRemoto;
import servidor.modelo.ModeloRemoto;

/**
 *
 * @author Moi
 */
public class ControladorApuestas extends UnicastRemoteObject implements ObservadorRemoto{
    
    private ModeloRemoto modelo;
    private VistaApuestas vista;
    private Jugador j;

    public ControladorApuestas(VistaApuestas vista, Jugador j) throws RemoteException {
        try {
            this.vista = vista;
            //System.setProperty("java.rmi.server.hostname","186.54.152.199");
            this.modelo=(ModeloRemoto)Naming.lookup("rmi://localhost/modelo");
            this.j = j;
            modelo.agregar(this);
        } catch (NotBoundException | MalformedURLException ex) {
            Logger.getLogger(ControladorApuestas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Apuesta> cargarApuestas() {
        try {
            return modelo.getApuestas(j);
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorApuestas.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ArrayList<Apuesta> cargarApuestasPorRonda(Apuesta a) {
        return a.getRonda().getApuestas();
    }

    public void salirDeApuestas() {
        try {
            modelo.setApuestasOn(j, false);
            modelo.quitar(this);
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorApuestas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actualizar(ObservableRemoto origen, Serializable param) throws RemoteException {
        if(param.equals(Modelo.EVENTO_SORTEARNUMERO)){  
            vista.mostrarApuestas(cargarApuestas());
        }
    }
    
    
    
}
