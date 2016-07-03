/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.ControladorJuegos;
import controlador.VistaJuegos;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import modelo.JuegoRuleta;
import modelo.Jugador;

/**
 *
 * @author Moi
 */
public class VistaJuegosV1 extends javax.swing.JDialog implements VistaJuegos {

    
    private ControladorJuegos controlador;
    private Jugador jugador;
    private VistaListaMesasV1 vistaListaMesa;
    private VistaEstadisticasV1 vistaEstadisticas;

    public VistaJuegosV1(Jugador j) {
        try {
            initComponents();
            controlador = new ControladorJuegos(this, j);
            controlador.listarJuegos();
            jugador = j;
        } catch (RemoteException ex) {
            Logger.getLogger(VistaJuegosV1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        drp_listaJuegos = new javax.swing.JComboBox<>();
        label1 = new java.awt.Label();
        btn_chooseGame = new javax.swing.JButton();
        btn_statsCasino = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnLogout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("JUEGOS");
        getContentPane().setLayout(null);

        getContentPane().add(drp_listaJuegos);
        drp_listaJuegos.setBounds(130, 81, 155, 20);

        label1.setText("Elija un juego");
        getContentPane().add(label1);
        label1.setBounds(43, 80, 79, 23);

        btn_chooseGame.setText("IR");
        btn_chooseGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_chooseGameActionPerformed(evt);
            }
        });
        getContentPane().add(btn_chooseGame);
        btn_chooseGame.setBounds(295, 80, 70, 23);

        btn_statsCasino.setText("Ver estadísticas del casino");
        btn_statsCasino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_statsCasinoActionPerformed(evt);
            }
        });
        getContentPane().add(btn_statsCasino);
        btn_statsCasino.setBounds(80, 170, 220, 39);
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(10, 139, 380, 10);

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });
        getContentPane().add(btnLogout);
        btnLogout.setBounds(90, 240, 210, 30);

        setBounds(0, 0, 416, 401);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_chooseGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_chooseGameActionPerformed
        controlador.ingresarAjuego(drp_listaJuegos.getSelectedItem().toString());
    }//GEN-LAST:event_btn_chooseGameActionPerformed

    private void btn_statsCasinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_statsCasinoActionPerformed
        mostrarEstadisticas();
    }//GEN-LAST:event_btn_statsCasinoActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        logout(); 
    }//GEN-LAST:event_btnLogoutActionPerformed

    @Override
    public void mostrarJuegos(ArrayList<String> juegos) {
        drp_listaJuegos.setModel(new DefaultComboBoxModel(juegos.toArray()));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btn_chooseGame;
    private javax.swing.JButton btn_statsCasino;
    private javax.swing.JComboBox<String> drp_listaJuegos;
    private javax.swing.JSeparator jSeparator1;
    private java.awt.Label label1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void abrirJuego(Object juego) {
        if(juego.getClass()==JuegoRuleta.class){
            vistaListaMesa = new VistaListaMesasV1(jugador);
            vistaListaMesa.setVisible(true);
        }
        else
            System.out.println("vista.VistaJuegosV1.abrirJuego()");
    }

    @Override
    public void habilitarIrAJuego(boolean habilitar) {
        btn_chooseGame.setEnabled(habilitar);
    }

    @Override
    public void mostrarEstadisticas() {
        vistaEstadisticas = new VistaEstadisticasV1(jugador);
        vistaEstadisticas.setVisible(true);
        jugador.setStatsOn(true);
        habilitarEstadisticas(false);
    }

    @Override
    public void logout() {
        dispose();
        controlador.logout();
        if(vistaListaMesa != null){
            vistaListaMesa.salirDeJuego();
            vistaListaMesa.dispose();
            vistaListaMesa.eliminarObservador();
        }
        if (vistaEstadisticas != null) {
            vistaEstadisticas.dispose();
            vistaEstadisticas.eliminarObservador();
        }
        controlador.eliminarObservador();
    }

    @Override
    public void habilitarEstadisticas(boolean b) {
        btn_statsCasino.setEnabled(b);
    }

}
