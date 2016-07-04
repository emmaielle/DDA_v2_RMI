/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.controlador;

import servidor.exceptions.InvalidUserActionException;
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
public class ControladorLogin extends UnicastRemoteObject implements ObservadorRemoto{
    
    private ModeloRemoto modelo;
    private VistaLogin vista;
    
    public ControladorLogin(VistaLogin vista) throws RemoteException{
        try {
            this.vista = vista;
            //System.setProperty("java.rmi.server.hostname","186.54.152.199");
            this.modelo=(ModeloRemoto)Naming.lookup("rmi://localhost/modelo");
            modelo.agregar(this);
            vista.habilitar(modelo.isHabilitado());
        } catch (NotBoundException | MalformedURLException ex) {
            Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void login(String usr,String pass) throws InvalidUserActionException{ 
        Jugador j;
        try {
            j = modelo.login(usr, pass);
            if(j == null){
                vista.errorLogin();
            }
            else{
                vista.ingresarJugador(j);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarObservador() {
        try {
            modelo.quitar(this);
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actualizar(ObservableRemoto origen, Serializable param) throws RemoteException {
        if(param.equals(Modelo.EVENTO_LOGIN)){
            vista.habilitar(modelo.isHabilitado());
        }
    }
}
