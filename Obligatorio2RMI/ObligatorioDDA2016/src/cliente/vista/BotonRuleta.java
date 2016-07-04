/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.vista;

import javax.swing.JButton;
import servidor.modelo.Numero;

/**
 *
 * @author Euge
 */
public class BotonRuleta extends JButton{
    
    private Numero numero;
    
    public BotonRuleta(String text) {
        super(text);
    }

    public Numero getNumero() {
        return numero;
    }

    public void setNumero(Numero numero) {
        this.numero = numero;
    }
}
