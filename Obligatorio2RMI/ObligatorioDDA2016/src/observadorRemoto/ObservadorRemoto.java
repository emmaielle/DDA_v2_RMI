/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observadorRemoto;

import exceptions.InvalidUserActionException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Euge
 */
public interface ObservadorRemoto extends Remote{
    public void actualizar(ObservableRemoto origen, Serializable param) throws RemoteException;
    
}
