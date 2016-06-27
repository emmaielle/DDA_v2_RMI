/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Observable;

/**
 *
 * @author Moi
 */
public class MiObservable extends Observable {
    public void avisar(Object evento){
        setChanged();
        notifyObservers(evento);
    }
}
