/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author Euge
 */
public class Casino {
    
    private ArrayList<Object> juegos = new ArrayList(); // llenarlo
    private JuegoRuleta ruleta = JuegoRuleta.getInstancia();
    private static Casino instancia = new Casino();
    
    // <editor-fold defaultstate="collapsed" desc="Constructor"> 
    private Casino() {
        juegos.add(ruleta);
    }

    public static Casino getInstancia() {
        return instancia;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters"> 
    public ArrayList<String> getJuegos(){
        ArrayList<String> juegosString = new ArrayList();
        for(Object o: juegos){
            juegosString.add(o.toString());
        }
        return juegosString;
    }

    public Object getJuego(String j) {
        for (Object o : juegos){
            if (o.toString().equals(j)) return o;
        }
        return null;
    }

    public JuegoRuleta getRuleta() {
        return ruleta;
    }
    // </editor-fold>
}
