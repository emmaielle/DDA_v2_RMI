package modelo;

import java.util.Date;



public class ApuestaPleno extends Apuesta {
    
    private Numero numeroTablero;
    private static final int COEFICIENTE_PAGO = 35;

    
    public ApuestaPleno(int monto, Jugador jugador, String sNumero, Numero numero, Ronda ronda, Date fechaHora) {
        super(monto, jugador, sNumero, ronda, fechaHora);
            this.numeroTablero = numero;
    }
    
    public ApuestaPleno(int monto, String sNumero, Numero numero, Ronda ronda, Date fechaHora) {
        super(monto, sNumero, ronda, fechaHora);
            this.numeroTablero = numero;
    }

    public Numero getNumeroTablero() {
        return numeroTablero;
    }

    public void setNumeroTablero(Numero numeroTablero) {
        this.numeroTablero = numeroTablero;
    }
    	 
    @Override
    public boolean validar(){
        if (super.validar())
            return numeroTablero!=null;
        else return false;
    }

    @Override
    public int getCoeficientePago() {
        return COEFICIENTE_PAGO;
    }
    
    @Override
    public  String getTipo() {
        return "Pleno";
    }

    @Override
    public boolean esGanadora(Numero numero) {
        return numero.getValor() == this.numeroTablero.getValor();
    }
}
 
