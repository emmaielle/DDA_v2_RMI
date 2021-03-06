/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.controlador;


import servidor.exceptions.InvalidUserActionException;
import java.awt.Color;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidor.modelo.Jugador;
import servidor.modelo.MesaRemoto;
import servidor.modelo.Modelo;
import servidor.observadorRemoto.ObservableRemoto;
import servidor.observadorRemoto.ObservadorRemoto;
import servidor.modelo.ModeloRemoto;

/**
 *
 * @author Euge
 */
public class ControladorListaMesas extends UnicastRemoteObject implements ObservadorRemoto {
    private ModeloRemoto modelo;
    private Jugador jugador;
    private VistaListaMesas vista;

    public ControladorListaMesas(Jugador j, VistaListaMesas vista) throws RemoteException {
        try {
            //System.setProperty("java.rmi.server.hostname","186.54.152.199");
            this.modelo=(ModeloRemoto)Naming.lookup("rmi://localhost/modelo");
            this.jugador= j;
            this.vista = vista;
            modelo.agregar(this);
            listarMesas();
        } catch (NotBoundException | MalformedURLException ex) {
            Logger.getLogger(ControladorListaMesas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    public void crearMesa(String nom){
        MesaRemoto m;
        try {
            m = modelo.nuevaMesa(nom, jugador);
            vista.abrirMesa(m,jugador, false);
        } catch (RemoteException | InvalidUserActionException ex) {
            Logger.getLogger(ControladorListaMesas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void unirseAmesa(String nom) throws InvalidUserActionException{
        String nameMesa = nom.split(",")[0];
        MesaRemoto m;
        try {
            m = modelo.buscarMesaRuleta(nameMesa);
            modelo.unirJugadorAMesaRuleta(jugador, m, asignarColor(m));
            vista.abrirMesa(m, jugador, true);
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
    
    private Color asignarColor(MesaRemoto m){
        try {
            return modelo.asignarColorRuleta(m);
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorListaMesas.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void salirDeJuego() throws RemoteException {
        modelo.setEnJuego(jugador, false);
    }

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
            jugador = modelo.getJugador(jugador.getOid());
            if (jugador.isApuestasOn()) vista.habilitarApuestas(false);
            else vista.habilitarApuestas(true);
        }
    }

    public void eliminarObservador() {
        try {
            modelo.quitar(this);
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorListaMesas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
}
