/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Color;
import java.util.ArrayList;
import modelo.JugadorRuleta;
import modelo.Numero;

/**
 *
 * @author Moi
 */
public interface VistaMesa {
    public void mostrar(ArrayList<Numero> numeros);
    public void mostrarJugadores(ArrayList<JugadorRuleta> j);
    public void mostrarNumeroSorteado(int num);
    public void exitoApuesta();
    public void salirDeMesa();
    public void habilitar(boolean b);
    public void mostrarSaldo(long saldoJugador);
    public void mostrarTotalApostado(long total);
    public void mensajeRonda(String msj);
    public void cerrarVentana(String msj);
    public void colorSaldo(Color color);
    public void colorJugador(Color color);
    public void mostrarSegundos(int s);

    public void resetButtons();
}
