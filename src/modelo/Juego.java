/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author Alvaro
 */
public class Juego {
    public Tablero tablero;
    public Jugador jugador;
    public Jugador oponente;
    
    public Juego(){
        tablero = new Tablero();
        jugador = new Jugador("Jugador 1");
        oponente = new Jugador("Jugador 2");
    }
    
    public void inicializar(){
        jugador.baraja.generarCartas();
        jugador.mano.generarCartasIniciales(jugador.baraja);
        jugador.baraja.barajearCartas();
        
        oponente.baraja.generarCartas();
        oponente.mano.generarCartasIniciales(oponente.baraja);
        oponente.baraja.barajearCartas();
        
        //DECIDIR QUIEN EMPIEZA
        //supongamos que empiezas tú
        jugador.turno = true;
        oponente.turno = false;
    }
    
    public void atacarCarta(Jugador atacante, ArrayList<Carta> zonaAtacante, int i, Jugador atacado, ArrayList<Carta> zonaAtacado, int j){
        //PARA PODER ATACER SE NECESITAN TENER CARTAS EN LA ZONA DE DUELO 1
        if(tablero.zonaDueloJugador.size()>0 && tablero.zonaDueloOponente.size()>0){
                        
            //SELECCIONANDO CARTA DEL JUGADOR
            Carta cAtacante = zonaAtacante.get(getIndexCartaPorValor(i, zonaAtacante)); 
            
            //SELECCIONANDO CARTA DEL OPONENTE
            Carta cAtacado = zonaAtacado.get(getIndexCartaPorValor(j, zonaAtacado)); 
            
            if(cAtacante.valor > cAtacado.valor){ //LA CARTA DEL ATACANTE ES MAYOR
                atacado.bajarVida(cAtacante.valor-cAtacado.valor);
                quitarCartaDeZona(cAtacado.valor, zonaAtacado);// 
                
            }else if(cAtacado.valor > cAtacante.valor){ //LA CARTA DEL ATACANTE ES MENOR
                atacante.bajarVida(cAtacado.valor-cAtacante.valor);
                quitarCartaDeZona(cAtacante.valor, zonaAtacante); // 
                
            }else{
                System.out.println("EMPATE"); //AMBAS CARTAS C DESTRUYEN
            }
        }else{
            System.out.println("NO HAY CARTAS EN LAS ZONAS DE DUELO");
        }
    }
    
    public void robarCarta(Jugador j){
        j.mano.addCarta(j.baraja.extraerCartaArriba());
    }
    
    public void colocarCartaDuelo(Jugador j, int seleccion, ArrayList<Carta> zona){
        //EL JUGADOR COLOCA UNA CARTA DE LAS QUE TIENE EN MANO EN ZONA DE DUELO
        Carta c = j.mano.getCarta(seleccion); // esto si supieramos qué carta elejir
        zona.add(c);
    }
    
    public void quitarCartaDeZona(int valor, ArrayList<Carta> zona) {
        zona.remove(getIndexCartaPorValor(valor, zona));
    }
    
    public void mostrarEstado(){
        System.out.println("***************************************************");
        oponente.mano.imprimirCartasE();
        oponente.mano.imprimirCartasD();
        System.out.println("VIDA OPONENTE: "+oponente.vida);
        this.tablero.imprimirCartas();
        System.out.println("VIDA JUGADOR: "+jugador.vida);
        jugador.mano.imprimirCartasD();
        jugador.mano.imprimirCartasE();
        System.out.println("***************************************************");
        System.out.println("");
    }
    
    public int getIndexCartaPorValor(int valor, ArrayList<Carta> zona){
        boolean esta = false;
        int i = 0;
        for (Carta c : zona) {
            if (c.valor == valor) {
                esta = true;
                break;
            }
            i++;
        }
        if (!esta) {
            System.out.println("La carta con el valor " + valor + " no existe en la baraja");
            i = -1;
        }
        return i;
    }
}
