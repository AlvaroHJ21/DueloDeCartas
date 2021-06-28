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
    
    public Tablero(){
        this.duelo1 = new ArrayList<Carta>();
        this.duelo2 = new ArrayList<Carta>();
        this.especial1 = new ArrayList<Carta>();
        this.especial2 = new ArrayList<Carta>();
    }
    
    public void imprimirCartasDuelo1(){
        System.out.println("DUELO 1");
        for(Carta carta: this.duelo1){
            System.out.println("Valor: "+carta.valor);
            System.out.println("Tipo: "+carta.tipo);
        }
        System.out.println("ESPECIAL 1");
        System.out.println("");
        for(Carta carta: this.especial1){
            System.out.println("Valor: "+carta.valor);
            System.out.println("Tipo: "+carta.tipo);
        }
        System.out.println("");
        System.out.println("DUELO 2");
        for(Carta carta: this.duelo2){
            System.out.println("Valor: "+carta.valor);
            System.out.println("Tipo: "+carta.tipo);
        }
        System.out.println("");
        System.out.println("ESPECIAL 2");
        for(Carta carta: this.especial2){
            System.out.println("Valor: "+carta.valor);
            System.out.println("Tipo: "+carta.tipo);
        }
        System.out.println("");
    }
}
