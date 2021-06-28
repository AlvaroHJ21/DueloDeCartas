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
    
    public Carta(int valor){
        this.valor = valor;
        if(1<=valor&&valor<=3){
            this.tipo = "magia";
        }else if(4<=valor&&valor<=10){
            this.tipo = "duelo";
        }else{
            this.tipo = "trampa";
        }
    }
}
