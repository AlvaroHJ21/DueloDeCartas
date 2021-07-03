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
public class Tablero {

    public int nCartas;
    public ArrayList<Carta> zonaDueloJugador;
    public ArrayList<Carta> zonaDueloOponente;
    public ArrayList<Carta> zonaEspecialJugador;
    public ArrayList<Carta> zonaEspecialOponente;

    public Tablero() {
        this.zonaDueloJugador = new ArrayList<Carta>();
        this.zonaDueloOponente = new ArrayList<Carta>();
        this.zonaEspecialJugador = new ArrayList<Carta>();
        this.zonaEspecialOponente = new ArrayList<Carta>();
    }

    public void imprimirCartas() {
        System.out.println("-----------ESPECIAL 2-----------");
        for (Carta carta : this.zonaEspecialOponente) {
            System.out.print(" " + carta.valor);
        }
        System.out.println("");

        System.out.println("-----------DUELO 2-----------");
        for (Carta carta : this.zonaDueloOponente) {
            System.out.print(" " + carta.valor+"("+carta.valorExtra+")");
        }
        System.out.println("");

        System.out.println("-----------DUELO 1-----------");
        for (Carta carta : this.zonaDueloJugador) {
            System.out.print(" " + carta.valor+"("+carta.valorExtra+")");
        }
        System.out.println("");

        System.out.println("-----------ESPECIAL 1-----------");
        for (Carta carta : this.zonaEspecialJugador) {
            System.out.print(" " + carta.valor);
        }
        System.out.println("");
    }
    
    

    public void quitarCartaDeZona(int valor, ArrayList<Carta> zona) {
        zona.remove(getIndexCarta(valor, zona));
    }

    public int getIndexCarta(int valor, ArrayList<Carta> zona) {
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
