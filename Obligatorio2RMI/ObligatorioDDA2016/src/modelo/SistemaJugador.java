/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import exceptions.InvalidUserActionException;
import java.util.ArrayList;
import mapeadores.MapeadorJugador;
import mapeadores.MapeadorRonda;
import persistencia.BaseDatos;

/**
 *
 * @author Euge
 */
public class SistemaJugador {
    private ArrayList<Jugador> jugadores = new ArrayList();
    private ArrayList<Jugador> logueados = new ArrayList();
    private boolean habilitado = true;
    private static SistemaJugador instancia = new SistemaJugador();
    
    private SistemaJugador() {
    }

    public static SistemaJugador getInstancia() {
        return instancia;
    }
    
    public ArrayList<Jugador> getLogueados() {
        return logueados;
    }
    
    public boolean isHabilitado() {
        return habilitado;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Metodos">
    public void agregar(Jugador j){
        jugadores.add(j);
    }
    
    public Jugador login(String nom, String psw) throws InvalidUserActionException{
        if(!habilitado) return null;
        Jugador j = buscarJugador(nom);
        if (j != null){
            if (isLogged(j)) throw new InvalidUserActionException("Ya se encuentra logueado");
            if (j.getPassword().equals(psw) && !logueados.contains(j)){
                logueados.add(j);
                Modelo.getInstancia().notificar(Modelo.EVENTO_LOGUEADOS);
                return j;
            }
        }
        return null;
    }

    public Jugador buscarJugador(String nom){
        for(Jugador j:jugadores){
            if(j.getNombre().equals(nom)){
                return j;
            }
        }
        return null;
    }
    
    public boolean isLogged(Jugador j) {
        if (j != null) return logueados.contains(j);
        return false;
    }
    
    public void logout(Jugador j) {
        logueados.remove(j);
        Modelo.getInstancia().notificar(Modelo.EVENTO_LOGUEADOS);
    }

    public long totalCobradoTodos(){
        int total=0;
        for(Jugador j:jugadores){
            total+=j.getTotalCobrado();
        }
        return total;
    }
    public long totalApostadoTodos(){
        int total=0;
        for(Jugador j: jugadores){
            total+=j.getTotalApostado();
        }
        return total;
    }
    // </editor-fold>

    public void obtenerJugadores(){
        BaseDatos bd = BaseDatos.getInstancia();
        bd.conectar();
        MapeadorJugador map = new MapeadorJugador();
        ArrayList juga = bd.obtenerTodos(map);
        // problema 1: no esta tomando todos los jugadores como unicos. juntar
        for(Object o:juga){
            Jugador ju = (Jugador)o;
            ju.setSaldo(1000);
            map.setJ(ju);
            bd.guardar(map);
            jugadores.add(ju);
        }
        retrieveApuestasFromDB(bd, jugadores);
        bd.desconectar();
    }
    
     public void retrieveApuestasFromDB(BaseDatos bd, ArrayList<Jugador> jugadoresArray){
        ArrayList<Ronda> rondas = new ArrayList<>();
        for (Jugador j : jugadoresArray){
            ArrayList<Apuesta> apuestas = j.getApuestas();
            for (Apuesta a : apuestas){ //para cada una de esas apuestas, le termino de agregar la info de la ronda
                boolean bYaEstaba = false; 
                Ronda r;    
                for (int i = 0; i < rondas.size(); i++){ // al primero no entra porque la lista de rondas esta vacia
                    if (rondas.get(i).getOid() == a.getRonda().getOid()) {
                        r = (Ronda)rondas.get(i);
                        a.setRonda(r);
                        r.agregar(a);
                        bYaEstaba = true;
                        break;
                    }
                }
                if (rondas.isEmpty() || !bYaEstaba) { 
                    r = (Ronda)bd.consultar(new MapeadorRonda(), "where oid = " + a.getRonda().getOid() + " ORDER BY r.oid").get(0);
                    rondas.add(r);
                    a.setRonda(r);
                    r.agregar(a);
                }
                a.setJugador(j);
            }
        }
    }

    Jugador getJugador(int oid) {
        for (Jugador j : jugadores){
            if (j.getOid() == oid) return j;
        }
        return null;
    }

}
