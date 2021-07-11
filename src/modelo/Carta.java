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
public class Carta {

    public int valor;
    public String tipo; //duelo magia trampa
    public String estado; // oculto activado destruido
    public String posicion; //baraja mano tablero
    public Carta cartaPotenciadora;
    public int valorExtra;

    public Carta(int valor) {
        this.valor = valor;
        if (1 <= valor && valor <= 3) {
            this.tipo = "magia";
        } else if (4 <= valor && valor <= 10) {
            this.tipo = "duelo";
        } else {
            this.tipo = "trampa";
        }
        this.valorExtra = 0;
        this.estado = "oculto";
    }

    //solo funciona para las cartas especiales
    public void potenciar() {
        if (cartaPotenciadora != null) {
            switch (cartaPotenciadora.valor) {
                //CARTAS MAGICAS
                case 1: //Añade +4 de ataque a una carta de duelo -SIRVE PARA RESPONDER
                    this.valorExtra += 1;
                    break;
                case 2: //Permite atacar 2 veces (C DESTRUYE AL USARSE)
                    this.valorExtra += 2;
                    break;
                case 3: //El oponente no puede atacar en el siguiente turno (C DESTRUYE AL USARSE)
                    this.valorExtra += 3;
                    break;
                //CARTAS TRAMPA
                case 11://Destruye una carta de duelo de tu oponente (C DESTRUYE AL USARSE)
                    this.valorExtra += 4;
                    break;
                case 12: //Añade +2 de ataque a una carta de duelo
                    this.valorExtra += 5;
                    break;
                case 13: //Añade +3 de ataque a una carta de duelo
                    this.valorExtra += 6;
                    break;
            }
            cartaPotenciadora.estado = "activado";
        }else{
            System.out.println("No hay carta potenciadora");
        }

    }
    
    public int getValorAbsoluto(){
        return this.valor+this.valorExtra;
    }

    @Override
    public String toString() {
        if(this.valorExtra==0){
            return ""+this.valor;
        }else{
            return ""+this.valor+"("+this.valorExtra+")";
        }
    }
}
