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
public interface IModelo extends ObservableRemoto{
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
    public ArrayList<JugadorRuleta> getJugadoresPorMesa(MesaRemoto m) throws RemoteException;
    public Color asignarColorRuleta(MesaRemoto m) throws RemoteException;
    public Numero ultNumeroSorteado(MesaRemoto m) throws RemoteException;
    public void apostar(String numero, MesaRemoto mesa, Numero n, String v, JugadorRuleta jugador) throws InvalidUserActionException, RemoteException;
    public Numero finalizarApuesta(MesaRemoto mesa, JugadorRuleta jr) throws RemoteException;
    public void salirDeMesaRuleta(JugadorRuleta jugador, MesaRemoto mesa) throws RemoteException;
    public boolean estaEnEspera(JugadorRuleta jugador, MesaRemoto mesa) throws RemoteException;

    public MesaRemoto nuevaMesa(String n, Jugador j) throws RemoteException, InvalidUserActionException;
}
