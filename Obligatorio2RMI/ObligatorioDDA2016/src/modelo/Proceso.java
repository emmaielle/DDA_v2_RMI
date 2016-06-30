/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Moi
 */
public class Proceso extends UnicastRemoteObject implements Runnable, IProceso{

    public static final int EVENTO_ADD_SECONDS = 1;
    public static final int EVENTO_TIME_OUT = 2;
    
    private Thread hilo;
    private boolean ejecutar = false;
    private int segundos;
    private MiObservable observable = new MiObservable();
    
    public Proceso() throws RemoteException{
        
    }

    @Override
    public int getSegundos() throws RemoteException {
        return segundos;
    }    
    
    @Override
    public synchronized void addObserver(Observer o) throws RemoteException {
        observable.addObserver(o);
    }

    @Override
    public synchronized void deleteObserver(Observer o) throws RemoteException {
        observable.deleteObserver(o);
    }
    private void avisar(Object evento){
        observable.avisar(evento);
    }

    @Override
    public void ejecutar() throws RemoteException{
        if(!ejecutar ){
            ejecutar = true;
            hilo = new Thread(this);
            hilo.start();
        }
    }

    @Override
    public void parar() throws RemoteException{
        ejecutar=false;
        //para cortar el sleep
        hilo.interrupt();
    }
    
    @Override
    public void reset() throws RemoteException{
        segundos = 0;
        avisar(Proceso.EVENTO_ADD_SECONDS);
    }
    
    
    
    @Override
    public void run()  {
        for (;segundos< Ronda.getTIEMPO_LIMITE()*60&&ejecutar;segundos++){
            avisar(Proceso.EVENTO_ADD_SECONDS);
            try {
                hilo.sleep(1000);               
            } catch (InterruptedException ex) {

            }
        }
        try { 
            ejecutar=false;    
            reset();
        } catch (RemoteException ex) {
            Logger.getLogger(Proceso.class.getName()).log(Level.SEVERE, null, ex);
        }
        avisar(Proceso.EVENTO_TIME_OUT);
    }
           
}