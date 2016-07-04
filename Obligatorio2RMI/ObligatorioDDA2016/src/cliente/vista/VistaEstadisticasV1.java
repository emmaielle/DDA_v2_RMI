/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.vista;

import cliente.controlador.ControladorEstadisticas;
import cliente.controlador.VistaEstadisticas;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidor.modelo.Jugador;

/**
 *
 * @author Euge
 */
public class VistaEstadisticasV1 extends javax.swing.JDialog implements VistaEstadisticas{
    
    private ControladorEstadisticas controlador;
    
    public VistaEstadisticasV1(Jugador j) {
        try {
            initComponents();
            controlador = new ControladorEstadisticas(this, j);
            controlador.mostrarTotalApostadoTodos();
            controlador.mostrarTotalCobradoTodos();
            controlador.mostrarTotalApostado();
            controlador.mostrarTotalCobrado();
            setTitle(controlador.jugador.getNombreCompleto());
        } catch (RemoteException ex) {
            Logger.getLogger(VistaEstadisticasV1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_allPagado = new javax.swing.JLabel();
        lbl_apostado = new javax.swing.JLabel();
        lbl_all_apostado = new javax.swing.JLabel();
        lbl_totalCobrado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);
        getContentPane().add(lbl_allPagado);
        lbl_allPagado.setBounds(20, 170, 430, 30);
        getContentPane().add(lbl_apostado);
        lbl_apostado.setBounds(10, 30, 450, 30);
        getContentPane().add(lbl_all_apostado);
        lbl_all_apostado.setBounds(20, 130, 440, 30);
        getContentPane().add(lbl_totalCobrado);
        lbl_totalCobrado.setBounds(20, 70, 390, 30);

        setBounds(0, 0, 495, 339);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        habilitarBotonStats(true);
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbl_allPagado;
    private javax.swing.JLabel lbl_all_apostado;
    private javax.swing.JLabel lbl_apostado;
    private javax.swing.JLabel lbl_totalCobrado;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mostrarTodosApostado(long monto) {
        lbl_all_apostado.setText("Total Apostado por todos los jugadores: " + String.valueOf(monto));
    }

    @Override
    public void mostrarTodosCobrado(long monto) {
        lbl_allPagado.setText("Total Cobrado por todos los jugadores: "+String.valueOf(monto));
    }

    @Override
    public void mostrarTotalApostado(long monto) {
        lbl_apostado.setText("Total apostado desde el ingreso al casino: "+String.valueOf(monto));
    }

    @Override
    public void mostrarTotalCobrado(long m) {
        lbl_totalCobrado.setText("Total cobrado desde el ingreso al casino: "+String.valueOf(m));
    }

    @Override
    public void habilitarBotonStats(boolean habilitar) {
        try {
            controlador.habilitarStats(habilitar);
        } catch (RemoteException ex) {
            Logger.getLogger(VistaEstadisticasV1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminarObservador() {
        controlador.eliminarObservador();
    }
}
