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

    //METODO ATACARCARTA:
    //atacante -> Jugador que va atacar en su turno
    //i -> carta con la que quiere atacar
    //atacado -> Jugador que va ser atacado
    //j -> carta a la que se quiere atacar
    //responder -> true:si el atacado quiere contraatacar (validar antes de ejecutar esta funcion en la parte grafica)
    //cartaActivada -> Carta Especial que se va activar
    //cartaPotenciada -> Carta de Duelo que va ser potenciada con la carta espedial
    /*
    public void atacarCarta(Jugador atacante, int i, Jugador atacado, int j, boolean responder, int cartaActivada, int cartaPotenciada) {
        //PARA PODER ATACER SE NECESITAN TENER CARTAS EN LA ZONA DE DUELO 1
        if (tablero.zonaDueloJugador.size() > 0 && tablero.zonaDueloOponente.size() > 0) {

            if (responder) {
                activarCarta(atacado, cartaActivada, cartaPotenciada);
            }

            //SELECCIONANDO CARTA DEL ATACANTE
            Carta cAtacante = atacante.zonaD.get(getIndexCartaPorValor(i, atacante.zonaD));
            //SELECCIONANDO CARTA DEL ATACADO
            Carta cAtacado = atacado.zonaD.get(getIndexCartaPorValor(j, atacado.zonaD));

            if (cAtacante.getValorAbsoluto() > cAtacado.getValorAbsoluto()) { //LA CARTA DEL ATACANTE ES MAYOR
                atacado.bajarVida(cAtacante.getValorAbsoluto() - cAtacado.getValorAbsoluto());
                quitarCartaDeZona(cAtacado.valor, atacado.zonaD);

            } else if (cAtacado.getValorAbsoluto() > cAtacante.getValorAbsoluto()) { //LA CARTA DEL ATACANTE ES MENOR
                atacante.bajarVida(cAtacado.getValorAbsoluto() - cAtacante.getValorAbsoluto());
                quitarCartaDeZona(cAtacante.valor, atacante.zonaD);

            } else {
                System.out.println("EMPATE"); //AMBAS CARTAS C DESTRUYEN
                quitarCartaDeZona(cAtacado.valor, atacado.zonaD);
                quitarCartaDeZona(cAtacante.valor, atacante.zonaD);
            }
        } else {
            System.out.println("NO HAY CARTAS EN LAS ZONAS DE DUELO");
        }
    }*/
    
    public int duelo(Jugador jugadorA, Carta cartaA, Jugador jugadorB, Carta cartaB){
        if(cartaA.getValorAbsoluto()>cartaB.getValorAbsoluto()){
            quitarCartaDeZona(cartaB.valor, jugadorB.zonaD);
            jugadorB.bajarVida(cartaA.getValorAbsoluto()-cartaB.getValorAbsoluto());
            return 1;
        }else if(cartaA.getValorAbsoluto()<cartaB.getValorAbsoluto()){
            quitarCartaDeZona(cartaA.valor, jugadorA.zonaD);
            jugadorA.bajarVida(cartaB.getValorAbsoluto()-cartaA.getValorAbsoluto());
            return 2;
        }else{
            quitarCartaDeZona(cartaB.valor, jugadorB.zonaD);
            quitarCartaDeZona(cartaA.valor, jugadorA.zonaD);
            System.out.println("EMPATE");
            return 0;
        }
    }

    public boolean robarCarta() {
        if (turno == 1) {
            if (jugador.mano.getNumeroCartas() < 5) {
                Carta c = jugador.baraja.extraerCartaArriba();
                if (c != null) {
                    jugador.mano.addCarta(c);
                    jugador.maxCartasRobar--;
                    return true;
                } else {
                    System.out.println("NO HAY MAS CARTAS");
                    return false;
                }
            } else {
                System.out.println("NO PUEDES TENER MÁS DE 5 CARTAS EN TU MANO, CRACK");
                return false;
            }
        } else if (turno == 2) {
            if (oponente.mano.getNumeroCartas() < 5) {
                Carta c = oponente.baraja.extraerCartaArriba();
                if (c != null) {
                    oponente.mano.addCarta(c);
                    oponente.maxCartasRobar--;
                    return true;
                } else {
                    System.out.println("NO HAY MAS CARTAS");
                    return false;
                }
            } else {
                System.out.println("NO PUEDES TENER MÁS DE 5 CARTAS EN TU MANO, CRACK");
                return false;
            }
        }
        return false;
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

    public boolean colocarCarta(int valor) {
        if (turno == 1) {
            if (valor >= 4 && valor <= 10) {
                if (tablero.zonaDueloJugador.size() >= 3) {
                    return false;
                }
                Carta c = jugador.mano.getCarta(valor);
                tablero.zonaDueloJugador.add(c);
                return true;
            } else {
                if (tablero.zonaEspecialJugador.size() >= 3) {
                    return false;
                }
                Carta c = jugador.mano.getCarta(valor);
                tablero.zonaEspecialJugador.add(c);
                return true;
            }
        } else {
            if (valor >= 4 && valor <= 10) {
                if (tablero.zonaDueloOponente.size() >= 3) {
                    return false;
                }
                Carta c = oponente.mano.getCarta(valor);
                tablero.zonaDueloOponente.add(c);
                return true;
            } else {
                if (tablero.zonaEspecialOponente.size() >= 3) {
                    return false;
                }
                Carta c = oponente.mano.getCarta(valor);
                tablero.zonaEspecialOponente.add(c);
                return true;
            }
        }
    }

    public void quitarCartaDeZona(int valor, ArrayList<Carta> zona) {
        int index = getIndexCartaPorValor(valor, zona);
        if (index != -1) {
            zona.remove(index);
        } else {
            System.out.println("Error");
        }
    }
    
    /*

    public void activarCarta(Jugador jugador, int vCartaActiva, int vCartaPotenciada) {
        int index1 = getIndexCartaPorValor(vCartaActiva, jugador.zonaE);
        int index2 = getIndexCartaPorValor(vCartaPotenciada, jugador.zonaD);
        if (index1 != -1 && index2 != -1) {
            Carta c1 = jugador.zonaE.get(index1);
            Carta c2 = jugador.zonaD.get(index2);
            c2.cartaPotenciadora = c1;
            c2.potenciar();

            quitarCartaDeZona(vCartaActiva, jugador.zonaE);
            System.out.println("CARTA POTENCIADAAA!!");
        } else {
            System.out.println("Error no cuenta con cartas para realizar esa acción");
        }
    }*/

    /*--------------------------------------------------------------------------------*/
    public void activarCartaTrampa(int cartaTrampa, int cartaAtacada, int cartaAtacante) {
        switch (cartaTrampa) {
            case 11: //DESTRUYE LA CARTA ATACANTE
                destruirCartaDueloTablero(cartaAtacante);
                quitarCartaEspecialUsada(11);
                break;
            case 12: //AÑADE +2
                agregarValorExtra(cartaAtacada, 2);
                quitarCartaEspecialUsada(12);
                break;
            case 13: //AÑADE +3
                agregarValorExtra(cartaAtacada, 3);
                quitarCartaEspecialUsada(13);
                break;
            default:
                System.out.println("ERRORORORORORO");
                break;
        }
    }

    public void quitarCartaEspecialUsada(int carta) {
        if (turno == 1) {
            quitarCartaDeZona(carta, tablero.zonaEspecialJugador);
        } else {
            quitarCartaDeZona(carta, tablero.zonaEspecialOponente);
        }
    }

    public void destruirCartaDueloTablero(int carta) {
        if (turno == 1) {
            quitarCartaDeZona(carta, tablero.zonaDueloOponente);
        } else {
            quitarCartaDeZona(carta, tablero.zonaDueloJugador);
        }
    }

    public void agregarValorExtra(int carta, int valorExtra) {
        if (turno == 1) {
            Carta c = getCartaPorValor(carta, this.tablero.zonaDueloJugador);
            c.valorExtra = valorExtra;
        } else {
            Carta c = getCartaPorValor(carta, this.tablero.zonaDueloOponente);
            c.valorExtra = valorExtra;
        }
    }

    /*--------------------------------------------------------------------------------*/
    /*--------------------------------------------------------------------------------*/
    public void activarCartaMagica(int cartaMagica, int cartaDuelo){
        switch (cartaMagica) {
            case 1: //Añade +4 de ataque a una carta de duelo
                agregarValorExtra(cartaDuelo, 4);
                quitarCartaEspecialUsada(1);
                break;
            case 2: //Permite atacar 2 veces
                break;
            case 3: //El oponente no puede atacar en el siguiente turno
                break;
            default:
                System.out.println("ERRORORORORORO");
                break;
        }
    }
    /*--------------------------------------------------------------------------------*/
    
    
    
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

    public boolean hayCartasTrampaOcultas(ArrayList<Carta> zona) {
        int i = 0;
        for (Carta c : zona) {
//            if (c.estado.equals("oculto") && (c.valor >= 11 && c.valor <= 13)) { //SOLO CARTAS TRAMPA
                if (c.estado.equals("oculto") && (c.tipo.equals("trampa"))) { //SOLO CARTAS TRAMPA
                i++;
            }
        }
        if (i > 0) {
            System.out.println("CARTASSSSSSSSSSSSSSS -> " + i);
            System.out.println(zona.size());
            System.out.println(zona.get(0).valor);
            return true;
        }
        return false;
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

    public Carta getCartaPorValor(int valor, ArrayList<Carta> cartas) {
        for (Carta c : cartas) {
            if (c.valor == valor) {
                return c;
            }
        }
        return null;
    }
}
