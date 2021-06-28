/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javax.swing.JOptionPane;
import modelo.*;

/**
 *
 * @author Alvaro
 */
public class Main {
    public static void main(String[] args) {
        int i, j;
        //INICIA EL JUEGO
        Juego juego = new Juego();
        juego.inicializar();
        juego.mostrarEstado();
        
        //JUGADOR UNO ROBA UNA CARTA
        juego.robarCarta(juego.jugador);
        juego.mostrarEstado();
        
        //JUGADOR UNO COLOCA UNA CARTA DE DUELO
        i = Integer.parseInt(JOptionPane.showInputDialog("JUGADOR: valor de la carta de duelo que desea colocar"));
        juego.colocarCartaDuelo(juego.jugador, i, juego.tablero.zonaDueloJugador);
        juego.mostrarEstado();
        
        //JUGADOR OPONENTE ROBA UNA CARTA
        juego.robarCarta(juego.oponente);
        juego.mostrarEstado();
        
        //JUGADOR OPONENTE COLOCA UNA CARTA DE DUELO
        i = Integer.parseInt(JOptionPane.showInputDialog("OPONENTE: valor de la carta de duelo que desea colocar"));
        juego.colocarCartaDuelo(juego.oponente, i, juego.tablero.zonaDueloOponente);
        juego.mostrarEstado();
        
        //JUGADOR OPONENTE SELECIONA SU CARTA Y LA CARTA DEL JUGADOR Y PRESIONA ATACAR
        i = Integer.parseInt(JOptionPane.showInputDialog("OPONENTE: valor de la carta con la que va atacar"));
        j = Integer.parseInt(JOptionPane.showInputDialog("OPONENTE: valor de la carta que va ser atacada"));
        juego.atacarCarta(juego.oponente, juego.tablero.zonaDueloOponente, i, juego.jugador, juego.tablero.zonaDueloJugador, j);
        
        //ESTADO FINAL
        juego.mostrarEstado();
        
    }
}
