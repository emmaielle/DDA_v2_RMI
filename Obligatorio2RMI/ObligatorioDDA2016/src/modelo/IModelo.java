/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import exceptions.InvalidUserActionException;
import java.awt.Color;
import java.util.ArrayList;
import observadorRemoto.ObservableRemoto;

/**
 *
 * @author Euge
 */
public interface IModelo extends ObservableRemoto{
    public void agregar(Jugador j);
    public void obtenerTodos();
    public boolean isHabilitado();
    public Jugador login(String nom, String psw) throws InvalidUserActionException;
    public void logout(Jugador j);
    public long totalApostadoTodos();
    public long totalCobradoTodos();
    public ArrayList<String> getJuegos();
    public Object getJuego(String j);
    public ArrayList<Mesa> listarMesasRuleta();
    public void agregarMesaRuleta(Mesa m, Jugador j, Color c) throws InvalidUserActionException;
    public Mesa buscarMesaRuleta(String nom);
    public void unirJugadorAMesaRuleta(Jugador j, Mesa m, Color c) throws InvalidUserActionException;
    public ArrayList<JugadorRuleta> getJugadoresPorMesa(Mesa m);
    public Color asignarColorRuleta(Mesa m);
    public Numero ultNumeroSorteado(Mesa m);
    public void apostar(String numero, Mesa mesa, Numero n, String v, JugadorRuleta jugador) throws InvalidUserActionException;
    public Numero finalizarApuesta(Mesa mesa, JugadorRuleta jr);
    public void salirDeMesaRuleta(JugadorRuleta jugador, Mesa mesa);
    public boolean estaEnEspera(JugadorRuleta jugador, Mesa mesa);
}
