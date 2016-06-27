/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import exceptions.InvalidUserActionException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mapeadores.MapeadorJugador;
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
                try {
                    logueados.add(j);
                    new Modelo().notificar(Modelo.EVENTO_LOGUEADOS);
                    return j;
                } catch (RemoteException ex) {
                    Logger.getLogger(SistemaJugador.class.getName()).log(Level.SEVERE, null, ex);
                }
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
    
    public void logout(Jugador j) throws RemoteException {
        logueados.remove(j);
        new Modelo().notificar(Modelo.EVENTO_LOGUEADOS);
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
//
//    public void persistoJugadores(){
//        String url="jdbc:mysql://localhost/obligatoriodda2016";
//        String user="root";
//        String pass="";
//        BaseDatos bd = BaseDatos.getInstancia();
//        bd.conectar(url, user, pass);
//        
//        
//        MapeadorJugador map = new MapeadorJugador();
//        Jugador j = new Jugador("a","a","Juan Perez",1000);
//        
//        map.setJ(j);
//        bd.guardar(map);
//        System.out.println(j);
//        j=new Jugador("b","b","Rodrigo Rodriguez",1000);
//        map.setJ(j);
//        bd.guardar(map);
//                System.out.println(j);
//
//        j=new Jugador("c","c","Roberto Lopez",1000);
//        map.setJ(j);
//        bd.guardar(map);
//                System.out.println(j);
//
//        j = new Jugador("d","d","Leticia Bueno",1000);
//        map.setJ(j);
//        bd.guardar(map);
//                System.out.println(j);
//
//        j = new Jugador("e","e","Laura Lorenzo",1000);
//        map.setJ(j);
//        bd.guardar(map);
//                System.out.println(j);
//
//        j=new Jugador("f","f","Luis Suarez",1000);
//        map.setJ(j);
//        bd.guardar(map);
//                System.out.println(j);
//
//        j = new Jugador("g","g","Moira Lasserre",1000);
//        map.setJ(j);
//        bd.guardar(map);
//                System.out.println(j);
//
//        j=new Jugador("h","h","Maria Eugenia Cremona",1000);
//        map.setJ(j);
//        bd.guardar(map);
//                System.out.println(j);
//
//        j=new Jugador("i","i","Dario Campalans",1000);
//        map.setJ(j);
//        bd.guardar(map);
//                System.out.println(j);
//
//        j=new Jugador("j","j","Gabriel Serrano",1000);
//        map.setJ(j);
//        bd.guardar(map);
//        
//        System.out.println(j);
// 
//        bd.desconectar();
//    }
    public void obtenerJugadores(){
        String url="jdbc:mysql://localhost/obligatoriodda2016";
        String user="root";
        String pass="";
        BaseDatos bd = BaseDatos.getInstancia();
        bd.conectar(url, user, pass);
        MapeadorJugador map = new MapeadorJugador();
        ArrayList juga = bd.obtenerTodos(map);
        for(Object o:juga){
            Jugador ju = (Jugador)o;
            ju.setSaldo(1000);
            map.setJ(ju);
            bd.guardar(map);
            jugadores.add(ju);
        }
        bd.desconectar();

    }
    
}
