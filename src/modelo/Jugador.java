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
    
    public ArrayList<Carta> zonaD;
    public ArrayList<Carta> zonaE;
    
    public Jugador(String nombre){
        this.nombre = nombre;
        this.baraja = new Baraja();
        this.mano = new Mano();
        this.vida = 10;
        this.turno = true;
    }
    
    public void bajarVida(int descuento){
        this.vida-=descuento;
    }
}
