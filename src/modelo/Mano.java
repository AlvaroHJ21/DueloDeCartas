/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Alvaro
 */
public class Mano {

    public int nCartas;
    public ArrayList<Carta> cartasD;
    public ArrayList<Carta> cartasE;

    public Mano() {
        this.cartasD = new ArrayList<Carta>();
        this.cartasE = new ArrayList<Carta>();
    }

    public void addCarta(Carta c) {
        if (c.tipo.equals("duelo")) {
            this.cartasD.add(c);
        } else {
            this.cartasE.add(c);
        }
    }
    
    public int getIndexCarta(int valor, ArrayList<Carta> cartas){
        boolean esta = false;
        int i = 0;
        for(Carta c: cartas){
            if(c.valor==valor){
                esta = true;
                break;
            }
            i++;
        }
        if(!esta){
            System.out.println("La carta con el valor "+valor+" no existe en la baraja");
            i = -1;
        }
        return i;
    }
    
    public Carta getCarta(int valor){
        Carta c = null;
        
        if(4<=valor&&valor<=10){
            int index = getIndexCarta(valor, this.cartasD);
            if(index!=-1){
                c = this.cartasD.remove(index);
            }
        }else{
            int index = getIndexCarta(valor, this.cartasE);
            if(index!=-1){
                c = this.cartasE.remove(index);
            }
        }
        
        return c;
    }
    
    public Carta getCartaDuelo(){ //OBTIENE LA CARTA DE MÁS ARRIBA DE DUELO
        Carta c = null;
        if(this.cartasD.size()>0){
            c = this.cartasD.remove(this.cartasD.size()-1);
        }
        return c;
    }

    public void generarCartasIniciales(Baraja baraja) {
        //escogiendo las cartas de duelo

        ArrayList<Integer> valD = new ArrayList<Integer>();
        valD.add(4);
        valD.add(5);
        valD.add(6);
        valD.add(7);
        valD.add(8);
        valD.add(9);
        valD.add(10);//valores de cartas permitidos

        Random ran = new Random();
        int j;
        Carta c;
        for (int i = 0; i < 4; i++) {
            j = ran.nextInt(valD.size());
            c = baraja.extraerCarta(valD.remove(j)); // obtiene el indice y luego lo desecha
            addCarta(c);
        }

        //escogiendo las cartas especiales
        ArrayList<Integer> valE = new ArrayList<Integer>();
        valE.add(1);
        valE.add(2);
        valE.add(3);
        valE.add(11);
        valE.add(12);
        valE.add(13);//valores de cartas permitidos
        j = ran.nextInt(valE.size());
        c = baraja.extraerCarta(valE.remove(j)); // obtiene el indice y luego lo desecha
        addCarta(c);
    }

    public void imprimirCartasD() {
        System.out.println("DUELO");
        for (Carta carta : this.cartasD) {
            System.out.println("Valor: " + carta.valor);
            System.out.println("Tipo: " + carta.tipo);
        }
        System.out.println("");
    }
    
    public void imprimirCartasE() {
        System.out.println("ESPECIALES");
        for (Carta carta : this.cartasE) {
            System.out.println("Valor: " + carta.valor);
            System.out.println("Tipo: " + carta.tipo);
        }
        System.out.println("");
    }

}
