/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor.modelo;

import servidor.exceptions.InvalidUserActionException;
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
    public ArrayList<TipoJugador> getJugadoresEspera() throws RemoteException;
    public ArrayList<TipoJugador> getJugadoresMesa()throws RemoteException;
    public ArrayList<TipoJugador> getTodosJugadoresEnMesa()throws RemoteException;
    public ArrayList<Numero> getNumeros() throws RemoteException;
    public void setNombre(String nombre)throws RemoteException;
    public void agregarJugador(Color c, Jugador j)throws RemoteException;
    public void quitarJugador(TipoJugador j) throws RemoteException;
    public boolean validar()throws RemoteException;
    public TipoJugador buscarJugador(Jugador j)throws RemoteException;
    public Color getUnusedColour()throws RemoteException;
    public int getUltimaRonda()throws RemoteException;
    public Ronda buscarRonda(int id)throws RemoteException;
    public Numero sortearNumeroGanador() throws RemoteException;
    public void nuevaRonda()throws RemoteException;
    public Numero getNumeroGanador()throws RemoteException;
    public void apostarUnNumero(String numero, Numero n, String v, TipoJugador jugador) throws InvalidUserActionException, RemoteException;
    public void desapostar(Numero n, TipoJugador jugador) throws InvalidUserActionException, RemoteException;
    public void desapostar(String tipo, TipoJugador jugador) throws InvalidUserActionException, RemoteException;
    public Numero finalizarApuestaPorTiempo() throws RemoteException;
    public Numero finalizarApuesta(TipoJugador jr)throws RemoteException;
    public void yaApostado(boolean si)throws RemoteException;
    public Numero apuestaTotal() throws RemoteException ;
    public void avisarCheckSaldo() throws RemoteException;
    public boolean estaEnEspera(TipoJugador jugador)throws RemoteException;
    public Numero buscarNumeroEnTablero(int randomOut)throws RemoteException;
}
