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

/**
 *
 * @author Euge
 */
public class JuegoRuleta  {

    private static final JuegoRuleta instancia = new JuegoRuleta();
    private ArrayList<Mesa> listadoMesas = new ArrayList();
    
    private JuegoRuleta() {
    }

    public static JuegoRuleta getInstancia() {
        return instancia;
    }

    // <editor-fold defaultstate="collapsed" desc="Getters y setters"> 

    public ArrayList<Mesa> getListadoMesas() {
        return listadoMesas;
    }

    public void setListadoMesas(ArrayList<Mesa> listadoMesas) {
        this.listadoMesas = listadoMesas;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Metodos">
    public void agregar(Mesa m) throws InvalidUserActionException{
        if(listadoMesas.contains(m))throw new InvalidUserActionException("La mesa ya existe");
        listadoMesas.add(m);
        Modelo.getInstancia().notificar(Modelo.EVENTO_NUEVA_MESA);
    }
    
    public void cerrarMesa(Mesa m){
        listadoMesas.remove(m);
        (m.buscarRonda(m.getUltimaRonda())).stopProceso();
        Modelo.getInstancia().notificar(Modelo.EVENTO_SALIR_MESA);
    }
    
    public Mesa buscarMesa(String nom) {
        for (Mesa m: this.listadoMesas){
            if (m.getNombre().equals(nom)) return m;
        }
        // return null or throw exception
        return null;
    }

    public void unirJugadorAMesaRuleta(Jugador j, Mesa m, Color c) throws InvalidUserActionException {
        if (m != null) {
            if (m.getTodosJugadoresEnMesa().size() == 4) throw new InvalidUserActionException("Esta mesa ya contiene el maxino numero de jugadores posible");
            if (j.isEnMesa()) throw new InvalidUserActionException("Jugador ya se encuentra en una mesa");
            m.agregarJugador(c, j);
        }
    }

    public void agregarMesaRuleta(Mesa m, Jugador j, Color c) throws InvalidUserActionException {
        if (j.isEnMesa()) throw new InvalidUserActionException("Jugador ya se encuentra en una mesa");
        if (m.validar()){
            m.agregarJugador(c, j); // lista de jugadores en mesa
            agregar(m); // lista de mesas en ruleta
        }
    }
    
    public void quitarJugador(JugadorRuleta jugador, Mesa mesa) throws RemoteException {
        mesa.quitarJugador(jugador);
        if (mesa.getTodosJugadoresEnMesa().isEmpty()) cerrarMesa(mesa); 
    }
    
    @Override
    public String toString() {
        return "Ruleta (" + this.listadoMesas.size() + ")";
    }
    
    // </editor-fold>

}
