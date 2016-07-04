/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor.modelo;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Euge
 */
public class Inicio {

    public static void main(String[] args) {
        try{
            
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            Modelo m = Modelo.getInstancia();
            cargarDatos(m);
            Naming.bind("modelo", m);
            System.out.println("Sin errores al cargar el main");
        }catch(RemoteException | AlreadyBoundException | MalformedURLException e){
            System.out.println("Error main: " + e);
        }
    }
 
    private static void cargarDatos(Modelo m) {
        m.obtenerTodos();
    }
}
