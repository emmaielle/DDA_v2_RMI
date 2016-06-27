/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.ControladorLogin;
import controlador.VistaLogin;
import exceptions.InvalidUserActionException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Jugador;

/**
 *
 * @author Euge
 */
public class VistaLoginV1 extends javax.swing.JDialog implements VistaLogin{

    private ControladorLogin controlador;
    private VistaJuegosV1 vistaJuegos;
    public VistaLoginV1(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        try {
            initComponents();
            controlador = new ControladorLogin(this);
        } catch (RemoteException ex) {
            Logger.getLogger(VistaLoginV1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNombre = new javax.swing.JLabel();
        lblPsw = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtPsw = new javax.swing.JTextField();
        btnIngresarCasino = new javax.swing.JButton();
        estado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("LOGIN");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        lblNombre.setText("Nombre :");
        getContentPane().add(lblNombre);
        lblNombre.setBounds(30, 68, 80, 20);

        lblPsw.setText("Password :");
        getContentPane().add(lblPsw);
        lblPsw.setBounds(29, 125, 90, 20);
        getContentPane().add(txtNombre);
        txtNombre.setBounds(148, 65, 58, 20);
        getContentPane().add(txtPsw);
        txtPsw.setBounds(148, 122, 58, 20);

        btnIngresarCasino.setText("Ingresar Al Casino");
        btnIngresarCasino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarCasinoActionPerformed(evt);
            }
        });
        getContentPane().add(btnIngresarCasino);
        btnIngresarCasino.setBounds(230, 70, 160, 60);
        getContentPane().add(estado);
        estado.setBounds(66, 204, 211, 18);

        setBounds(0, 0, 416, 339);
    }// </editor-fold>//GEN-END:initComponents

    private void btnIngresarCasinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarCasinoActionPerformed
        login();
    }//GEN-LAST:event_btnIngresarCasinoActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        //controlador.eliminarObservador();
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIngresarCasino;
    private javax.swing.JLabel estado;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPsw;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPsw;
    // End of variables declaration//GEN-END:variables

    @Override
    public void errorLogin() {
        JOptionPane.showMessageDialog(this, "Login incorrecto");
    }

    @Override
    public void ingresarJugador(Jugador j) {
        dispose();
        vistaJuegos = new VistaJuegosV1(j);
        vistaJuegos.setVisible(true);
    }

    @Override
    public void habilitar(boolean b) {
        btnIngresarCasino.setEnabled(b);
    }

    @Override
    public void finalizar() {
        if(vistaJuegos!=null) vistaJuegos.logout();
    }
    
    @Override
    public void login(){
        try{
            controlador.login(txtNombre.getText(), txtPsw.getText());
        }
        catch (InvalidUserActionException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
