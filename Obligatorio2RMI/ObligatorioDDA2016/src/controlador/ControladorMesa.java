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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.MesaRemoto;
import modelo.Modelo;
import modelo.Numero;
import observadorRemoto.ObservableRemoto;
import observadorRemoto.ObservadorRemoto;
import modelo.ModeloRemoto;
import modelo.TipoJugador;

/**
 *
 * @author Moi
 */
public class ControladorMesa extends UnicastRemoteObject implements ObservadorRemoto {

    private ModeloRemoto modelo;
    private VistaMesa vista;
    private TipoJugador jugador;
    private MesaRemoto mesa;
    
    public ControladorMesa(VistaMesa vista, MesaRemoto m, TipoJugador jr)throws RemoteException{
        try {
            this.vista = vista;
            //System.setProperty("java.rmi.server.hostname","179.25.157.190");
            this.modelo=(ModeloRemoto)Naming.lookup("rmi://localhost/modelo");
            this.jugador = jr;
            this.mesa= m;
            mostrarNum();
            modelo.agregar(this);
        } catch (NotBoundException | MalformedURLException ex) {
            Logger.getLogger(ControladorMesa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void actualizar(ObservableRemoto origen, Serializable param) throws RemoteException {
        if (param.equals(Modelo.EVENTO_ADD_SECONDS)){
            vista.mostrarSegundos(mesa.buscarRonda(mesa.getUltimaRonda()).getElProceso().getSegundos());
        }
        else if (param.equals(Modelo.EVENTO_SIN_JUGAR)){
            try {
                jugador.sinApostarTresVeces();
            } catch (InvalidUserActionException ex) {
                vista.cerrarVentana(ex.getMessage());
                salirDeMesa();
            }
        }
        else if(param.equals(Modelo.EVENTO_TABLERO)){
            mostrarNum();
            long tot = mesa.buscarRonda(mesa.getUltimaRonda()).totalApostadoRonda(jugador.getJugador()); // mas limpio??
            vista.mostrarTotalApostado(tot);
        }
        else if(param.equals(Modelo.EVENTO_SORTEARNUMERO)){            
            jugador = mesa.buscarJugador(jugador.getJugador());
            buscarNumeroActual();
            if (!modelo.estaEnEspera(jugador, mesa))  vista.habilitar(true);
            mensajeRonda();
            mostrarSaldo();
            resetButtons();
        }
        else if (param.equals(Modelo.EVENTO_NUEVO_JUGADOR_MESA_RULETA) || param.equals(Modelo.EVENTO_SALIR_MESA)){
            vista.mostrarJugadores(modelo.getJugadoresPorMesa(mesa));
        }
        else if (param.equals(Modelo.EVENTO_CHECK_SALDOS)){
            try {
                jugador = mesa.buscarJugador(jugador.getJugador());
                jugador.expulsado();
            } catch (InvalidUserActionException ex) {
                vista.cerrarVentana(ex.getMessage());
                salirDeMesa();
            } 
        }
        else if(param.equals(Modelo.EVENTO_ACTUALIZA_SALDOS))
            jugador = mesa.buscarJugador(jugador.getJugador());
            vista.mostrarSaldo(jugador.getJugador().getSaldo());
    }
    
    public void apostar(String numero, Numero n, String v) throws InvalidUserActionException, RemoteException {             
        modelo.apostar(numero, mesa, n, v, jugador);   
        vista.exitoApuesta();
    }

    public void cargarJugadoresActivos() {
        try {
            ArrayList<TipoJugador> j = modelo.getJugadoresPorMesa(mesa);
            vista.mostrarJugadores(j);
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorMesa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buscarNumeroActual() {
        try {
            mostrarNumeroSorteado(modelo.ultNumeroSorteado(mesa).getValor());
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorMesa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void mostrarNumeroSorteado(int i){
        vista.mostrarNumeroSorteado(i);
    }

    public void mostrarSaldo() {
        vista.mostrarSaldo(jugador.getJugador().getSaldo());
    }

    public void finalizarApuesta()  { 
        Numero sorteado;
        try {
            sorteado = modelo.finalizarApuesta(mesa, jugador);
            if(sorteado!= null){
                vista.habilitar(true);
                mesa.avisarCheckSaldo();
            }
            else{
                vista.habilitar(false);
                jugador.setApostado(true);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorMesa.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 

    public void salirDeMesa() {
        try {
            //jugador = mesa.buscarJugador(jugador.getJugador());
            modelo.salirDeMesaRuleta(jugador, mesa);
            eliminarObservador();
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorMesa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mensajeRonda() throws RemoteException{
        String msj = (mesa.estaEnEspera(jugador)) ? "Espera..." : "Apostar";
        vista.mensajeRonda(msj);
    }
    
    public void eliminarObservador() {
        try {
            modelo.quitar(this);
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorMesa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void colorJugador(Color color) {
        vista.colorJugador(color);
    }

    public void desapostar(String tipo) throws InvalidUserActionException, RemoteException{
        mesa.desapostar(tipo, jugador);
    }

    private void resetButtons() {
        vista.resetButtons();
    }
    public void mostrarNum(){
        try {
            vista.mostrar(mesa.getNumeros());
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorMesa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
