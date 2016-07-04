/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor.observadorRemoto;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Moi
 */
public class ObservableRemotoProceso implements ObservableRemoto{
    private ArrayList<ObservadorRemoto> observadores = new ArrayList();
    
    @Override
    public void agregar(ObservadorRemoto obs) throws RemoteException {
        if(!observadores.contains(obs)){
            observadores.add(obs);
        }
    }

    @Override
    public void quitar(ObservadorRemoto obs) throws RemoteException {
        observadores.remove(obs);
    }
    
    public void notificar() {
        notificar(null);
    }
    public void notificar(Serializable param){
        ArrayList<ObservadorRemoto> copia = new ArrayList();
        copia.addAll(observadores);
        for(ObservadorRemoto obs:copia){
            try{
                obs.actualizar(this,param);
            }catch(RemoteException ex){
                System.out.println("Error:: "+ex);
            }
        }
        
    }
    
}
