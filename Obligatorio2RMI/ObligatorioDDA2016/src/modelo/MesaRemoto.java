/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import exceptions.InvalidUserActionException;
import java.awt.Color;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Moi
 */
public interface MesaRemoto extends Remote {
    
    public ArrayList<Color> getColoresDisp() throws RemoteException;

    public void setColoresDisp(ArrayList<Color> coloresDisp) throws RemoteException;
    
    public String getNombre() throws RemoteException;
    public ArrayList<JugadorRuleta> getJugadoresEspera() throws RemoteException;
    
    public ArrayList<JugadorRuleta> getJugadoresMesa()throws RemoteException;
    
    public ArrayList<JugadorRuleta> getTodosJugadoresEnMesa()throws RemoteException;

    public ArrayList<Numero> getNumeros() throws RemoteException;

    public void setNombre(String nombre)throws RemoteException;

    public void agregarJugador(Color c, Jugador j)throws RemoteException;
    
    public void quitarJugador(JugadorRuleta j) throws RemoteException;

    public boolean validar()throws RemoteException;

    public JugadorRuleta buscarJugador(Jugador j)throws RemoteException;

    public Color getUnusedColour()throws RemoteException;
    
    public int getUltimaRonda()throws RemoteException;
    
    public Ronda buscarRonda(int id)throws RemoteException;

    public Numero sortearNumeroGanador() throws RemoteException;

    public void nuevaRonda()throws RemoteException;
    public Numero getNumeroGanador()throws RemoteException;

    public void apostarUnNumero(String numero, Numero n, String v, JugadorRuleta jugador) throws InvalidUserActionException, RemoteException;
    
    public void desapostar(Numero n, JugadorRuleta jugador) throws InvalidUserActionException, RemoteException;
    
    public void desapostar(String tipo, JugadorRuleta jugador) throws InvalidUserActionException, RemoteException;

    public Numero finalizarApuestaPorTiempo() throws RemoteException;
    public Numero finalizarApuesta(JugadorRuleta jr)throws RemoteException;
    
    public void yaApostado(boolean si)throws RemoteException;
    public Numero apuestaTotal() throws RemoteException ;
       
    public void avisarCheckSaldo() throws RemoteException;
    public boolean estaEnEspera(JugadorRuleta jugador)throws RemoteException;

    public Numero buscarNumeroEnTablero(int randomOut)throws RemoteException;
}
