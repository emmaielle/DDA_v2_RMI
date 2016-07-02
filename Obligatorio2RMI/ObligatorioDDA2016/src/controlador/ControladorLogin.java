/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import exceptions.InvalidUserActionException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Jugador;
import modelo.Modelo;
import observadorRemoto.ObservableRemoto;
import observadorRemoto.ObservadorRemoto;
import modelo.ModeloRemoto;

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
