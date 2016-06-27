/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import modelo.Apuesta;
import modelo.Numero;

/**
 *
 * @author Euge
 */
public class PanelTablero extends javax.swing.JPanel {

    public PanelTablero(ArrayList<Numero> numeros, ActionListener al) {
        initComponents();
        mostrarTablero(numeros,al);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    private void mostrarTablero(ArrayList<Numero> numeros, ActionListener al) {
        GridLayout disenio = new GridLayout(3, 12);
        setLayout(disenio);
        
        for(Numero n:numeros){
            BotonRuleta b = new BotonRuleta(n.getValor() + "");
            if (n.getApuesta()!= null) {
                b.setBackground(n.getApuesta().getJugador().getColor());
            }
            else b.setBackground(n.getColor());
            
            b.setForeground(Color.white);
            b.addActionListener(al);
            b.setNumero(n);
            
            Apuesta a = n.getApuesta();
            if(a==null) b.setToolTipText("Sin marcar");
            else b.setToolTipText(a.getJugador().getJugador().getNombreCompleto() + ". Monto: " + a.getMonto());
            add(b);
        }
    }
}
