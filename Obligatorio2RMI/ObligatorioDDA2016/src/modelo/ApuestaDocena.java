/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Date;



/**
 *
 * @author Euge
 */
public class ApuestaDocena extends Apuesta {

    private static final int COEFICIENTE_PAGO = 3;
    
    // <editor-fold defaultstate="collapsed" desc="Constructor">
    
    public ApuestaDocena(int monto, Jugador jugador, String numero, Ronda ronda, Date fechaHora) {
        super(monto, jugador, numero, ronda, fechaHora);
    }
    
    public ApuestaDocena(int monto, String numero, Ronda ronda, Date fechaHora) {
        super(monto, numero, ronda, fechaHora);
    }

    // </editor-fold>
    
    @Override
    public int getCoeficientePago() {
        return COEFICIENTE_PAGO;
    }
 
    @Override
    public String getTipo() {
        return "Docena";
    }

    @Override
    public boolean esGanadora(Numero numero) {
        String docena = super.getNumero().split(" ")[1];
        return numero.getDocena() == Integer.parseInt(docena);
    }
    
	 
}
