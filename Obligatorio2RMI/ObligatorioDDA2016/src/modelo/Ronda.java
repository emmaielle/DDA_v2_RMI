/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Euge
 */
public class Ronda  implements Serializable, Observer{
    private int oid;
    private int nroRonda;
    private ArrayList<Apuesta> apuestasGanadoras = new ArrayList<>();
    private Numero nroGanador = null;
    //private String colorGanador; ///
    private ArrayList<Apuesta> apuestas = new ArrayList<>();
    private static int TIEMPO_LIMITE = 1; // minutos
    private transient Mesa mesa;
    private Date fechaYhoraFin;
    private Proceso elProceso;


    // <editor-fold defaultstate="collapsed" desc="Constructor">   
    public Ronda(int numRonda, Mesa m) {
        nroRonda = numRonda;
        mesa = m;
        try {
            elProceso = (Proceso) new Proceso();
        } catch (RemoteException ex) {
            Logger.getLogger(Ronda.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            elProceso.addObserver(this);
            elProceso.reset();
            elProceso.ejecutar();
        } catch (RemoteException ex) {
            Logger.getLogger(Ronda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public Ronda() throws RemoteException{
        
    }
    
    //</editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters y setters">
    public Numero getNroGanador() {
        return nroGanador;
    }

    public static int getTIEMPO_LIMITE() {
        return TIEMPO_LIMITE;
    }

    public static void setTIEMPO_LIMITE(int TIEMPO_LIMITE) {
        Ronda.TIEMPO_LIMITE = TIEMPO_LIMITE;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }
    
    public Mesa getMesa() {
        return mesa;
    }
    
    public void setApuestas(ArrayList<Apuesta> apuestas) {
        this.apuestas = apuestas;
    }
    
    public int getNroRonda() {
        return nroRonda;
    }

    public ArrayList<Apuesta> getApuestaGanadora() {
        return apuestasGanadoras;
    }

    public ArrayList<Apuesta> getApuestas() {
        return apuestas;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }


    public Date getFechaYhoraFin() {
        return fechaYhoraFin;
    }

    public void setFechaYhoraFin(Date fechaYhoraFin) {
        this.fechaYhoraFin = fechaYhoraFin;
    }
    //agregue para la persistencia
    public void setNroGanador(Numero nroGanador) {
        this.nroGanador = nroGanador;
    }

    public Proceso getElProceso() {
        return elProceso;
    }
    
    
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Metodos">
    
    // sortea si no existe, sino devuelve existente
    public Numero sortearNroGanador() throws RemoteException {
        
        if (nroGanador == null){ // todavia no se sorteo
            int randomOut = (int)Math.floor(Math.random()*37);
            Numero tablero = mesa.buscarNumeroEnTablero(randomOut);
            nroGanador = new Numero(tablero.getValor(), tablero.getColor());
            lookForWinner();
            this.fechaYhoraFin = new Date();
            return nroGanador;
        }
        return nroGanador;
    }
    
    public ApuestaPleno buscarApuestaPorNumero(Numero n){
        ApuestaPleno yaApostada = null;
        for (Apuesta a: apuestas){
            if (a instanceof ApuestaPleno){
                ApuestaPleno ap = (ApuestaPleno)a;
                if (ap.getNumeroTablero() == n)
                    yaApostada = ap;
            }
        }
        return yaApostada;
    }
    
    private Apuesta buscarApuestaPorTipo(String tipo) {
        Apuesta yaApostada = null;
        String type = tipo.split(" ")[0];
        String eleccion = tipo.split(" ")[1];
         for (Apuesta a: apuestas){
            if (a.getTipo().equals(type)){
                if (a.getNumero().split(" ")[1].equals(eleccion))
                    yaApostada = a;
            }
        }
        return yaApostada;
    }
    
    public void apostar(String numero, Numero n, int v, JugadorRuleta jugador) throws RemoteException { //funciona en ambos sentidos si se clickea de nuevo
        Apuesta yaApostada = buscarApuestaPorTipo(numero);
        if (yaApostada == null){ // si entra aca es porque ese numero no fue elegido antes
            Apuesta a = setApuestaByType(numero, v, jugador.getJugador(), n);
            if (a.validar()){
                if (!areThereBetsInThisRondaForThisPlayer(jugador)) {
                    jugador.setRondasSinApostarAnterior(jugador.getRondasSinApostar());
                    jugador.setRondasSinApostar(0);
                }
                agregarApuesta(a);
                jugador.getJugador().modificarSaldo(false, v);
            }
        }
        else desapostar(jugador, numero);
    }
    
    public void desapostar(JugadorRuleta j, Numero n) throws RemoteException{
        Apuesta yaApostada = buscarApuestaPorNumero(n);
        if (yaApostada.getJugador().equals(j)) 
            quitarApuesta(yaApostada);
        if (!areThereBetsInThisRondaForThisPlayer(j)) j.setRondasSinApostar(j.getRondasSinApostarAnterior());
    }
    
    public void desapostar(JugadorRuleta j, String tipo) throws RemoteException {
        Apuesta yaApostada = buscarApuestaPorTipo(tipo);
        if (yaApostada.getJugador().equals(j)) 
            quitarApuesta(yaApostada);
        if (!areThereBetsInThisRondaForThisPlayer(j)) j.setRondasSinApostar(j.getRondasSinApostarAnterior());
    }
    
    public void quitarApuesta(Apuesta a) {
        a.getJugador().modificarSaldo(true,a.getMonto());
        if (a instanceof ApuestaPleno){
            ((ApuestaPleno)a).getNumeroTablero().setApuesta(null);
        }
        a.getJugador().quitarApuesta(a);
        a.setJugador(null);
        a.setRonda(null);
        apuestas.remove(a);
        Modelo.getInstancia().notificar(Modelo.EVENTO_TABLERO);
    }
    
    public void agregarApuesta(Apuesta a) {
        if (a instanceof ApuestaPleno){
            ((ApuestaPleno)a).getNumeroTablero().setApuesta(a);
        }
        a.getJugador().agregarApuesta(a);
        apuestas.add(a);
        Modelo.getInstancia().notificar(Modelo.EVENTO_TABLERO);
    }

    private void lookForWinner() throws RemoteException {
        for (Apuesta a : apuestas){
            if (a.esGanadora(nroGanador)) {
                apuestasGanadoras.add(a);
            }
            modificarSaldos(a);
        }
    }
    
    public void modificarSaldos(Apuesta a) {
        Jugador j = a.getJugador();
        boolean ganadora = false;
        if (!apuestasGanadoras.isEmpty()){
            for (Apuesta ag : apuestasGanadoras){
                if (ag != null && ag.equals(a)){ // si hubo un ganador
                    j.modificarSaldo(true, a.getMonto()* a.getCoeficientePago());
                    j.setTotalCobrado(j.getTotalCobrado() + a.getMonto() * a.getCoeficientePago());
                    j.setTotalApostado(j.getTotalApostado() + a.getMonto());
                    a.setMontoGanado(a.getMonto()* a.getCoeficientePago());
                    ganadora = true;
                }
            }
        }
        if (!ganadora) {
            j.setTotalApostado(j.getTotalApostado() + a.getMonto());
            a.setMontoGanado(0);
        }
        Modelo.getInstancia().notificar(Modelo.EVENTO_ACTUALIZA_SALDOS);
    }
    
    public void eliminarApuestas(Jugador j) throws RemoteException{
        for (int i = 0; i < apuestas.size(); i++){
            if(apuestas.get(i).getJugador() == j){
                quitarApuesta(apuestas.get(i));
                i--;
            }
        }
    }
    
    public long totalApostadoRonda(Jugador j){
        long total = 0;
        for(Apuesta a:apuestas){
            if(a.getJugador()==j) total += a.getMonto();
        }
        return total;
    }

    public boolean areThereBetsInThisRondaForThisPlayer(JugadorRuleta jugador) {
        for (Apuesta a : apuestas){
            if (a.getJugador().equals(jugador)) return true;
        }
        return false;
    }
    // </editor-fold>

    @Override
    public void update(java.util.Observable o, Object arg)   {
        if (arg.equals(Proceso.EVENTO_ADD_SECONDS)){
            Modelo.getInstancia().notificar(Modelo.EVENTO_ADD_SECONDS);
        }
        else if (arg.equals(Proceso.EVENTO_TIME_OUT)){            
            try {
                mesa.finalizarApuestaPorTiempo();
            } catch (RemoteException ex) {
                Logger.getLogger(Ronda.class.getName()).log(Level.SEVERE, null, ex);
            }
            Modelo.getInstancia().notificar(Modelo.EVENTO_SIN_JUGAR);
        }
    }

    public void stopProceso() {
        try {
            elProceso.parar();
        } catch (RemoteException ex) {
            Logger.getLogger(Ronda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void quitarObservador() {
        try {
            elProceso.deleteObserver(this);
        } catch (RemoteException ex) {
            Logger.getLogger(Ronda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Apuesta setApuestaByType(String numero, int monto, Jugador jugador, Numero n) {
        Apuesta a;
        if (n != null && numero.contains("Pleno")){
            a = new ApuestaPleno(monto, jugador, numero, n, this, new Date());
            n.setApuesta(a);
        }
        else if (numero.contains("Color")){
            a = new ApuestaColor(monto, jugador, numero, this, new Date());
        }
        else if (numero.contains("Docena")){
            a = new ApuestaDocena(monto, jugador, numero, this, new Date());
        }
        else a = null;
        return a;
    }

    public void agregar(Apuesta a) {
        apuestas.add(a);
    }
    
    public void agregar(int monto, String sNumero, Date fechayhora){
        Apuesta a;
        if(sNumero.contains("Pleno")){
            a = new ApuestaPleno(monto, 
                    sNumero, null, this, fechayhora);
            a.setMontoGanado(monto);            
        }
        else if(sNumero.contains("Docena")){
            a=new ApuestaDocena(monto, sNumero, this, fechayhora);
            a.setMontoGanado(monto);
        }
        else {
            a=new ApuestaColor(monto, sNumero, this, fechayhora);
            a.setMontoGanado(monto);
        }
        apuestas.add(a);
    }
}
