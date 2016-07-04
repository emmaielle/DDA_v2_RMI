/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor.modelo;

import servidor.exceptions.InvalidUserActionException;
import java.awt.Color;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Objects;
import servidor.mapeadores.MapeadorJugador;
import servidor.mapeadores.MapeadorRonda;
import servidor.persistencia.BaseDatos;

/**
 *
 * @author Euge
 */
public class Mesa extends UnicastRemoteObject implements MesaRemoto {
    private String nombre;
    private ArrayList<TipoJugador> jugadoresEspera = new ArrayList<>();
    private ArrayList<TipoJugador> jugadoresMesa = new ArrayList();
    private ArrayList<Numero> numeros = new ArrayList();
    private ArrayList<Ronda> rondas = new ArrayList();
    private ArrayList<Color> coloresDisp;
    private int cantFinalizados;
    
    // <editor-fold defaultstate="collapsed" desc="Constructor">   
    public Mesa(String nombre) throws RemoteException {
        this.nombre = nombre;
        initMesa();
    }
    
    public Mesa() throws RemoteException{
        
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters y setters">   
    @Override
    public ArrayList<Color> getColoresDisp() {
        if (coloresDisp == null){
            coloresDisp = new ArrayList<>();
            coloresDisp.add(Color.BLUE);
            coloresDisp.add(Color.YELLOW);
            coloresDisp.add(Color.MAGENTA);
            coloresDisp.add(Color.PINK);
        }
        return coloresDisp;
    }

    @Override
    public void setColoresDisp(ArrayList<Color> coloresDisp) {
        this.coloresDisp = coloresDisp;
    }
    
    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public ArrayList<TipoJugador> getJugadoresEspera() {
        return jugadoresEspera;
    }
    
    @Override
    public ArrayList<TipoJugador> getJugadoresMesa() {
        return jugadoresMesa;
    }
    
    @Override
    public ArrayList<TipoJugador> getTodosJugadoresEnMesa(){
        ArrayList<TipoJugador> todos = new ArrayList<>();
        for (TipoJugador k : jugadoresMesa){
            todos.add(k);
        }
        for (TipoJugador j : jugadoresEspera){
            todos.add(j);
        }
        return todos;
    }

    @Override
    public ArrayList<Numero> getNumeros() {
        return numeros;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Metodos">  
    @Override
    public void agregarJugador(Color c, Jugador j){
        // crea y agrega el jugadorRuleta en la mesa actual y lo guarda en su lista de JR
        // devuelve boolean que indica si el jugador esta en espera o no
        TipoJugador jr = new JugadorRuleta(c, this, j);
        jr.setMesa(this);
        j.setJugadorTipo(jr);
        if(jugadoresMesa.isEmpty()){
            jugadoresMesa.add(jr);
        }
        else if(jugadoresMesa.size()<4 && this.buscarRonda(this.getUltimaRonda()).getNroGanador() == null){           
            jugadoresEspera.add(jr);
        }
        Modelo.getInstancia().notificar(Modelo.EVENTO_NUEVO_JUGADOR_MESA_RULETA);
        j.setEnMesa(true);
    }
    
    @Override
    public void quitarJugador(TipoJugador j) throws RemoteException{
        jugadoresMesa.remove(j);
        jugadoresEspera.remove(j);
        j.getJugador().setEnMesa(false);
        //if(jugadoresMesa.size()>0) 
            buscarRonda(getUltimaRonda()).eliminarApuestas(j.getJugador());
        if (!j.isApostado()) {
            if (!jugadoresMesa.isEmpty())
                apuestaTotal();
        }
        System.out.println("QUITAR: Rondas sin apostar " + j.getJugador().getNombreCompleto() + 
                ". Rondas: " + j.getRondasSinApostar());
        j.setRondasSinApostar(0);
        j.setRondasSinApostarAnterior(0);
        Modelo.getInstancia().notificar(Modelo.EVENTO_SALIR_MESA);
        // no necesito quitar mesa de j, porque se va a eliminar solo con el garbage collector
    }

    @Override
    public boolean validar() {
        return !nombre.isEmpty() && !nombre.contains(",");
    }

    @Override
    public TipoJugador buscarJugador(Jugador j){
        for(TipoJugador jr:jugadoresMesa){
            if(jr.getJugador().getNombre().equals(j.getNombre())) return jr;     
        }
        for(TipoJugador jr: jugadoresEspera){
            if(jr.getJugador().getNombre().equals(j.getNombre())) return jr;
        }
        return null;
    }

    public void initMesa() throws RemoteException{
        if(numeros!=null)numeros.clear();
        numeros.add(new Numero(0, Color.green));
        numeros.add(new Numero(1, Color.red));
        numeros.add(new Numero(2, Color.black));
        numeros.add(new Numero(3, Color.red));
        numeros.add(new Numero(4, Color.black));
        numeros.add(new Numero(5, Color.red));
        numeros.add(new Numero(6, Color.black));
        numeros.add(new Numero(7, Color.red));
        numeros.add(new Numero(8, Color.black));
        numeros.add(new Numero(9, Color.red));
        numeros.add(new Numero(10, Color.black));
        numeros.add(new Numero(11, Color.black));
        numeros.add(new Numero(12, Color.red));
        numeros.add(new Numero(13, Color.black));
        numeros.add(new Numero(14, Color.red));
        numeros.add(new Numero(15, Color.black));
        numeros.add(new Numero(16, Color.red));
        numeros.add(new Numero(17, Color.black));
        numeros.add(new Numero(18, Color.red));
        numeros.add(new Numero(19, Color.red));
        numeros.add(new Numero(20, Color.black));
        numeros.add(new Numero(21, Color.red));
        numeros.add(new Numero(22, Color.black));
        numeros.add(new Numero(23, Color.red));
        numeros.add(new Numero(24, Color.black));
        numeros.add(new Numero(25, Color.red));
        numeros.add(new Numero(26, Color.black));
        numeros.add(new Numero(27, Color.red));
        numeros.add(new Numero(28, Color.black));
        numeros.add(new Numero(29, Color.black));
        numeros.add(new Numero(30, Color.red));
        numeros.add(new Numero(31, Color.black));
        numeros.add(new Numero(32, Color.red));
        numeros.add(new Numero(33, Color.black));
        numeros.add(new Numero(34, Color.red));
        numeros.add(new Numero(35, Color.black));
        numeros.add(new Numero(36, Color.red));
        if (getUltimaRonda() != 0){
            buscarRonda(getUltimaRonda()).quitarObservador();
            buscarRonda(getUltimaRonda()).stopProceso();
        }
        
        Ronda ronda;
        ronda = new Ronda(getUltimaRonda() + 1, this);
        rondas.add(ronda);
        cantFinalizados=0;
    }

    @Override
    public Color getUnusedColour() {
        Color sirve = Color.YELLOW;
        ArrayList<Color> temp = new ArrayList<>();
        for (TipoJugador jr : this.getTodosJugadoresEnMesa()){
            temp.add(jr.getColor());
        }
        for (Color c : this.getColoresDisp()){
            if (!temp.contains(c)) {
                sirve = c;
                break;
            }
        }
        return sirve;
    }
    
    @Override
    public int getUltimaRonda(){
        int ultRonda = 0;
        for (Ronda r : rondas){
            if (r.getNroRonda() > ultRonda) ultRonda = r.getNroRonda();
        }
        return ultRonda;
    }
    
    @Override
    public Ronda buscarRonda(int id){
        for (Ronda r: rondas){
            if (r.getNroRonda() == id) return r;
        }
        return null;
    }

    @Override
    public Numero sortearNumeroGanador() throws RemoteException {
        Ronda ultimaRonda = buscarRonda(getUltimaRonda());
        Numero nro = ultimaRonda.sortearNroGanador(); 
        // reviso resultados // aviso ganadores // reparto plata // guardo historial
        persistencia();
        nuevaRonda();
        Modelo.getInstancia().notificar(Modelo.EVENTO_SORTEARNUMERO);
        return nro;
    }

    @Override
    public void nuevaRonda() throws RemoteException {
        // esto asegura que no intente pasar en espera solo porque termino la ronda
        // y no considere la max cant de jugadores por ronda
        while (jugadoresMesa.size() < 4 && !jugadoresEspera.isEmpty()){
            if (!jugadoresEspera.isEmpty()){
                TipoJugador temp = jugadoresEspera.get(0);
                jugadoresMesa.add(temp);
                jugadoresEspera.remove(temp);
            }
        }
        initMesa();
        Modelo.getInstancia().notificar(Modelo.EVENTO_TABLERO);
        Modelo.getInstancia().notificar(Modelo.EVENTO_NUEVO_JUGADOR_MESA_RULETA);
    }
    
    @Override
    public Numero getNumeroGanador() {
        if (this.getUltimaRonda() == 1) return new Numero(-1);
        return (this.buscarRonda(this.getUltimaRonda() - 1)).getNroGanador();
    }

    @Override
    public void apostarUnNumero(String numero, Numero n, String v, TipoJugador jugador) throws InvalidUserActionException, RemoteException {
        if(jugadoresEspera.contains(jugador)) throw new InvalidUserActionException("Debe esperar a que finalice la ronda actual");
        if(jugador.isApostado()) throw new InvalidUserActionException("Ya ha finalizado su apuesta");
        
        if (v.equals("")) {
            desapostar(n, jugador);
        } 
        else {
            int montoInt = Integer.parseInt(v);
            if(jugador.getJugador().getSaldo() < montoInt) throw new InvalidUserActionException("No tiene saldo suficiente para realizar esta apuesta");
            if(montoInt == 0) throw new InvalidUserActionException("Ingrese un monto mayor que 0");
            if(montoInt != 0){
                for(TipoJugador jr:jugadoresMesa){
                    if(jugador.getJugador().getNombreCompleto().equals(jr.getJugador().getNombreCompleto()))
                        (buscarRonda(getUltimaRonda())).apostar(numero, n, montoInt, jr);
                }
                Modelo.getInstancia().notificar(Modelo.EVENTO_ACTUALIZA_SALDOS);
            }
        }
    }
    
    @Override
    public void desapostar(Numero n, TipoJugador jugador) throws InvalidUserActionException, RemoteException {
        if(jugador.isApostado()) throw new InvalidUserActionException("Ya ha finalizado su apuesta");
        for(TipoJugador jr:jugadoresMesa){
            if(jugador.getJugador().getNombre().equals(jr.getJugador().getNombre())){
                if (n == null || n.getApuesta() == null) throw new InvalidUserActionException("Debe elegir un monto para apostar");
                (buscarRonda(getUltimaRonda())).desapostar(jugador, n);
            }
        }
        Modelo.getInstancia().notificar(Modelo.EVENTO_ACTUALIZA_SALDOS);
    }
    
    @Override
    public void desapostar(String tipo, TipoJugador jugador) throws InvalidUserActionException, RemoteException{
        if(jugador.isApostado()) throw new InvalidUserActionException("Ya ha finalizado su apuesta");
        for(TipoJugador jr:jugadoresMesa){
            if(jugador.getJugador().getNombre().equals(jr.getJugador().getNombre())){
                (buscarRonda(getUltimaRonda())).desapostar(jugador, tipo);
            }
        }
        Modelo.getInstancia().notificar(Modelo.EVENTO_ACTUALIZA_SALDOS);
    }

    @Override
    public Numero finalizarApuestaPorTiempo() throws RemoteException{
        cantFinalizados = jugadoresMesa.size();
        return apuestaTotal();
    }
    
    @Override
    public Numero finalizarApuesta(TipoJugador jr)throws RemoteException{
        cantFinalizados++;
        Numero nroSorteado = apuestaTotal();
        Modelo.getInstancia().notificar(Modelo.EVENTO_SIN_JUGAR);
        return nroSorteado;
    } 
    
    @Override
    public void yaApostado(boolean si){
        for(TipoJugador jr:jugadoresMesa){
            jr.setApostado(si);
        }
    }
    
    @Override
    public Numero apuestaTotal() throws RemoteException {
        // a cada jugador en juego le suma una ronda sin apostar. Los que si habian apostado quedan 
        // en 0 y los que tenian X rondas sin apostar le suman 1.
        // cuando terminaron de apostar todos. O cuando apostaron todos menos uno que se va
        if(cantFinalizados == jugadoresMesa.size() || cantFinalizados == jugadoresMesa.size() + 1){ 
            yaApostado(false);
            sumarRondaSinApostar();
            Numero nroGan =  sortearNumeroGanador();
            return nroGan;
        }
        else return null;
    }
    
    private void sumarRondaSinApostar() {
        Ronda ultRonda = buscarRonda(getUltimaRonda());
        for (TipoJugador jr : jugadoresMesa){
            boolean haApostado = false;
            for (Apuesta a: jr.getJugador().getApuestas()){
                if (a.getRonda().equals(ultRonda)) haApostado = true;
            }
            if (!haApostado) jr.setRondasSinApostar(jr.getRondasSinApostar() + 1);
            System.out.println("Rondas sin apostar " + jr.getJugador().getNombreCompleto() + 
                ". Rondas: " + jr.getRondasSinApostar());
        }
    }
    
    @Override
    public void avisarCheckSaldo()  {
        Modelo.getInstancia().notificar(Modelo.EVENTO_CHECK_SALDOS);
    }
    
    @Override
    public boolean estaEnEspera(TipoJugador jugador) {
        return jugadoresEspera.contains(jugador);
    }

    @Override
    public String toString() {
        String temp = nombre + ", " + this.getTodosJugadoresEnMesa().size();
        if (this.getTodosJugadoresEnMesa().size() == 1) return temp + " jugador";
        else return temp + " jugadores";
    }
        
    @Override
     public boolean equals(Object o){
        Mesa m = (Mesa)o;
        return nombre.equalsIgnoreCase(m.getNombre());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.nombre);
        return hash;
    }
    
    @Override
    public Numero buscarNumeroEnTablero(int randomOut) {
        ArrayList<Numero> nums = this.getNumeros();
        for (Numero n: nums){
            if (n.getValor() == randomOut) return n;
        }
        return null;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Persistencia">
    private void persistencia() {
        BaseDatos bd = BaseDatos.getInstancia();
        bd.conectar();
        for(TipoJugador jr:jugadoresMesa){
            persistoJugador(jr, bd);
            persistoRonda(bd);   
        }     
        bd.desconectar();
    }

    private void persistoJugador(TipoJugador jr, BaseDatos bd) {
        MapeadorJugador map = new MapeadorJugador();
        ArrayList jugadores = bd.consultar(map, " and u.oid = "+jr.getJugador().getOid()); // esto podria ser diferente
        Jugador j = (Jugador) jugadores.get(0);
        map.setJ(j);
        j.setSaldo(jr.getJugador().getSaldo());
        bd.guardar(map);
    }

    private void persistoRonda(BaseDatos bd) {
        MapeadorRonda mapR = new MapeadorRonda();
        mapR.setR(this.buscarRonda(this.getUltimaRonda()));
        bd.guardar(mapR);
    }

    // </editor-fold>



   

   
}
