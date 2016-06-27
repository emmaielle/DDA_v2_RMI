/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.awt.Color;
import java.util.Date;

/**
 *
 * @author Euge
 */
public class ApuestaColor extends Apuesta {

    private static final int COEFICIENTE_PAGO = 2;
    
    public ApuestaColor(int monto, Jugador jugador, String sNumero, Ronda ronda, Date fechaHora) {
        super(monto, jugador, sNumero, ronda, fechaHora);
    }
    
    public ApuestaColor(int monto, String sNumero, Ronda ronda, Date fechaHora) {
        super(monto, sNumero, ronda, fechaHora);
    }

    @Override
    public int getCoeficientePago() {
        return COEFICIENTE_PAGO;
    }
 
    @Override
    public String getTipo() {
        return "Color";
    }

    @Override
    public boolean esGanadora(Numero numero) {
        String color = super.getNumero().split(" ")[1];
        Color selected = Color.GREEN;
        if (color.equals("RED")) selected = Color.RED;
        if (color.equals("BLACK")) selected = Color.BLACK;
        return numero.getColor().equals(selected); //check
    }
	 
}
 

