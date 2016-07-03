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
import observadorRemoto.ObservableRemoto;

/**
 *
 * @author Euge
 */
public interface ModeloRemoto extends ObservableRemoto{
//    public void obtenerTodos();
    public boolean isHabilitado() throws RemoteException;
    public Jugador login(String nom, String psw) throws InvalidUserActionException, RemoteException;
    public void logout(Jugador j) throws RemoteException;
    public long totalApostadoTodos() throws RemoteException;
    public long totalCobradoTodos() throws RemoteException;
    public ArrayList<String> getJuegos() throws RemoteException;
    public Object getJuego(String j) throws RemoteException;
    public ArrayList<MesaRemoto> listarMesasRuleta() throws RemoteException;
    public void agregarMesaRuleta(MesaRemoto m, Jugador j, Color c) throws InvalidUserActionException, RemoteException;
    public MesaRemoto buscarMesaRuleta(String nom) throws RemoteException;
    public void unirJugadorAMesaRuleta(Jugador j, MesaRemoto m, Color c) throws InvalidUserActionException, RemoteException;
    public ArrayList<TipoJugador> getJugadoresPorMesa(MesaRemoto m) throws RemoteException;
    public Color asignarColorRuleta(MesaRemoto m) throws RemoteException;
    public Numero ultNumeroSorteado(MesaRemoto m) throws RemoteException;
    public void apostar(String numero, MesaRemoto mesa, Numero n, String v, TipoJugador jugador) throws InvalidUserActionException, RemoteException;
    public Numero finalizarApuesta(MesaRemoto mesa, TipoJugador jr) throws RemoteException;
    public void salirDeMesaRuleta(TipoJugador jugador, MesaRemoto mesa) throws RemoteException;
    public boolean estaEnEspera(TipoJugador jugador, MesaRemoto mesa) throws RemoteException;
    public MesaRemoto nuevaMesa(String n, Jugador j) throws RemoteException, InvalidUserActionException;
    public Jugador getJugador(int oid) throws RemoteException;
    public void setStatsOn(Jugador jugador, boolean habilitar) throws RemoteException;
    public void setEnJuego(Jugador jugador, boolean b) throws RemoteException;
    public void setApuestasOn(Jugador j, boolean b) throws RemoteException;
    public ArrayList<Apuesta> getApuestas(Jugador j) throws RemoteException;
}