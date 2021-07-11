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
    public boolean turno;
    
    public int maxCartasColocar;
    public int maxCartasRobar;
    public int maxAtaques;
    public boolean puedeAtacar;
    
    public ArrayList<Carta> zonaD;
    public ArrayList<Carta> zonaE;
    
    public Jugador(String nombre){
        this.nombre = nombre;
        this.baraja = new Baraja();
        this.mano = new Mano();
        this.vida = 10;
        this.turno = true;
        
        maxCartasColocar = 1;
        maxCartasRobar = 1;
        maxAtaques = 1;
        puedeAtacar = true;
    }
    
    public void setMax(int max1, int max2, int max3){
        maxCartasColocar = max1;
        maxCartasRobar = max2;
        maxAtaques = max3;
    }
    
    public void bajarVida(int descuento){
        this.vida-=descuento;
    }
}
