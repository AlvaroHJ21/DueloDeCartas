/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author Alvaro
 */
public class Juego {

    public Tablero tablero;
    public Jugador jugador;
    public Jugador oponente;
    public int turno = 0; //1: jugador 2: oponente

    public Juego() {
        tablero = new Tablero();
        jugador = new Jugador("Jugador 1");
        oponente = new Jugador("Jugador 2");
    }

    public void inicializar() {
        jugador.baraja.generarCartas();
        jugador.mano.generarCartasIniciales(jugador.baraja);
        jugador.baraja.barajearCartas();
        jugador.zonaD = tablero.zonaDueloJugador;
        jugador.zonaE = tablero.zonaEspecialJugador;

        oponente.baraja.generarCartas();
        oponente.mano.generarCartasIniciales(oponente.baraja);
        oponente.baraja.barajearCartas();
        oponente.zonaD = tablero.zonaDueloOponente;
        oponente.zonaE = tablero.zonaEspecialOponente;

        //DECIDIR QUIEN EMPIEZA
        //supongamos que empiezas tú
        turno = new Random().nextInt(2) + 1;
        if (turno == 1) {
            jugador.turno = true;
            oponente.turno = false;
        } else {
            jugador.turno = false;
            oponente.turno = true;
        }

    }

    public void atacarCarta(Jugador atacante, int i, Jugador atacado, int j) {
        //PARA PODER ATACER SE NECESITAN TENER CARTAS EN LA ZONA DE DUELO 1
        if (tablero.zonaDueloJugador.size() > 0 && tablero.zonaDueloOponente.size() > 0) {
            //AVISAR AL JUGADOR QUE SERÁ ATACADO
            if (atacado.zonaE.size() > 0) {
                String opcion = JOptionPane.showInputDialog("El otro jugador quiere atacarte deseas activar una carta?");
                if (opcion.equals("si")) {
                    int v = Integer.parseInt(JOptionPane.showInputDialog("QUE CARTA QUIERES ACTIVAR"));
                    int a = Integer.parseInt(JOptionPane.showInputDialog("A QUE CARTA QUIERES POTENCIAR"));
                    activarCarta(atacado, v, a);
                } else {
                    System.out.println("Ok");
                }
            }

            //SELECCIONANDO CARTA DEL JUGADOR
            Carta cAtacante = atacante.zonaD.get(getIndexCartaPorValor(i, atacante.zonaD));
            //SELECCIONANDO CARTA DEL OPONENTE
            Carta cAtacado = atacado.zonaD.get(getIndexCartaPorValor(j, atacado.zonaD));

            if (cAtacante.getValorAbsoluto() > cAtacado.getValorAbsoluto()) { //LA CARTA DEL ATACANTE ES MAYOR
                atacado.bajarVida(cAtacante.getValorAbsoluto() - cAtacado.getValorAbsoluto());
                quitarCartaDeZona(cAtacado.valor, atacado.zonaD);// 

            } else if (cAtacado.getValorAbsoluto() > cAtacante.getValorAbsoluto()) { //LA CARTA DEL ATACANTE ES MENOR
                atacante.bajarVida(cAtacado.getValorAbsoluto() - cAtacante.getValorAbsoluto());
                quitarCartaDeZona(cAtacante.valor, atacante.zonaD); // 

            } else {
                System.out.println("EMPATE"); //AMBAS CARTAS C DESTRUYEN
                quitarCartaDeZona(cAtacado.valor, atacado.zonaD);
                quitarCartaDeZona(cAtacante.valor, atacante.zonaD);
            }
        } else {
            System.out.println("NO HAY CARTAS EN LAS ZONAS DE DUELO");
        }
    }

    public void robarCarta(Jugador j) {
        Carta c = j.baraja.extraerCartaArriba();
        if (c != null) {
            j.mano.addCarta(c);
        } else {
            System.out.println("NO HAY MAS CARTAS");
        }
    }

    public boolean colocarCartaEnZona(Jugador j, int seleccion, ArrayList<Carta> zona) {
        //EL JUGADOR COLOCA UNA CARTA DE LAS QUE TIENE EN MANO EN ZONA DE DUELO
        if (zona.size() >= 3) {
            return false;
        }
        Carta c = j.mano.getCarta(seleccion);
        zona.add(c);
        return true;
    }

    public void quitarCartaDeZona(int valor, ArrayList<Carta> zona) {
        zona.remove(getIndexCartaPorValor(valor, zona));
    }

    public void activarCarta(Jugador jugador, int vCartaActiva, int vCartaPotenciada) {
        int index1 = getIndexCartaPorValor(vCartaActiva, jugador.zonaE);
        int index2 = getIndexCartaPorValor(vCartaPotenciada, jugador.zonaD);
        if (index1 != -1 && index2 != -1) {
            Carta c1 = jugador.zonaE.get(index1);
            Carta c2 = jugador.zonaD.get(index2);
            c2.cartaPotenciadora = c1;
            c2.potenciar();
            quitarCartaDeZona(vCartaActiva, jugador.zonaE);
        } else {
            System.out.println("Error no cuenta con cartas para realizar esa acción");
        }
    }

    public void pasarTurno() {
        if (turno == 1) {
            turno = 2;
            jugador.turno = false;
            oponente.turno = true;
        } else {
            turno = 1;
            jugador.turno = true;
            oponente.turno = false;
        }
    }

    public void mostrarEstado() {
        System.out.println("***************************************************");
        oponente.mano.imprimirCartasE();
        oponente.mano.imprimirCartasD();
        System.out.println("VIDA OPONENTE: " + oponente.vida);
        this.tablero.imprimirCartas();
        System.out.println("VIDA JUGADOR: " + jugador.vida);
        jugador.mano.imprimirCartasD();
        jugador.mano.imprimirCartasE();
        System.out.println("***************************************************");
        System.out.println("Turno del jugador " + turno);
    }

    public int getIndexCartaPorValor(int valor, ArrayList<Carta> zona) {
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
