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
import java.util.logging.Level;
import java.util.logging.Logger;
import mapeadores.MapeadorJugador;
import mapeadores.MapeadorRonda;
import persistencia.BaseDatos;

/**
 *
 * @author Euge
 */
public class Mesa {
    private String nombre;
    private ArrayList<JugadorRuleta> jugadoresEspera = new ArrayList<>();
    private ArrayList<JugadorRuleta> jugadoresMesa = new ArrayList();
    private ArrayList<Numero> numeros = new ArrayList();
    private ArrayList<Ronda> rondas = new ArrayList();
    private ArrayList<Color> coloresDisp;
    private int cantFinalizados;
    
    // <editor-fold defaultstate="collapsed" desc="Constructor">   
    public Mesa(String nombre) {
        this.nombre = nombre;
        initMesa();
    }
 // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters y setters">   
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

    public void setColoresDisp(ArrayList<Color> coloresDisp) {
        this.coloresDisp = coloresDisp;
    }
    
    public String getNombre() {
        return nombre;
    }

    public ArrayList<JugadorRuleta> getJugadoresEspera() {
        return jugadoresEspera;
    }
    
    public ArrayList<JugadorRuleta> getJugadoresMesa() {
        return jugadoresMesa;
    }
    
    public ArrayList<JugadorRuleta> getTodosJugadoresEnMesa(){
        ArrayList<JugadorRuleta> todos = new ArrayList<>();
        for (JugadorRuleta k : jugadoresMesa){
            todos.add(k);
        }
        for (JugadorRuleta j : jugadoresEspera){
            todos.add(j);
        }
        return todos;
    }

