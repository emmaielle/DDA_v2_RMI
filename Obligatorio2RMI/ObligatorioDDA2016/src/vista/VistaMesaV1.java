/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.ControladorMesa;
import controlador.VistaMesa;
import exceptions.InvalidUserActionException;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import modelo.Jugador;
import modelo.MesaRemoto;
import modelo.Numero;
import modelo.TipoJugador;

/**
 *
 * @author Euge
 */
public class VistaMesaV1 extends javax.swing.JDialog implements VistaMesa, ActionListener{
    
    private ControladorMesa controlador;
    private JSplitPane split = new JSplitPane();
    private PanelTablero bottom;
    private PanelDatos top;


    public VistaMesaV1(MesaRemoto m, Jugador j) {
        try {
            initComponents();
            setTitle("Mesa: " + m.getNombre()+ " - Jugador: " + j.getNombreCompleto());
            TipoJugador jr= m.buscarJugador(j); // acaaa
            controlador = new ControladorMesa(this,m,jr);
            top = new PanelDatos(controlador);
            split.setTopComponent(top);
            split.setOrientation(JSplitPane.VERTICAL_SPLIT);
            split.setDividerLocation(270);
            setContentPane(split);
            controlador.cargarJugadoresActivos();
            controlador.buscarNumeroActual();
            controlador.mostrarSaldo();
            controlador.mensajeRonda();
            controlador.colorJugador(jr.getColor());
        } catch (RemoteException ex) {
            Logger.getLogger(VistaMesaV1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        setBounds(0, 0, 716, 549);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        salirDeMesa();
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public void mostrar(ArrayList<Numero> numeros) {
        bottom = new PanelTablero(numeros,this);
        split.setBottomComponent(bottom);
        split.setDividerLocation(270);
        validate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BotonRuleta origen = (BotonRuleta)e.getSource();
        Numero n = origen.getNumero();
        try{
            String sMonto = top.obtenerApuesta();
            controlador.apostar("Pleno " + n.getValor(), n, sMonto); // change heree
            controlador.mostrarNum();
            
        } catch (RemoteException ex) {
            Logger.getLogger(VistaMesaV1.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InvalidUserActionException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    @Override
    public void mostrarJugadores(ArrayList<TipoJugador> j) {
        top.mostrarJugadores(j);
    }

    @Override
    public void mostrarNumeroSorteado(int num) {
        top.mostrarNumeroSorteado(num);
        validate(); 
    }

    @Override
    public void salirDeMesa() {
        controlador.salirDeMesa();
    }

    @Override
    public void exitoApuesta() {
        top.exito();
    }

    @Override
    public void mostrarSaldo(long saldoJugador) {
        top.mostrarSaldo(saldoJugador);
    }

    @Override
    public void habilitar(boolean b) {
        top.habilitar(b);
    }

    @Override
    public void mostrarTotalApostado(long total) {
        top.mostrarTotalApostado(total);
    }

    @Override
    public void mensajeRonda(String msj) {
        top.mensajesRonda(msj);
    }

    @Override
    public void cerrarVentana(String msj) {
        JOptionPane.showMessageDialog(this, msj);
        salirDeMesa();
        dispose();
    }

    @Override
    public void colorSaldo(Color color){
        top.colorSaldo(color);
    }

    @Override
    public void colorJugador(Color color) {
        top.colorJugador(color);
    }

    @Override
    public void mostrarSegundos(int s) {
        top.mostrarSegundos(s);
    }

    @Override
    public void resetButtons() {
        top.resetButtons();
    }

}
