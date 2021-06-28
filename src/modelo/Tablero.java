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
    public ArrayList<Carta> duelo1;
    public ArrayList<Carta> duelo2;
    public ArrayList<Carta> especial1;
    public ArrayList<Carta> especial2;

    public Tablero() {
        this.duelo1 = new ArrayList<Carta>();
        this.duelo2 = new ArrayList<Carta>();
        this.especial1 = new ArrayList<Carta>();
        this.especial2 = new ArrayList<Carta>();
    }

    public void imprimirCartas() {
        System.out.println("CARTAS UBICADAS EN EL TABLERO");
        System.out.println("-----------ESPECIAL 2-----------");
        for (Carta carta : this.especial2) {
            System.out.println("Valor: " + carta.valor + " Tipo: " + carta.tipo);
        }
        System.out.println("");

        System.out.println("-----------DUELO 2-----------");
        for (Carta carta : this.duelo2) {
            System.out.println("Valor: " + carta.valor + " Tipo: " + carta.tipo);
        }
        System.out.println("");

        System.out.println("-----------DUELO 1-----------");
        for (Carta carta : this.duelo1) {
            System.out.println("Valor: " + carta.valor + " Tipo: " + carta.tipo);
        }
        System.out.println("");

        System.out.println("-----------ESPECIAL 1-----------");
        for (Carta carta : this.especial1) {
            System.out.println("Valor: " + carta.valor + " Tipo: " + carta.tipo);
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
