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
public class Jugador {
    public String nombre;
    public Baraja baraja;
    public Mano mano;
    public int vida;
    
    public Jugador(String nombre){
        this.nombre = nombre;
        this.baraja = new Baraja();
        this.mano = new Mano();
        this.vida = 10;
    }
    
    public void robarCarta(){
        this.mano.addCarta(this.baraja.extraerCartaArriba());
    }
    
    public void colocarCarta(int valor, ArrayList<Carta> zona){
        //EL JUGADOR COLOCA UNA CARTA DE LAS QUE TIENE EN MANO EN SU RESPECTIVA ZONA
        Carta c;
        c = this.mano.getCarta(valor);
        zona.add(c);
    }
    
    public void atacar(){
        
    }
    
    public void pasarTurno(){
        
    }
    
    
    
    public void bajarVida(int descuento){
        this.vida-=descuento;
    }
}
