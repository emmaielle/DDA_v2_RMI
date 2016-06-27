/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import exceptions.InvalidUserActionException;
import java.awt.Color;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import observadorRemoto.ObservableRemotoV1;

/**
 *
 * @author Euge
 */
public class Modelo extends ObservableRemotoV1 implements IModelo {
        
    private SistemaJugador sj = SistemaJugador.getInstancia();
    private Casino casino = Casino.getInstancia();
    public static final int EVENTO_LOGIN = 1;
    public static final int EVENTO_LOGUEADOS= 2;
    public static final int EVENTO_NUEVA_MESA = 3;
    public static final int EVENTO_TABLERO = 4;
    public static final int EVENTO_SORTEARNUMERO = 5;
    public static final int EVENTO_NUEVO_JUGADOR_MESA_RULETA = 6;
    public static final int EVENTO_JUEGO_CERRADO = 7;
    public static final int EVENTO_ACTUALIZA_SALDOS = 8;
    public static final int EVENTO_SALIR_MESA = 9;
    public static final int EVENTO_STATSWINDOW = 10;
    public static final int EVENTO_CHECK_SALDOS = 11;
    public static final int EVENTO_ADD_SECONDS = 12;
    public static final int EVENTO_SIN_JUGAR = 13;
    public static final int EVENTO_APUESTASWINDOW = 14;

    
    public Modelo() throws RemoteException{
    }

    
    
    // <editor-fold defaultstate="collapsed" desc="Metodos Sistema Jugador">   
    @Override
    public void agregar (Jugador j){
        sj.agregar(j);
        
    }
//    public void persistirJugadores(){
//        sj.persistoJugadores();
//    }
    @Override
    public void obtenerTodos(){
        sj.obtenerJugadores();
    }
    
    // capaz?
//    public void cargarJugadores(ArrayList<Object> j){
//        sj.cargarJugadores(j);
//    }

    @Override
    public boolean isHabilitado() {
        return sj.isHabilitado();
    }

    @Override
    public Jugador login(String nom, String psw) throws InvalidUserActionException{
        return sj.login(nom,psw);
    }
    
    @Override
    public void logout(Jugador j){
        try {
            sj.logout(j);
        } catch (RemoteException ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public long totalApostadoTodos() {
        return sj.totalApostadoTodos();
    }

    @Override
    public long totalCobradoTodos() {
        return sj.totalCobradoTodos();
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Metodos Casino"> 
    @Override
    public ArrayList<String> getJuegos(){
        return casino.getJuegos();
    }

    @Override
    public Object getJuego(String j) {
        return casino.getJuego(j);
    }

    @Override
    public ArrayList<Mesa> listarMesasRuleta() {
        return casino.getRuleta().getListadoMesas();
    }

    @Override
    public void agregarMesaRuleta(Mesa m, Jugador j, Color c) throws InvalidUserActionException {
        casino.getRuleta().agregarMesaRuleta(m, j, c);
    }

    @Override
    public Mesa buscarMesaRuleta(String nom){
        return casino.getRuleta().buscarMesa(nom);
    }

    @Override
    public void unirJugadorAMesaRuleta(Jugador j, Mesa m, Color c) throws InvalidUserActionException{
        casino.getRuleta().unirJugadorAMesaRuleta(j, m, c);
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Metodos mesa">  
    @Override
    public ArrayList<JugadorRuleta> getJugadoresPorMesa(Mesa m) {
        return m.getTodosJugadoresEnMesa();
    }

    @Override
    public Color asignarColorRuleta(Mesa m) {
        return m.getUnusedColour();
    }

    @Override
    public Numero ultNumeroSorteado(Mesa m) {
        return m.getNumeroGanador();
    }

    @Override
    public void apostar(String numero, Mesa mesa, Numero n, String v, JugadorRuleta jugador) throws InvalidUserActionException {
        try {
            mesa.apostarUnNumero(numero, n, v, jugador);
        } catch (RemoteException ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  

    @Override
    public Numero finalizarApuesta(Mesa mesa, JugadorRuleta jr) {
        try {
            return mesa.finalizarApuesta(jr);
        } catch (RemoteException ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void salirDeMesaRuleta(JugadorRuleta jugador, Mesa mesa) {
        try {
            casino.getRuleta().quitarJugador(jugador, mesa);
        } catch (RemoteException ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean estaEnEspera(JugadorRuleta jugador, Mesa mesa) {
        return mesa.estaEnEspera(jugador);
    }
    // </editor-fold>



}
