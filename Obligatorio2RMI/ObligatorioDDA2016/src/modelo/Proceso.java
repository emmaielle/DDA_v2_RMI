/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Observer;

/**
 *
 * @author Moi
 */
public class Proceso implements Runnable{

    public static final int EVENTO_ADD_SECONDS = 1;
    public static final int EVENTO_TIME_OUT = 2;
    
    private Thread hilo;
    private boolean ejecutar = false;
    private int segundos;
    private MiObservable observable = new MiObservable();
    
    public Proceso(){
        
    }

    public int getSegundos() {
        return segundos;
    }    
    
    public synchronized void addObserver(Observer o) {
        observable.addObserver(o);
    }

    public synchronized void deleteObserver(Observer o) {
        observable.deleteObserver(o);
    }
    private void avisar(Object evento){
        observable.avisar(evento);
    }

    public void ejecutar(){
        if(!ejecutar ){
            ejecutar = true;
            hilo = new Thread(this);
            hilo.start();
        }
    }

    public void parar(){
        ejecutar=false;
        //para cortar el sleep
        hilo.interrupt();
    }
    
    public void reset(){
        segundos = 0;
        avisar(Proceso.EVENTO_ADD_SECONDS);
    }
    
    
    
    @Override
    public void run() {
        for (;segundos< Ronda.getTIEMPO_LIMITE()*60&&ejecutar;segundos++){
            avisar(Proceso.EVENTO_ADD_SECONDS);
            try {
                hilo.sleep(1000);               
            } catch (InterruptedException ex) {

            }
        }
        ejecutar=false;    
        reset();
        avisar(Proceso.EVENTO_TIME_OUT);
    }
           
}