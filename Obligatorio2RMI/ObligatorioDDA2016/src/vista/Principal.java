/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.util.ArrayList;

/**
 *
 * @author Euge
 */
public class Principal extends javax.swing.JFrame {

    private ArrayList<VistaLoginV1> vistasLogin = new ArrayList<>();
    public Principal() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        finalizar = new javax.swing.JButton();
        ingresoCasino1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("INGRESO AL CASINO");
        getContentPane().setLayout(null);

        finalizar.setText("Finalizar");
        finalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finalizarActionPerformed(evt);
            }
        });
        getContentPane().add(finalizar);
        finalizar.setBounds(110, 170, 170, 50);

        ingresoCasino1.setText("Ingresar al Casino");
        ingresoCasino1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ingresoCasino1ActionPerformed(evt);
            }
        });
        getContentPane().add(ingresoCasino1);
        ingresoCasino1.setBounds(110, 70, 170, 50);

        setBounds(0, 0, 416, 339);
    }// </editor-fold>//GEN-END:initComponents

    private void ingresoCasino1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ingresoCasino1ActionPerformed
        ingresar();
    }//GEN-LAST:event_ingresoCasino1ActionPerformed

    private void finalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finalizarActionPerformed
        finalizar();
    }//GEN-LAST:event_finalizarActionPerformed

    private void ingresar(){
        VistaLoginV1 vistaLogin = new VistaLoginV1(this,false);
        vistasLogin.add(vistaLogin);
        vistaLogin.setVisible(true);
        vistaLogin.setLocationRelativeTo(this);
    }
    
    private void finalizar() {
        dispose();
        if(!vistasLogin.isEmpty()) 
            for (VistaLoginV1 v : vistasLogin) v.finalizar();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton finalizar;
    private javax.swing.JButton ingresoCasino1;
    // End of variables declaration//GEN-END:variables
}
