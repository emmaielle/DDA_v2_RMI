/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.vista;

import cliente.controlador.ControladorListaMesas;
import cliente.controlador.VistaListaMesas;
import servidor.exceptions.InvalidUserActionException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import servidor.modelo.Jugador;
import servidor.modelo.MesaRemoto;

/**
 *
 * @author Euge
 */
public class VistaListaMesasV1 extends javax.swing.JDialog implements VistaListaMesas {

    private ControladorListaMesas controlador;
    private VistaMesaV1 vistaMesa;
    private VistaApuestasV1 vistaApuestas;
    
    public VistaListaMesasV1(Jugador j) {
        try {
            initComponents();
            controlador = new ControladorListaMesas(j,this);
        } catch (RemoteException ex) {
            Logger.getLogger(VistaListaMesasV1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        list_mesas = new javax.swing.JList();
        btn_enterTable = new javax.swing.JButton();
        txt_newTableName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnCrear = new javax.swing.JButton();
        btn_apuestas = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("MESAS ACTIVAS");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        jLabel2.setText("Mesas activas:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(121, 0, 91, 25);

        jScrollPane1.setViewportView(list_mesas);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 31, 310, 130);

        btn_enterTable.setText("Ingresar");
        btn_enterTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_enterTableActionPerformed(evt);
            }
        });
        getContentPane().add(btn_enterTable);
        btn_enterTable.setBounds(338, 71, 85, 33);
        getContentPane().add(txt_newTableName);
        txt_newTableName.setBounds(130, 220, 118, 20);

        jLabel1.setText("Crear Mesa:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(120, 179, 130, 20);

        jLabel3.setText("Nombre:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(40, 220, 80, 20);

        btnCrear.setText("Crear");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });
        getContentPane().add(btnCrear);
        btnCrear.setBounds(270, 220, 73, 23);

        btn_apuestas.setText("Ver apuestas realizadas");
        btn_apuestas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_apuestasActionPerformed(evt);
            }
        });
        getContentPane().add(btn_apuestas);
        btn_apuestas.setBounds(180, 260, 160, 40);

        setBounds(0, 0, 483, 410);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_enterTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_enterTableActionPerformed
        ingresarMesa();
    }//GEN-LAST:event_btn_enterTableActionPerformed

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        crearMesa();
    }//GEN-LAST:event_btnCrearActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        salirDeJuego();
    }//GEN-LAST:event_formWindowClosing

    private void btn_apuestasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_apuestasActionPerformed
        controlador.verApuestas();
    }//GEN-LAST:event_btn_apuestasActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btn_apuestas;
    private javax.swing.JButton btn_enterTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList list_mesas;
    private javax.swing.JTextField txt_newTableName;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mostrar(ArrayList<MesaRemoto> lista) {
        ArrayList<String> listado = new ArrayList();
        for(MesaRemoto mr: lista){
            try {
                listado.add(formatear(mr));
            } catch (RemoteException ex) {
                Logger.getLogger(VistaListaMesasV1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        list_mesas.setListData(listado.toArray());
    }

    @Override
    public void abrirMesa(MesaRemoto m, Jugador j, boolean enEspera) {
        vistaMesa = new VistaMesaV1(m, j);
        vistaMesa.setVisible(true);
        vistaMesa.setLocationRelativeTo(null);
        vistaMesa.habilitar(!enEspera);
    }

    @Override
    public void ingresarMesa()  {
        try{
            if (list_mesas.getModel().getSize() == 0)  throw new InvalidUserActionException("No hay ninguna mesa creada");
            if(list_mesas.getSelectedValue() == null) throw new InvalidUserActionException("Debe seleccionar una mesa para ingresar");
            controlador.unirseAmesa(list_mesas.getSelectedValue().toString());
        }
        catch (InvalidUserActionException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    @Override
    public void crearMesa() {
        try{
            if (txt_newTableName.getText().equals("")) throw new InvalidUserActionException("Ingrese un nombre para la mesa a crear");
                if(txt_newTableName.getText().contains(",")) throw new InvalidUserActionException("El nombre de la mesa no puede contener ','");
                    controlador.crearMesa(txt_newTableName.getText());
        }
        catch (InvalidUserActionException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    
    @Override
    public void salirDeJuego() {
        try {
            controlador.salirDeJuego();
            if(vistaMesa!=null){
                vistaMesa.salirDeMesa(); 
                vistaMesa.dispose();
            }
            if (vistaApuestas != null){
                vistaApuestas.dispose();
            }
        } catch (RemoteException ex) {
            Logger.getLogger(VistaListaMesasV1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void errorCrearMesa(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
    
    @Override
    public void eliminarObservador() {
        controlador.eliminarObservador();
    }
    
    @Override
    public void verApuestas(Jugador j) {
        vistaApuestas = new VistaApuestasV1(j);
        vistaApuestas.setLocationRelativeTo(null);
        habilitarApuestas(false);
        vistaApuestas.setVisible(true);
    }

    @Override
    public void habilitarApuestas(boolean b) {
        btn_apuestas.setEnabled(b);
    }

    private String formatear(MesaRemoto mr) throws RemoteException {
        String temp = mr.getNombre() + ", " + mr.getTodosJugadoresEnMesa().size();
        if (mr.getTodosJugadoresEnMesa().size() == 1) return temp + " jugador";
        else return temp + " jugadores"; 
    }
    
}
