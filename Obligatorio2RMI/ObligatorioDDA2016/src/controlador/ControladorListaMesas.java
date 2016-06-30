/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;


import exceptions.InvalidUserActionException;
import java.awt.Color;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.IModelo;
import modelo.Jugador;
import modelo.Mesa;
import modelo.Modelo;
import observadorRemoto.ObservableRemoto;
import observadorRemoto.ObservadorRemoto;

/**
 *
 * @author Euge
 */
public class ControladorListaMesas extends UnicastRemoteObject implements ObservadorRemoto {
    private IModelo modelo;
    private Jugador jugador;
    private VistaListaMesas vista;

    public ControladorListaMesas(Jugador j, VistaListaMesas vista) throws RemoteException {
        try {
            this.modelo=(IModelo)Naming.lookup("rmi://localhost/modelo");
            this.jugador= j;
            this.vista = vista;
            modelo.agregar(this);
            listarMesas();
        } catch (NotBoundException ex) {
            Logger.getLogger(ControladorListaMesas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ControladorListaMesas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public void crearMesa(String nom){
        Mesa m = new Mesa(nom);
        try{
            try {
                modelo.agregarMesaRuleta(m, jugador, asignarColor(m));
                vista.abrirMesa(m,jugador, false);
        }
        catch(InvalidUserActionException ex){
            vista.errorCrearMesa(ex.getMessage());
        }
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorListaMesas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void unirseAmesa(String nom) throws InvalidUserActionException{
        String nameMesa = nom.split(",")[0];
        Mesa m;
        try {
            m = modelo.buscarMesaRuleta(nameMesa);
            modelo.unirJugadorAMesaRuleta(jugador, m, asignarColor(m));
            vista.abrirMesa(m, jugador, true);
        }
        catch (InvalidUserActionException ex){
            vista.errorCrearMesa(ex.getMessage());
        }
        catch (RemoteException ex) {
            Logger.getLogger(ControladorListaMesas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void listarMesas(){
        try {
            vista.mostrar(modelo.listarMesasRuleta());
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorListaMesas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Color asignarColor(Mesa m){
        try {
            return modelo.asignarColorRuleta(m);
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorListaMesas.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public void salirDeJuego() {
        jugador.setEnJuego(false);
    }

//    public void eliminarObservador() {
//        modelo.deleteObserver(this);
//    }

    public void verApuestas() {
        vista.verApuestas(jugador);
    }

    @Override
    public void actualizar(ObservableRemoto origen, Serializable param) throws RemoteException {
        if (param.equals(Modelo.EVENTO_NUEVA_MESA) || param.equals(Modelo.EVENTO_NUEVO_JUGADOR_MESA_RULETA)
         || param.equals(Modelo.EVENTO_SALIR_MESA)){
            listarMesas();
        }
        else if (param.equals(Modelo.EVENTO_APUESTASWINDOW)){
            if (jugador.isApuestasOn()) vista.habilitarApuestas(false);
            else vista.habilitarApuestas(true);
        }
    }
   
}
