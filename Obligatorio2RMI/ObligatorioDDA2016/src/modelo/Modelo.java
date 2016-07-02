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
public class Modelo extends ObservableRemotoV1 implements ModeloRemoto {
    private SistemaJugador sj = SistemaJugador.getInstancia();
    private Casino casino = Casino.getInstancia();
    private static Modelo instancia;
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

    
    protected static Modelo getInstancia(){
        if (instancia == null){
            try {
                instancia = new Modelo();
            } catch (RemoteException ex) {
                System.out.println("Error " + ex);
            }
        }
        return instancia;
    }
    
    private Modelo() throws RemoteException{
    }

    
    
    // <editor-fold defaultstate="collapsed" desc="Metodos Sistema Jugador">   
//    public void persistirJugadores(){
//        sj.persistoJugadores();
//    }
    
    public void obtenerTodos(){
        sj.obtenerJugadores();
    }
    
    // capaz?
//    public void cargarJugadores(ArrayList<Object> j){
//        sj.cargarJugadores(j);
//    }

    @Override
    public boolean isHabilitado() throws RemoteException {
        return sj.isHabilitado();
    }

    @Override
    public Jugador login(String nom, String psw) throws RemoteException, InvalidUserActionException{
        return sj.login(nom,psw);
    }
    
    @Override
    public void logout(Jugador j) throws RemoteException {
        sj.logout(j);
    }
    
    @Override
    public long totalApostadoTodos() throws RemoteException {
        return sj.totalApostadoTodos();
    }

    @Override
    public long totalCobradoTodos() throws RemoteException {
        return sj.totalCobradoTodos();
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Metodos Casino"> 
    @Override
    public ArrayList<String> getJuegos() throws RemoteException{
        return casino.getJuegos();
    }

    @Override
    public Object getJuego(String j) throws RemoteException{
        return casino.getJuego(j);
    }

    @Override
    public ArrayList<MesaRemoto> listarMesasRuleta() throws RemoteException {
        return casino.getRuleta().getListadoMesas();
    }

    @Override
    public void agregarMesaRuleta(MesaRemoto m, Jugador j, Color c) throws InvalidUserActionException, RemoteException {
        casino.getRuleta().agregarMesaRuleta(m, j, c);
    }

    @Override
    public MesaRemoto buscarMesaRuleta(String nom) throws RemoteException{
        return casino.getRuleta().buscarMesa(nom);
    }

    @Override
    public void unirJugadorAMesaRuleta(Jugador j, MesaRemoto m, Color c) throws RemoteException, InvalidUserActionException{
        casino.getRuleta().unirJugadorAMesaRuleta(j, m, c);
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Metodos mesa">  
    @Override
    public ArrayList<TipoJugador> getJugadoresPorMesa(MesaRemoto m) throws RemoteException {
        return m.getTodosJugadoresEnMesa();
    }

    @Override
    public Color asignarColorRuleta(MesaRemoto m) throws RemoteException{
        return m.getUnusedColour();
    }

    @Override
    public Numero ultNumeroSorteado(MesaRemoto m) throws RemoteException{
        return m.getNumeroGanador();
    }

    @Override
    public void apostar(String numero, MesaRemoto mesa, Numero n, String v, TipoJugador jugador) throws RemoteException, InvalidUserActionException {
            mesa.apostarUnNumero(numero, n, v, jugador);
    }  

    @Override
    public Numero finalizarApuesta(MesaRemoto mesa, TipoJugador jr)throws RemoteException {
        try {
            return mesa.finalizarApuesta(jr);
        } catch (RemoteException ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void salirDeMesaRuleta(TipoJugador jugador, MesaRemoto mesa) throws RemoteException{
        casino.getRuleta().quitarJugador(jugador, mesa);
    }

    @Override
    public boolean estaEnEspera(TipoJugador jugador, MesaRemoto mesa) throws RemoteException{
        return mesa.estaEnEspera(jugador);
    }
    // </editor-fold>

    @Override
    public MesaRemoto nuevaMesa(String n, Jugador j) throws RemoteException, InvalidUserActionException {
        return casino.getRuleta().crearYAgregarAMesa(j, n);
    }

    @Override
    public Jugador getJugador(int oid) throws RemoteException {
        return sj.getJugador(oid);
    }

    @Override
    public void setStatsOn(Jugador jugador, boolean habilitar) throws RemoteException {
        sj.setStatsOn(!habilitar, jugador);
    }



}
