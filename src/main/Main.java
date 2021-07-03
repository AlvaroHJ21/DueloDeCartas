/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Random;
import javax.swing.JOptionPane;
import modelo.*;

/**
 *
 * @author Alvaro
 */
public class Main {
    public static void main(String[] args) {
        
        int i = new Random().nextInt(2);
        for (int j = 0; j < 10; j++) {
            i = new Random().nextInt(2)+1;
            System.out.println(i);
        }
        
        
    }
    
    public static void pruebaEstatica(){
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
        juego.colocarCartaEnZona(juego.jugador, i, juego.tablero.zonaDueloJugador);
        juego.mostrarEstado();
        
        //JUGADOR OPONENTE ROBA UNA CARTA
        juego.robarCarta(juego.oponente);
        juego.mostrarEstado();
        
        //JUGADOR OPONENTE COLOCA UNA CARTA DE DUELO
        i = Integer.parseInt(JOptionPane.showInputDialog("OPONENTE: valor de la carta de duelo que desea colocar"));
        juego.colocarCartaEnZona(juego.oponente, i, juego.tablero.zonaDueloOponente);
        juego.mostrarEstado();
        
        //JUGADOR OPONENTE SELECIONA SU CARTA Y LA CARTA DEL JUGADOR Y PRESIONA ATACAR
        i = Integer.parseInt(JOptionPane.showInputDialog("OPONENTE: valor de la carta con la que va atacar"));
        j = Integer.parseInt(JOptionPane.showInputDialog("OPONENTE: valor de la carta que va ser atacada"));
        juego.atacarCarta(juego.oponente, i, juego.jugador, j);
        juego.mostrarEstado();
        
        //JUGADOR UNO ROBA UNA CARTA Y COLOCA UNA CARTA DE DUELO Y UNA ESPECIAL
        juego.robarCarta(juego.jugador);
        juego.mostrarEstado();
        i = Integer.parseInt(JOptionPane.showInputDialog("JUGADOR: valor de la carta de duelo que desea colocar"));
        juego.colocarCartaEnZona(juego.jugador, i, juego.tablero.zonaDueloJugador);
        juego.mostrarEstado();
        i = Integer.parseInt(JOptionPane.showInputDialog("JUGADOR: valor de la carta especial que desea colocar"));
        juego.colocarCartaEnZona(juego.jugador, i, juego.tablero.zonaEspecialJugador);
        juego.mostrarEstado();
        
        //JUGADOR UNO PASA EL TURNO AL JUGADOR OPONENTE
        
        //JUGADOR OPONENTE ROBA UNA CARTA DE DUELO Y QUIERE ATACAR AL JUGADOR
        juego.robarCarta(juego.oponente);
        juego.mostrarEstado();
        i = Integer.parseInt(JOptionPane.showInputDialog("OPONENTE: valor de la carta de duelo que desea colocar"));
        juego.colocarCartaEnZona(juego.oponente, i, juego.tablero.zonaDueloOponente);
        juego.mostrarEstado();
        
        i = Integer.parseInt(JOptionPane.showInputDialog("OPONENTE: valor de la carta con la que va atacar"));
        j = Integer.parseInt(JOptionPane.showInputDialog("OPONENTE: valor de la carta que va ser atacada"));
        
        juego.atacarCarta(juego.oponente, i, juego.jugador, j);
        
        juego.mostrarEstado();
    }
}
