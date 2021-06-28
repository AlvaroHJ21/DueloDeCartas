/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Alvaro
 */
public class Baraja {
    public int nCartas;
    public ArrayList<Carta> cartas;
    
    public Baraja(){
        this.cartas = new ArrayList<Carta>();
    }
    
    public void generarCartas(){
        for(int i=1; i<=13; i++){
            this.cartas.add(new Carta(i));
        }
    }
    
    public int getIndexCarta(int valor){
        boolean esta = false;
        int i = 0;
        for(Carta c: this.cartas){
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
    
    public Carta extraerCarta(int valor){
        Carta c = null;
        if(cartas.size()!=0){
            int index = this.getIndexCarta(valor);
            if(index!=-1){
                c = cartas.remove(index);
            };
        }else{
            System.out.println("Error, no hay cartas");
        }
        return c;
    }
    
    public Carta extraerCartaArriba(){
        Carta c = null;
        if(this.cartas.size()>0){
            c = this.cartas.remove(cartas.size()-1);//POP
        }
        return c;
    }
   
    public void imprimirCartas(){
        for(Carta carta: this.cartas){
            System.out.println("Valor: "+carta.valor);
            System.out.println("Tipo: "+carta.tipo);
        }
        System.out.println("");
    }
    
    public void barajearCartas(){
        Collections.shuffle(this.cartas);
    }
    
}
