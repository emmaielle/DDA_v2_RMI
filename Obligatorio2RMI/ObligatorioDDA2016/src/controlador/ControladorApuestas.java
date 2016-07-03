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
import modelo.Apuesta;
import modelo.Jugador;
import modelo.Modelo;
import observadorRemoto.ObservableRemoto;
import observadorRemoto.ObservadorRemoto;
import modelo.ModeloRemoto;

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
            //System.setProperty("java.rmi.server.hostname","179.25.157.190");
            this.modelo=(ModeloRemoto)Naming.lookup("rmi://localhost/modelo");
            this.j = j;
            modelo.agregar(this);
        } catch (NotBoundException | MalformedURLException ex) {
            Logger.getLogger(ControladorApuestas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Apuesta> cargarApuestas() {
        return j.getApuestas();
    }

    public ArrayList<Apuesta> cargarApuestasPorRonda(Apuesta a) {
        return a.getRonda().getApuestas();
    }

    public void salirDeApuestas() {
        try {
            j.setApuestasOn(false);
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
