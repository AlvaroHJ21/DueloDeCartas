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
        Tablero tablero = new Tablero();
        
        //GENERAMOS LAS CARTAS EN LA BARAJA DEL JUGADOR
        jugador1.baraja.generarCartas();
        System.out.println("ORDENADAS");
        jugador1.baraja.imprimirCartas();
        
        //REPARTIMOS LAS CARTAS INICIALES DEL JUGADOR (4 DE DUELO Y 1 ESPECIAL)
        System.out.println("CARTAS DE DUELO EN MANO");
        jugador1.mano.generarCartasIniciales(jugador1.baraja);
        jugador1.mano.imprimirCartasD();
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
        jugador1.mano.imprimirCartasD();
        jugador1.mano.imprimirCartasE();
        
        System.out.println("CARTAS QUE QUEDAN EN LA BARAJA");
        jugador1.baraja.imprimirCartas();
        
        //JUGADOR PONE UNA CARTA EN ZONA DE DUELO
        Carta c = jugador1.mano.getCartaDuelo(); //extrae la ultima carta de duelo (solo para probar)
        jugador1.colocarCarta(c, tablero.duelo1);
        
        tablero.imprimirCartas();
        jugador1.mano.imprimirCartasD();
        jugador1.mano.imprimirCartasE();
        
    }
    
    public static void mostrarBarajas(Jugador jugadores[]){
        for (int i = 0; i < 2; i++) {
            System.out.println("CARTAS EN LA BARAJA DEL JUGADOR "+i);
            jugadores[i].baraja.imprimirCartas();
        }
    }
   
}
