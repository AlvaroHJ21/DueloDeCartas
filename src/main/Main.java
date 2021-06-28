/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import modelo.*;

/**
 *
 * @author Alvaro
 */
public class Main {
    public static void main(String[] args) {
        Jugador jugador1 = new Jugador("Alvaro");
        Tablero tablero;
        
        //GENERAMOS LAS CARTAS EN LA BARAJA DEL JUGADOR
        jugador1.baraja.generarCartas();
        System.out.println("ORDENADAS");
        jugador1.baraja.imprimirCartas();
        
        //REPARTIMOS LAS CARTAS INICIALES DEL JUGADOR (4 DE DUELO Y 1 ESPECIAL)
        System.out.println("CARTAS DE DUELO EN MANO");
        jugador1.mano.generarCartasIniciales(jugador1.baraja);
        System.out.println("DUELO");
        jugador1.mano.imprimirCartasD();
        System.out.println("ESPECIALES");
        jugador1.mano.imprimirCartasE();
        
        System.out.println("CARTAS QUE QUEDAN EN LA BARAJA");
        jugador1.baraja.imprimirCartas();
        
        //MEZCLAMOS LAS CARTAS SOBRANTES EN LA BARAJA
        jugador1.baraja.barajearCartas();
        System.out.println("CARTAS QUE QUEDAN EN LA BARAJA BARAJEADAS");
        jugador1.baraja.imprimirCartas();
        
        //JUGADOR ROBA UNA CARTA DE LA BARAJA
        jugador1.robarCarta();
        System.out.println("JUGADOR ROBA UN CARTA");
        System.out.println("DUELO");
        jugador1.mano.imprimirCartasD();
        System.out.println("ESPECIALES");
        jugador1.mano.imprimirCartasE();
        
        System.out.println("CARTAS QUE QUEDAN EN LA BARAJA");
        jugador1.baraja.imprimirCartas();
        
        
    }
    
    public static void mostrarBarajas(Jugador jugadores[]){
        for (int i = 0; i < 2; i++) {
            System.out.println("CARTAS EN LA BARAJA DEL JUGADOR "+i);
            jugadores[i].baraja.imprimirCartas();
        }
    }
   
}
