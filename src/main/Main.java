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
        Jugador jugador2 = new Jugador("Jugador 2");
        Tablero tablero = new Tablero();
        
        /*JUGADOR 1*/
        System.out.println("JUGADOR 1----------------------------------------------------");
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
        Carta c1 = jugador1.mano.getCartaDuelo(); //extrae la ultima carta de duelo (solo para probar)
        jugador1.colocarCarta(c1, tablero.duelo1);
        
        /*JUGADOR 2*/
        System.out.println("JUGADOR 2----------------------------------------------------");
        //GENERAMOS LAS CARTAS EN LA BARAJA DEL JUGADOR
        jugador2.baraja.generarCartas();
        System.out.println("ORDENADAS");
        jugador2.baraja.imprimirCartas();
        
        //REPARTIMOS LAS CARTAS INICIALES DEL JUGADOR (4 DE DUELO Y 1 ESPECIAL)
        System.out.println("CARTAS DE DUELO EN MANO");
        jugador2.mano.generarCartasIniciales(jugador2.baraja);
        jugador2.mano.imprimirCartasD();
        jugador2.mano.imprimirCartasE();
        
        System.out.println("CARTAS QUE QUEDAN EN LA BARAJA");
        jugador2.baraja.imprimirCartas();
        
        //MEZCLAMOS LAS CARTAS SOBRANTES EN LA BARAJA
        jugador2.baraja.barajearCartas();
        System.out.println("CARTAS QUE QUEDAN EN LA BARAJA BARAJEADAS");
        jugador2.baraja.imprimirCartas();
        
        //JUGADOR ROBA UNA CARTA DE LA BARAJA
        jugador2.robarCarta();
        System.out.println("JUGADOR ROBA UN CARTA");
        jugador2.mano.imprimirCartasD();
        jugador2.mano.imprimirCartasE();
        
        System.out.println("CARTAS QUE QUEDAN EN LA BARAJA");
        jugador2.baraja.imprimirCartas();
        
        //JUGADOR PONE UNA CARTA EN ZONA DE DUELO
        Carta c2 = jugador2.mano.getCartaDuelo(); //extrae la ultima carta de duelo (solo para probar)
        jugador2.colocarCarta(c2, tablero.duelo2);
        
        
        //RESULTADO EN EL TABLERO
        System.out.println("**********************************");
        System.out.println("Vida Jugador 2: "+jugador2.vida);
        tablero.imprimirCartas();
        System.out.println("Vida Jugador 1: "+jugador1.vida);
        System.out.println("**********************************");
        
        //JUGADOR 1 ATACA AL JUGADOR 2
        atacarCarta(tablero, jugador1, jugador2, 0, 0); 
        
        //RESULTADO EN EL TABLERO
        System.out.println("**********************************");
        System.out.println("Vida Jugador 2: "+jugador2.vida);
        tablero.imprimirCartas();
        System.out.println("Vida Jugador 1: "+jugador1.vida);
        System.out.println("**********************************");
    }
    
    public static void mostrarBarajas(Jugador jugadores[]){
        for (int i = 0; i < 2; i++) {
            System.out.println("CARTAS EN LA BARAJA DEL JUGADOR "+i);
            jugadores[i].baraja.imprimirCartas();
        }
    }
    
    public static void atacarCarta(Tablero tablero, Jugador atacante, Jugador atacado, int i, int j){
        //PARA PODER ATACER SE NECESITAN TENER CARTAS EN LA ZONA DE DUELO 1
        if(tablero.duelo1.size()>0){
            //LUEGO HAY QUE SELECIONAR LA CARTA CON LA QUE SE DESEA ATACAR
            Carta cA = tablero.duelo1.get(tablero.duelo1.size()-1); 
            /*como pruba la carta selecionada 
            será la ultima ingresada a la zona
            de duelo, sino utilizar la variable i y j
            que indica el valor de la carta elejida*/
            
            //CARTA A ATACAR, de no haber carta quitar vida directamente
            Carta cB = tablero.duelo2.get(tablero.duelo2.size()-1); 
            
            if(cA.valor > cB.valor){ //LA CARTA A TIENE MAYOR VALOR QUE LA CARTA B
                atacado.bajarVida(cA.valor-cB.valor);
                tablero.quitarCartaDeZona(cB.valor, tablero.duelo2);// C DESTRUYE LA CARTA B
                
            }else if(cB.valor > cA.valor){ //LA CARTA B TIENE MAYOR VALOR QUE LA CARTA A
                atacante.bajarVida(cB.valor-cA.valor);
                tablero.quitarCartaDeZona(cA.valor, tablero.duelo1); // C DESTRUYE LA CARTA A
                
            }else{
                System.out.println("EMPATE"); //AMBAS CARTAS C DESTRUYEN
            }
            
            
        }else{
            System.out.println("NO HAY CARTAS EN LA ZONA DE DUELO");
        }
    }
   
}
