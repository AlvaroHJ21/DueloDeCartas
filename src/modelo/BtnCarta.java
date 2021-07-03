/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.awt.Image;
import javax.swing.JToggleButton;

/**
 *
 * @author Alvaro
 */
public class BtnCarta extends JToggleButton {

    public Carta carta;
    //public boolean visible = true; // true: visible para el oponente

    public BtnCarta(Carta carta, int x, int y, int width, int height, boolean textVisible) {
        this.carta = carta;
        this.setBounds(x, y, width, height);
        
        if (textVisible) {
            this.actualizarTexto();
        } else {
            this.setText("");
        }
    }

    public void actualizarTexto() {
        if (carta.valorExtra > 0) {
            this.setText(String.valueOf(this.carta.valor) + "(+" + this.carta.valorExtra + ")");
        } else {
            this.setText(String.valueOf(this.carta.valor));
        }
    }

    public void setVisibilidadValor(boolean visible) {
        if (visible) {
            this.actualizarTexto();
        } else {
            this.setText("");
        }
    }
}
