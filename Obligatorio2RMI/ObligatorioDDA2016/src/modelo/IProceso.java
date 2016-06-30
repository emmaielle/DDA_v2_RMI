/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Observer;

/**
 *
 * @author Moi
 */
public interface IProceso extends Remote {
    public void ejecutar() throws RemoteException;
    public void reset() throws RemoteException;
    public void parar() throws RemoteException;
    public void addObserver(Observer o) throws RemoteException;
    public void deleteObserver(Observer o) throws RemoteException;
    public int getSegundos() throws RemoteException;
}
