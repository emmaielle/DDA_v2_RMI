/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import exceptions.InvalidUserActionException;
import java.awt.Color;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Euge
 */
public class JuegoRuleta implements Serializable {

    private static final JuegoRuleta instancia = new JuegoRuleta();
    private ArrayList<MesaRemoto> listadoMesas = new ArrayList();
    
    private JuegoRuleta() {
    }

    public static JuegoRuleta getInstancia() {
        return instancia;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters y setters"> 

    public ArrayList<MesaRemoto> getListadoMesas() {
        return listadoMesas;
    }

    public void setListadoMesas(ArrayList<MesaRemoto> listadoMesas) {
        this.listadoMesas = listadoMesas;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Metodos">
    public void agregar(MesaRemoto m) throws InvalidUserActionException{
        if(listadoMesas.contains(m))throw new InvalidUserActionException("La mesa ya existe");
        listadoMesas.add(m);
        Modelo.getInstancia().notificar(Modelo.EVENTO_NUEVA_MESA);
    }
    
    public void cerrarMesa(MesaRemoto m) throws RemoteException{
        listadoMesas.remove(m);
        (m.buscarRonda(m.getUltimaRonda())).stopProceso();
        Modelo.getInstancia().notificar(Modelo.EVENTO_SALIR_MESA);
    }
    
    public MesaRemoto buscarMesa(String nom) throws RemoteException {
        for (MesaRemoto m: this.listadoMesas){
            if (m.getNombre().equals(nom)) return m;
        }
        // return null or throw exception
        return null;
    }

    public void unirJugadorAMesaRuleta(Jugador j, MesaRemoto m, Color c) throws InvalidUserActionException, RemoteException {
        if (m != null) {
            if (m.getTodosJugadoresEnMesa().size() == 4) throw new InvalidUserActionException("Esta mesa ya contiene el maxino numero de jugadores posible");
            if (j.isEnMesa()) throw new InvalidUserActionException("Jugador ya se encuentra en una mesa");
            m.agregarJugador(c, j);
        }
    }

    public void agregarMesaRuleta(MesaRemoto m, Jugador j, Color c) throws InvalidUserActionException, RemoteException {
        if (j.isEnMesa()) throw new InvalidUserActionException("Jugador ya se encuentra en una mesa");
        if (m.validar()){
            m.agregarJugador(c, j); // lista de jugadores en mesa
            agregar(m); // lista de mesas en ruleta
        }
    }
    
    public void quitarJugador(TipoJugador jugador, MesaRemoto mesa) throws RemoteException {
        mesa.quitarJugador(jugador);
        if (mesa.getTodosJugadoresEnMesa().isEmpty()) cerrarMesa(mesa); 
    }
    
    @Override
    public String toString() {
        return "Ruleta (" + this.listadoMesas.size() + ")";
    }
    
    // </editor-fold>

    public MesaRemoto crearYAgregarAMesa(Jugador j, String n) {
        Mesa m = null;
        try {
            m = new Mesa(n);
            //m.agregarJugador(m.getUnusedColour(), j);
            agregarMesaRuleta(m, j, m.getUnusedColour());
        } catch (RemoteException ex) {
            Logger.getLogger(Casino.class.getName()).log(Level.SEVERE, null, ex);
        
        } catch (InvalidUserActionException ex) {
            Logger.getLogger(JuegoRuleta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return m;
    }

}