    public ArrayList<Numero> getNumeros() {
        return numeros;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Metodos">  
    
    public void agregarJugador(Color c, Jugador j){
        try {
            // crea y agrega el jugadorRuleta en la mesa actual y lo guarda en su lista de JR
            // devuelve boolean que indica si el jugador esta en espera o no
            JugadorRuleta jr = new JugadorRuleta(c, this, j);
            jr.setMesa(this); // mesa en jugador
            if(jugadoresMesa.isEmpty()){
                jugadoresMesa.add(jr);
            }
            else if(jugadoresMesa.size()<4 && this.buscarRonda(this.getUltimaRonda()).getNroGanador() == null){           
                jugadoresEspera.add(jr);
            }
            new Modelo().notificar(Modelo.EVENTO_NUEVO_JUGADOR_MESA_RULETA);
            j.setEnMesa(true);
        } catch (RemoteException ex) {
            Logger.getLogger(Mesa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void quitarJugador(JugadorRuleta j) throws RemoteException{
        jugadoresMesa.remove(j);
        jugadoresEspera.remove(j);
        j.getJugador().setEnMesa(false);
        if(jugadoresMesa.size()>0) buscarRonda(getUltimaRonda()).eliminarApuestas(j);
        if (!j.isApostado()) {
            apuestaTotal();
        }
        System.out.println("QUITAR: Rondas sin apostar " + j.getJugador().getNombreCompleto() + 
                ". Rondas: " + j.getRondasSinApostar());
        j.setRondasSinApostar(0);
        j.setRondasSinApostarAnterior(0);
        new Modelo().notificar(Modelo.EVENTO_SALIR_MESA);
        // no necesito quitar mesa de j, porque se va a eliminar solo con el garbage collector
    }

    public boolean validar() {
        return !nombre.isEmpty() && !nombre.contains(",");
    }

    public JugadorRuleta buscarJugador(Jugador j){
        for(JugadorRuleta jr:jugadoresMesa){
            if(jr.getJugador()==j) return jr;     
        }
        for(JugadorRuleta jr: jugadoresEspera){
            if(jr.getJugador()==j) return jr;
        }
        return null;
    }

    public void initMesa(){
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
        
        Ronda ronda = new Ronda(getUltimaRonda() + 1, this);
        rondas.add(ronda);
        cantFinalizados=0;
    }

    public Color getUnusedColour() {
        Color sirve = Color.YELLOW;
        ArrayList<Color> temp = new ArrayList<>();
        for (JugadorRuleta jr : this.getTodosJugadoresEnMesa()){
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
    
    public int getUltimaRonda(){
        int ultRonda = 0;
        for (Ronda r : rondas){
            if (r.getNroRonda() > ultRonda) ultRonda = r.getNroRonda();
        }
        return ultRonda;
    }
    
    public Ronda buscarRonda(int id){
        for (Ronda r: rondas){
            if (r.getNroRonda() == id) return r;
        }
        return null;
    }

    public Numero sortearNumeroGanador() throws RemoteException {
        Ronda ultimaRonda = buscarRonda(getUltimaRonda());
        Numero nro = ultimaRonda.sortearNroGanador(); 
        // reviso resultados // aviso ganadores // reparto plata // guardo historial
        persistencia();
        nuevaRonda();
        new Modelo().notificar(Modelo.EVENTO_SORTEARNUMERO);
        return nro;
    }

    public void nuevaRonda() throws RemoteException{
        // esto asegura que no intente pasar en espera solo porque termino la ronda
        // y no considere la max cant de jugadores por ronda
        while (jugadoresMesa.size() < 4 && !jugadoresEspera.isEmpty()){
            if (!jugadoresEspera.isEmpty()){
                JugadorRuleta temp = jugadoresEspera.get(0);
                jugadoresMesa.add(temp);
                jugadoresEspera.remove(temp);
            }
        }
        initMesa();
        new Modelo().notificar(Modelo.EVENTO_TABLERO);
        new Modelo().notificar(Modelo.EVENTO_NUEVO_JUGADOR_MESA_RULETA);
    }
    
    public Numero getNumeroGanador() {
        if (this.getUltimaRonda() == 1) return new Numero(-1);
        return (this.buscarRonda(this.getUltimaRonda() - 1)).getNroGanador();
    }

    public void apostarUnNumero(String numero, Numero n, String v, JugadorRuleta jugador) throws InvalidUserActionException, RemoteException {
        if(jugadoresEspera.contains(jugador)) throw new InvalidUserActionException("Debe esperar a que finalice la ronda actual");
        if(jugador.isApostado()) throw new InvalidUserActionException("Ya ha finalizado su apuesta");
        
        if (v.equals("")) {
            desapostar(n, jugador);
            if (!numero.split(" ")[0].equals("Pleno")) throw new InvalidUserActionException("");
        } 
        else {
            int montoInt = Integer.parseInt(v);
            if(jugador.getJugador().getSaldo() < montoInt) throw new InvalidUserActionException("No tiene saldo suficiente para realizar esta apuesta");
            if(montoInt == 0) throw new InvalidUserActionException("Ingrese un monto mayor que 0");
            if(montoInt != 0){
                for(JugadorRuleta jr:jugadoresMesa){
                    if(jugador==jr)
                        (buscarRonda(getUltimaRonda())).apostar(numero, n, montoInt, jugador);
                }
                new Modelo().notificar(Modelo.EVENTO_ACTUALIZA_SALDOS);
            }
        }
    }
    
    public void desapostar(Numero n, JugadorRuleta jugador) throws InvalidUserActionException, RemoteException {
        if(jugador.isApostado()) throw new InvalidUserActionException("Ya ha finalizado su apuesta");
        for(JugadorRuleta jr:jugadoresMesa){
            if(jugador==jr)
                if (n!=null && n.getApuesta() != null) (buscarRonda(getUltimaRonda())).desapostar(jugador, n);
        }
        new Modelo().notificar(Modelo.EVENTO_ACTUALIZA_SALDOS);
    }
    
    public void desapostar(String tipo, JugadorRuleta jugador) throws InvalidUserActionException, RemoteException{
        if(jugador.isApostado()) throw new InvalidUserActionException("Ya ha finalizado su apuesta");
        for(JugadorRuleta jr:jugadoresMesa){
            if(jugador==jr)
                (buscarRonda(getUltimaRonda())).desapostar(jugador, tipo);
        }
        new Modelo().notificar(Modelo.EVENTO_ACTUALIZA_SALDOS);
    }

    public Numero finalizarApuestaPorTiempo() throws RemoteException{
        cantFinalizados = jugadoresMesa.size();
        return apuestaTotal();
    }
    
    public Numero finalizarApuesta(JugadorRuleta jr)throws RemoteException{
        cantFinalizados++;
        Numero nroSorteado = apuestaTotal();
        new Modelo().notificar(Modelo.EVENTO_SIN_JUGAR);
        return nroSorteado;
    } 
    
    public void yaApostado(boolean si){
        for(JugadorRuleta jr:jugadoresMesa){
            jr.setApostado(si);
        }
    }
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
        for (JugadorRuleta jr : jugadoresMesa){
            boolean haApostado = false;
            for (Apuesta a: jr.getJugador().getApuestas()){
                if (a.getRonda().equals(ultRonda)) haApostado = true;
            }
            if (!haApostado) jr.setRondasSinApostar(jr.getRondasSinApostar() + 1);
            System.out.println("Rondas sin apostar " + jr.getJugador().getNombreCompleto() + 
                ". Rondas: " + jr.getRondasSinApostar());
        }
    }
    
    public void avisarCheckSaldo()  {
        try {
            new Modelo().notificar(Modelo.EVENTO_CHECK_SALDOS);
        } catch (RemoteException ex) {
            Logger.getLogger(Mesa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean estaEnEspera(JugadorRuleta jugador) {
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
     // </editor-fold>



    private void persistencia() {
        String url="jdbc:mysql://localhost/obligatoriodda2016";
        String user="root";
        String pass="";
        BaseDatos bd = BaseDatos.getInstancia();
        bd.conectar(url, user, pass);
        for(JugadorRuleta jr:jugadoresMesa){
            persistoJugador(jr, bd);
            persistoRonda(jr,bd);   
        }     
        bd.desconectar();

    }

    private void persistoJugador(JugadorRuleta jr, BaseDatos bd) {
        MapeadorJugador map = new MapeadorJugador();
        ArrayList jugadores = bd.consultar(map, " where u.oid = "+jr.getJugador().getOid());
        Jugador j = (Jugador) jugadores.get(0);
        map.setJ(j);
        j.setSaldo(jr.getJugador().getSaldo());
        bd.guardar(map);
    }

    private void persistoRonda(JugadorRuleta jr, BaseDatos bd) {
        MapeadorRonda mapR = new MapeadorRonda();
        mapR.setR(this.buscarRonda(this.getUltimaRonda()));
        bd.guardar(mapR);
    }

    public Numero buscarNumeroEnTablero(int randomOut) {
        ArrayList<Numero> nums = this.getNumeros();
        for (Numero n: nums){
            if (n.getValor() == randomOut) return n;
        }
        return null;
    }



   

   
}
