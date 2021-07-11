/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import modelo.Carta;
import modelo.Juego;

import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import modelo.BtnCarta;
import modelo.Jugador;

/**
 *
 * @author Alvaro
 */
public class JFInicio extends javax.swing.JFrame {

    /**
     * Creates new form JFInicio
     */
    private Juego juego;

    private int ANCHO_CARTA = 60;
    private int LARGO_CARTA = 80;
    private int x = 0, y = 0;

    private ArrayList<BtnCarta> btnCartasManoDuelo1;
    private ArrayList<BtnCarta> btnCartasManoDuelo2;
    private ArrayList<BtnCarta> btnCartasManoEspecial1;
    private ArrayList<BtnCarta> btnCartasManoEspecial2;

    private ArrayList<BtnCarta> btnCartasTableroDuelo1;
    private ArrayList<BtnCarta> btnCartasTableroDuelo2;
    private ArrayList<BtnCarta> btnCartasTableroEspecial1;
    private ArrayList<BtnCarta> btnCartasTableroEspecial2;

    public JFInicio() {
        initComponents();
        this.juego = new Juego();
        this.juego.inicializar();
        this.setLocationRelativeTo(null);
        //setMatrix();
        this.panelManoDuelo2.setPreferredSize(new Dimension(ANCHO_CARTA * 7, LARGO_CARTA));
        this.panelManoEspecial1.setPreferredSize(new Dimension(ANCHO_CARTA * 6, LARGO_CARTA));

        this.btnCartasManoDuelo1 = new ArrayList<BtnCarta>();
        this.btnCartasManoDuelo2 = new ArrayList<BtnCarta>();
        this.btnCartasManoEspecial1 = new ArrayList<BtnCarta>();
        this.btnCartasManoEspecial2 = new ArrayList<BtnCarta>();
        this.btnCartasTableroDuelo1 = new ArrayList<BtnCarta>();
        this.btnCartasTableroDuelo2 = new ArrayList<BtnCarta>();
        this.btnCartasTableroEspecial1 = new ArrayList<BtnCarta>();
        this.btnCartasTableroEspecial2 = new ArrayList<BtnCarta>();

    }

    public void actualizarJuego() {
        //actualizando labels
        this.txtVidaJugador1.setText(String.valueOf(juego.jugador.vida));
        this.txtVidaJugador2.setText(String.valueOf(juego.oponente.vida));
        if (juego.jugador.vida <= 0) {
            JOptionPane.showMessageDialog(this, "GANÓ EL JUGADOR 2");
            System.exit(0);
        } else if (juego.oponente.vida <= 0) {
            JOptionPane.showMessageDialog(this, "GANÓ EL JUGADOR 1");
            System.exit(0);
        }

        actualizarCartasMano(juego.jugador.mano.cartasD, panelManoDuelo1, gBtnMano1, btnCartasManoDuelo1);
        actualizarCartasMano(juego.jugador.mano.cartasE, panelManoEspecial1, gBtnMano1, btnCartasManoEspecial1);
        actualizarCartasMano(juego.oponente.mano.cartasD, panelManoDuelo2, gBtnMano2, btnCartasManoDuelo2);
        actualizarCartasMano(juego.oponente.mano.cartasE, panelManoEspecial2, gBtnMano2, btnCartasManoEspecial2);

        this.actualizarVisibilidad();
        this.actualizarCartasTablero(juego.tablero.zonaDueloJugador, panelTableroDuelo1, gBtnTableroDuelo1, btnCartasTableroDuelo1);
        this.actualizarCartasTablero(juego.tablero.zonaEspecialJugador, panelTableroEspecial1, gBtnTableroEspecial1, btnCartasTableroEspecial1);
        this.actualizarCartasTablero(juego.tablero.zonaDueloOponente, panelTableroDuelo2, gBtnTableroDuelo2, btnCartasTableroDuelo2);
        this.actualizarCartasTablero(juego.tablero.zonaEspecialOponente, panelTableroEspecial2, gBtnTableroEspecial2, btnCartasTableroEspecial2);
        this.juego.mostrarEstado();

        this.actualizarBotones();
    }

    /*-------------------------------------------------------------------------------------------------------*/
    private void actualizarCartasMano(ArrayList<Carta> cartas, JPanel panel, ButtonGroup grupo, ArrayList<BtnCarta> btnCartas) {
        panel.removeAll();
        btnCartas.clear();
        x = 0;
        y = 0;
        for (Carta c : cartas) {
            BtnCarta btnCarta = new BtnCarta(c, x, y, ANCHO_CARTA, LARGO_CARTA, true);
            panel.add(btnCarta);
            grupo.add(btnCarta);
            btnCarta.updateUI();
            btnCartas.add(btnCarta);
            x += ANCHO_CARTA;
        }
        panel.updateUI();
    }

    private void actualizarCartasTablero(ArrayList<Carta> cartas, JPanel panel, ButtonGroup grupo, ArrayList<BtnCarta> btnCartas) {
        panel.removeAll();
        btnCartas.clear();
        x = 0;
        y = 0;
        for (Carta c : cartas) {
            boolean flag = true;
            if ((juego.turno == 1 && panel.equals(panelTableroEspecial2)) || (juego.turno == 2 && panel.equals(panelTableroEspecial1))) {
                flag = false;
            }
            BtnCarta btnCarta = new BtnCarta(c, x, y, LARGO_CARTA, ANCHO_CARTA, flag);
            panel.add(btnCarta);
            grupo.add(btnCarta);
            btnCarta.updateUI();
            btnCartas.add(btnCarta);
            x += LARGO_CARTA;
        }
        panel.updateUI();
    }

    /*-------------------------------------------------------------------------------------------------------*/
    public void addCartaTablero(Carta c, JPanel panel, ButtonGroup grupo, ArrayList<BtnCarta> btnCartas, int x, int y, boolean textVisible) {
        BtnCarta btnCarta = new BtnCarta(c, x, y, LARGO_CARTA, ANCHO_CARTA, textVisible);
        btnCartas.add(btnCarta);
        grupo.add(btnCarta);
        panel.add(btnCarta);
        btnCarta.updateUI();
    }

    public void actualizarBotones() {
        if (juego.turno == 1) {
            if (juego.jugador.maxCartasRobar <= 0) {
                this.btnRobarCarta.setEnabled(false);
            } else {
                this.btnRobarCarta.setEnabled(true);
            }
        } else {
            if (juego.oponente.maxCartasRobar <= 0) {
                this.btnRobarCarta.setEnabled(false);
            } else {
                this.btnRobarCarta.setEnabled(true);
            }
        }
    }

    /*
    private void actualizarCartasTablero() {
        x = 0;
        y = 0;
        panelTableroEspecial2.removeAll();
        btnCartasTableroEspecial2.clear();
        btnCartasTableroDuelo2.clear();
        for (Carta c : juego.tablero.zonaEspecialOponente) {
            boolean flag = true;
            if(juego.turno == 1){
                flag = false;
            }
            addCartaTablero(c, panelTableroEspecial2, gBtnTableroEspecial2, btnCartasTableroEspecial2, x, y, flag);
            x += LARGO_CARTA;
        }
        x = 0;
        y = ANCHO_CARTA;
        for (Carta c : juego.tablero.zonaDueloOponente) {
            addCartaTablero(c, panelTableroEspecial2, gBtnTableroDuelo2, btnCartasTableroDuelo2, x, y, true);
            x += LARGO_CARTA;
        }
        panelTableroEspecial2.updateUI();

        x = 0;
        y = 0;
        panelTableroDuelo1.removeAll();
        btnCartasTableroDuelo1.clear();
        btnCartasTableroEspecial1.clear();
        for (Carta c : juego.tablero.zonaDueloJugador) {
            addCartaTablero(c, panelTableroDuelo1, gBtnTableroDuelo1, btnCartasTableroDuelo1, x, y, true);
            x += LARGO_CARTA;
        }
        x = 0;
        y = ANCHO_CARTA;
        for (Carta c : juego.tablero.zonaEspecialJugador) {
            boolean flag = true;
            if(juego.turno == 2){
                flag = false;
            }
            addCartaTablero(c, panelTableroDuelo1, gBtnTableroEspecial1, btnCartasTableroEspecial1, x, y, flag);
            x += LARGO_CARTA;
        }
        panelTableroDuelo1.updateUI();
    }*/

 /*------------------------------------------------------------------------------*/
    private BtnCarta getBtnCartaSeleccionada(ArrayList<BtnCarta> btnCartasTableroDuelo) {
        BtnCarta btnCarta = null;
        for (BtnCarta b : btnCartasTableroDuelo) {
            if (b.isSelected()) {
                btnCarta = b;
                break;
            }
        }
        return btnCarta;
    }

    private BtnCarta getBtnCartaSeleccionada(ArrayList<BtnCarta> btnCartasManoDuelo, ArrayList<BtnCarta> btnCartasManoEspeciales) {
        BtnCarta btn = null;
        for (BtnCarta b : btnCartasManoDuelo) {
            if (b.isSelected()) {
                btn = b;
            }
        }
        for (BtnCarta b : btnCartasManoEspeciales) {
            if (b.isSelected()) {
                btn = b;
            }
        }
        return btn;
    }

    /*------------------------------------------------------------------------------*/
    void mostrarBtnCartas(ArrayList<BtnCarta> btnCartas) {
        for (BtnCarta btn : btnCartas) {
            System.out.print(btn.carta.valor + " ");
        }
        System.out.println("");
    }

    private void actualizarVisibilidad() {
        if (juego.turno == 1) {
            setVisibleBotones(gBtnMano1, true);
            setVisibleBotones(gBtnMano2, false);
            setVisibleBotones(gBtnTableroEspecial2, false);
            setVisibleBotones(gBtnTableroEspecial1, true);
            //ocultar cartas especiales del jugador 2
            setVisibilidadBtnCartas(this.btnCartasTableroEspecial2, false);
            setVisibilidadBtnCartas(this.btnCartasTableroEspecial1, true);

        } else if (juego.turno == 2) {
            setVisibleBotones(gBtnMano2, true);
            setVisibleBotones(gBtnMano1, false);
            setVisibleBotones(gBtnTableroEspecial1, false);
            setVisibleBotones(gBtnTableroEspecial2, true);
            //ocultar cartas especiales del jugador 1
            setVisibilidadBtnCartas(this.btnCartasTableroEspecial2, true);
            setVisibilidadBtnCartas(this.btnCartasTableroEspecial1, false);
        }
    }

    private void setVisibilidadBtnCartas(ArrayList<BtnCarta> btnCartas, boolean visible) {
        for (BtnCarta b : btnCartas) {
            b.setVisibilidadValor(visible);
        }
    }

    private void setVisibleBotones(ButtonGroup grupo, boolean visible) {
        for (Enumeration<AbstractButton> buttons = grupo.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            button.setVisible(visible);
        }
    }

    private void robarCarta() {
        if (!this.juego.robarCarta()) {
            JOptionPane.showMessageDialog(null, "Error: Ya no hay cartas o tienes >=5 cartas en tu mano");
            this.btnRobarCarta.setEnabled(false);
        } else {
            this.btnRobarCarta.setEnabled(true);
        }
        actualizarJuego();
    }

    private void colocarCarta() {
        BtnCarta btnSelect;
        if (juego.turno == 1) {
            btnSelect = getBtnCartaSeleccionada(btnCartasManoDuelo1, btnCartasManoEspecial1);
        } else {
            btnSelect = getBtnCartaSeleccionada(btnCartasManoDuelo2, btnCartasManoEspecial2);
        }
        if (btnSelect != null) {
            int valor = btnSelect.carta.valor;
            if (!juego.colocarCarta(valor)) {
                JOptionPane.showMessageDialog(null, "No hay espacio, broo...");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una carta capo");
        }
        gBtnTableroDuelo1.clearSelection();
        gBtnMano1.clearSelection();
        gBtnTableroDuelo2.clearSelection();
        gBtnMano2.clearSelection();
        gBtnTableroEspecial1.clearSelection();
        gBtnTableroEspecial2.clearSelection();

        actualizarJuego();
    }

    /*
    private void colocarCarta() {
        if (juego.turno == 1) {
            int valor = getValorJToggleButton(gBtnMano1);
            if (valor != 0) {
                //System.out.println(valor);
                if (4 <= valor && valor <= 10) {
                    if (!juego.colocarCartaEnZona(juego.jugador, valor, juego.tablero.zonaDueloJugador)) {
                        JOptionPane.showMessageDialog(this, "No hay espacio en el tablero");
                    } else {
                        setJToggleButton(gBtnTableroDuelo1, valor);
                    }
                } else {
                    if (!juego.colocarCartaEnZona(juego.jugador, valor, juego.tablero.zonaEspecialJugador)) {
                        JOptionPane.showMessageDialog(this, "No hay espacio en el tablero");
                    } else {
                        setJToggleButton(gBtnTableroEspecial1, valor);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una carta, CAPO");
            }

        } else if (juego.turno == 2) {
            int valor = getValorJToggleButton(gBtnMano2);
            if (valor != 0) {
                System.out.println(valor);
                if (4 <= valor && valor <= 10) {
                    if (!juego.colocarCartaEnZona(juego.oponente, valor, juego.tablero.zonaDueloOponente)) {
                        JOptionPane.showMessageDialog(this, "No hay espacio en el tablero");
                    } else {
                        setJToggleButton(gBtnTableroDuelo2, valor);
                    }
                } else {
                    if (!juego.colocarCartaEnZona(juego.oponente, valor, juego.tablero.zonaEspecialOponente)) {
                        JOptionPane.showMessageDialog(this, "No hay espacio en el tablero");
                    } else {
                        setJToggleButton(gBtnTableroEspecial2, valor);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una carta, CAPO");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Error");
        }
        gBtnTableroDuelo1.clearSelection();
        gBtnMano1.clearSelection();
        gBtnTableroDuelo2.clearSelection();
        gBtnMano2.clearSelection();
        gBtnTableroEspecial1.clearSelection();
        gBtnTableroEspecial2.clearSelection();

        actualizarJuego();
    }*/
    private void abrirDialogo(BtnCarta btnAtacante, BtnCarta btnAtacada) {
        DialogoRespuesta dialogo = new DialogoRespuesta(this, this.juego, btnAtacante, btnAtacada);
        dialogo.setLocationRelativeTo(null);
        dialogo.setVisible(true);
    }

    private void atacar() {
        BtnCarta btnCarta1 = this.getBtnCartaSeleccionada(btnCartasTableroDuelo1);
        BtnCarta btnCarta2 = this.getBtnCartaSeleccionada(btnCartasTableroDuelo2);
        int resultado = -1;
        if (btnCarta1 != null && btnCarta2 != null) {
            if (juego.turno == 1) {
                if (juego.hayCartasTrampaOcultas(juego.tablero.zonaEspecialOponente)) {
                    this.pasarTurno();
                    abrirDialogo(btnCarta1, btnCarta2);
                } else {
                    resultado = juego.duelo(juego.jugador, btnCarta1.carta, juego.oponente, btnCarta2.carta);
                    mostrarResultado(resultado);
                }
            } else {
                if (juego.hayCartasTrampaOcultas(juego.tablero.zonaEspecialJugador)) {
                    this.pasarTurno();
                    abrirDialogo(btnCarta2, btnCarta1);
                } else {
                    resultado = juego.duelo(juego.jugador, btnCarta1.carta, juego.oponente, btnCarta2.carta);
                    mostrarResultado(resultado);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Se requiere seleccionar dos cartas");
        }
    }

    public void mostrarResultado(int resultado) {
        actualizarJuego();
        switch (resultado) {
            case 1:
                JOptionPane.showMessageDialog(this, "El duelo lo ganó el jugador 1");
                break;
            case 2:
                JOptionPane.showMessageDialog(this, "El duelo lo ganó el jugador 2");
                break;
            case 0:
                JOptionPane.showMessageDialog(this, "Ambos empataron");
                break;
            default:
                System.out.println("ERROROROROROROR");
                break;
        }
        pasarTurno();
    }

    /*

    private void atacar() {
        BtnCarta btnCarta1 = this.getBtnCartaSeleccionada(btnCartasTableroDuelo1);
        BtnCarta btnCarta2 = this.getBtnCartaSeleccionada(btnCartasTableroDuelo2);

        if (btnCarta1 != null && btnCarta2 != null) {
            int valor1 = btnCarta1.carta.valor;
            int valor2 = btnCarta2.carta.valor;
            if (juego.turno == 1) {
                //verificar que el atacado (2) tenga cartas en zona de especial
                if (juego.hayCartasEspecialesOcultas(juego.tablero.zonaEspecialOponente)) {
                    juego.pasarTurno();//1->2
                    this.actualizarJuego();
                    
                    int opcion = JOptionPane.showConfirmDialog(this, "Te quieren atacar, y tienes cartas que puedes activar, deseas hacerlo?:");

                    if (opcion == JOptionPane.OK_OPTION) {
                        int cartaActivada = Integer.parseInt(JOptionPane.showInputDialog("Carta (especial) que desea activar"));
                        int cartaPotenciada = Integer.parseInt(JOptionPane.showInputDialog("Carta (duelo) que desea potenciar"));

                        juego.pasarTurno();//2->1
                        juego.atacarCarta(juego.jugador, valor1, juego.oponente, valor2, true, cartaActivada, cartaPotenciada);
                        juego.pasarTurno();//1->2
                    } else {
                        JOptionPane.showMessageDialog(this, "ok");
                        juego.pasarTurno();//2->1
                        juego.atacarCarta(juego.jugador, valor1, juego.oponente, valor2, false, 0, 0);
                        juego.pasarTurno();//1->2
                    }
                } else {
                    juego.atacarCarta(juego.jugador, valor1, juego.oponente, valor2, false, 0, 0);
                    juego.pasarTurno();//1->2
                }

            } else if (juego.turno == 2) {
                //verificar que el atacado (1) tenga cartas en zona de especial
                if (juego.hayCartasEspecialesOcultas(juego.tablero.zonaEspecialJugador)) {
                    juego.pasarTurno();//2->1
                    this.actualizarJuego();
                    int opcion = JOptionPane.showConfirmDialog(this, "Te quieren atacar, y tienes cartas que puedes activar, deseas hacerlo?:");
                    if (opcion == JOptionPane.OK_OPTION) {
                        int cartaActivada = Integer.parseInt(JOptionPane.showInputDialog("Carta (especial) que desea activar"));
                        int cartaPotenciada = Integer.parseInt(JOptionPane.showInputDialog("Carta (duelo) que desea potenciar"));

                        juego.pasarTurno();//1->2
                        juego.atacarCarta(juego.oponente, valor2, juego.jugador, valor1, true, cartaActivada, cartaPotenciada);
                        juego.pasarTurno();//2->1
                    } else {
                        JOptionPane.showMessageDialog(this, "ok");
                        juego.pasarTurno();//1->2
                        juego.atacarCarta(juego.oponente, valor2, juego.jugador, valor1, false, 0, 0);
                        juego.pasarTurno();//2->1
                    }
                } else {
                    juego.atacarCarta(juego.oponente, valor2, juego.jugador, valor1, false, 0, 0);
                    juego.pasarTurno();//1->2
                }
            } else {
                System.out.println("Error!!!!");
            }
            this.actualizarJuego();
        } else {
            JOptionPane.showMessageDialog(this, "Se requiere seleccionar dos cartas");
        }
    }*/
    private void activarCartaMagica() {
        if (juego.turno == 1) {
            BtnCarta cartaMagica = getBtnCartaSeleccionada(btnCartasTableroEspecial1);
            BtnCarta cartaDuelo = getBtnCartaSeleccionada(btnCartasTableroDuelo1);
            if (cartaMagica != null && cartaMagica.carta.valor == 1 && cartaDuelo != null) {
                juego.activarCartaMagica(cartaMagica.carta.valor, cartaDuelo.carta.valor);
                JOptionPane.showMessageDialog(null, "Carta Magica Activada!!");
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione una carta magica");
            }
        } else {
            BtnCarta cartaMagica = getBtnCartaSeleccionada(btnCartasTableroEspecial2);
            BtnCarta cartaDuelo = getBtnCartaSeleccionada(btnCartasTableroDuelo2);
            if (cartaMagica != null && cartaMagica.carta.valor == 1 && cartaDuelo != null) {
                juego.activarCartaMagica(cartaMagica.carta.valor, cartaDuelo.carta.valor);
                JOptionPane.showMessageDialog(null, "Carta Magica Activada!!");
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione una carta magica");
            }
        }

    }

    private int getValorJToggleButton(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                if (!button.getText().equals("")) {
                    return Integer.parseInt(button.getText());
                }
            }
        }
        return 0;
    }

    private void pasarTurno() {
        juego.pasarTurno();
        juego.jugador.setMax(1, 1, 1);
        juego.oponente.setMax(1, 1, 1);
        this.btnRobarCarta.setEnabled(true);
        this.actualizarJuego();
    }

    private void setJToggleButton(ButtonGroup buttonGroup, int valor) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                button.setText(String.valueOf(valor));
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gBtnTableroDuelo1 = new javax.swing.ButtonGroup();
        gBtnMano1 = new javax.swing.ButtonGroup();
        gBtnMano2 = new javax.swing.ButtonGroup();
        gBtnTableroDuelo2 = new javax.swing.ButtonGroup();
        gBtnTableroEspecial1 = new javax.swing.ButtonGroup();
        gBtnTableroEspecial2 = new javax.swing.ButtonGroup();
        scroll = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        panelManoDuelo1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        panelManoEspecial2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelManoEspecial1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelManoDuelo2 = new javax.swing.JPanel();
        panelTablero = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        btnColocar = new javax.swing.JButton();
        btnRobarCarta = new javax.swing.JButton();
        btnRepartir = new javax.swing.JButton();
        btnPasarTurno = new javax.swing.JButton();
        btnAtacar = new javax.swing.JButton();
        txtVidaJugador2 = new javax.swing.JLabel();
        txtVidaJugador1 = new javax.swing.JLabel();
        btnActivar = new javax.swing.JButton();
        panelTableroEspecial2 = new javax.swing.JPanel();
        panelTableroDuelo2 = new javax.swing.JPanel();
        panelTableroDuelo1 = new javax.swing.JPanel();
        panelTableroEspecial1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(500, 600));

        panelManoDuelo1.setLayout(null);
        jScrollPane4.setViewportView(panelManoDuelo1);

        panelManoEspecial2.setLayout(null);
        jScrollPane3.setViewportView(panelManoEspecial2);

        panelManoEspecial1.setLayout(null);
        jScrollPane2.setViewportView(panelManoEspecial1);

        panelManoDuelo2.setLayout(null);
        jScrollPane1.setViewportView(panelManoDuelo2);

        panelTablero.setLayout(null);
        panelTablero.add(jSeparator1);
        jSeparator1.setBounds(0, 190, 320, 10);
        panelTablero.add(jSeparator2);
        jSeparator2.setBounds(0, 130, 320, 10);

        btnColocar.setText("Colocar");
        btnColocar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnColocarActionPerformed(evt);
            }
        });
        panelTablero.add(btnColocar);
        btnColocar.setBounds(220, 140, 100, 24);

        btnRobarCarta.setText("RobarCarta");
        btnRobarCarta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRobarCartaActionPerformed(evt);
            }
        });
        panelTablero.add(btnRobarCarta);
        btnRobarCarta.setBounds(0, 160, 100, 24);

        btnRepartir.setText("Actualizar");
        btnRepartir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRepartirMouseClicked(evt);
            }
        });
        btnRepartir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRepartirActionPerformed(evt);
            }
        });
        panelTablero.add(btnRepartir);
        btnRepartir.setBounds(0, 140, 99, 24);

        btnPasarTurno.setText("Pasar turno");
        btnPasarTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasarTurnoActionPerformed(evt);
            }
        });
        panelTablero.add(btnPasarTurno);
        btnPasarTurno.setBounds(110, 160, 100, 24);

        btnAtacar.setText("¡Atacar!");
        btnAtacar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtacarActionPerformed(evt);
            }
        });
        panelTablero.add(btnAtacar);
        btnAtacar.setBounds(110, 140, 100, 24);
        panelTablero.add(txtVidaJugador2);
        txtVidaJugador2.setBounds(340, 70, 40, 20);
        panelTablero.add(txtVidaJugador1);
        txtVidaJugador1.setBounds(340, 280, 40, 20);

        btnActivar.setText("Activar");
        btnActivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActivarActionPerformed(evt);
            }
        });
        panelTablero.add(btnActivar);
        btnActivar.setBounds(220, 160, 100, 24);

        javax.swing.GroupLayout panelTableroEspecial2Layout = new javax.swing.GroupLayout(panelTableroEspecial2);
        panelTableroEspecial2.setLayout(panelTableroEspecial2Layout);
        panelTableroEspecial2Layout.setHorizontalGroup(
            panelTableroEspecial2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );
        panelTableroEspecial2Layout.setVerticalGroup(
            panelTableroEspecial2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        panelTablero.add(panelTableroEspecial2);
        panelTableroEspecial2.setBounds(0, 0, 330, 60);

        javax.swing.GroupLayout panelTableroDuelo2Layout = new javax.swing.GroupLayout(panelTableroDuelo2);
        panelTableroDuelo2.setLayout(panelTableroDuelo2Layout);
        panelTableroDuelo2Layout.setHorizontalGroup(
            panelTableroDuelo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );
        panelTableroDuelo2Layout.setVerticalGroup(
            panelTableroDuelo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        panelTablero.add(panelTableroDuelo2);
        panelTableroDuelo2.setBounds(0, 70, 330, 60);

        javax.swing.GroupLayout panelTableroDuelo1Layout = new javax.swing.GroupLayout(panelTableroDuelo1);
        panelTableroDuelo1.setLayout(panelTableroDuelo1Layout);
        panelTableroDuelo1Layout.setHorizontalGroup(
            panelTableroDuelo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );
        panelTableroDuelo1Layout.setVerticalGroup(
            panelTableroDuelo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        panelTablero.add(panelTableroDuelo1);
        panelTableroDuelo1.setBounds(0, 200, 330, 60);

        javax.swing.GroupLayout panelTableroEspecial1Layout = new javax.swing.GroupLayout(panelTableroEspecial1);
        panelTableroEspecial1.setLayout(panelTableroEspecial1Layout);
        panelTableroEspecial1Layout.setHorizontalGroup(
            panelTableroEspecial1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );
        panelTableroEspecial1Layout.setVerticalGroup(
            panelTableroEspecial1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        panelTablero.add(panelTableroEspecial1);
        panelTableroEspecial1.setBounds(0, 270, 330, 60);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                    .addComponent(panelTablero, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTablero, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        scroll.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRepartirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRepartirMouseClicked
        //this.actualizarMano();
    }//GEN-LAST:event_btnRepartirMouseClicked

    private void btnRobarCartaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRobarCartaActionPerformed
        this.robarCarta();
        //this.btnRobarCarta.setEnabled(false);
    }//GEN-LAST:event_btnRobarCartaActionPerformed

    private void btnRepartirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRepartirActionPerformed
        this.actualizarJuego();

    }//GEN-LAST:event_btnRepartirActionPerformed

    private void btnColocarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnColocarActionPerformed
        this.colocarCarta();
    }//GEN-LAST:event_btnColocarActionPerformed

    private void btnPasarTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasarTurnoActionPerformed
        this.pasarTurno();
    }//GEN-LAST:event_btnPasarTurnoActionPerformed

    private void btnAtacarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtacarActionPerformed
        this.atacar();
    }//GEN-LAST:event_btnAtacarActionPerformed

    private void btnActivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActivarActionPerformed
        this.activarCartaMagica();
        this.actualizarJuego();
    }//GEN-LAST:event_btnActivarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFInicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActivar;
    private javax.swing.JButton btnAtacar;
    private javax.swing.JButton btnColocar;
    private javax.swing.JButton btnPasarTurno;
    private javax.swing.JButton btnRepartir;
    private javax.swing.JButton btnRobarCarta;
    private javax.swing.ButtonGroup gBtnMano1;
    private javax.swing.ButtonGroup gBtnMano2;
    private javax.swing.ButtonGroup gBtnTableroDuelo1;
    private javax.swing.ButtonGroup gBtnTableroDuelo2;
    private javax.swing.ButtonGroup gBtnTableroEspecial1;
    private javax.swing.ButtonGroup gBtnTableroEspecial2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel panelManoDuelo1;
    private javax.swing.JPanel panelManoDuelo2;
    private javax.swing.JPanel panelManoEspecial1;
    private javax.swing.JPanel panelManoEspecial2;
    private javax.swing.JPanel panelTablero;
    private javax.swing.JPanel panelTableroDuelo1;
    private javax.swing.JPanel panelTableroDuelo2;
    private javax.swing.JPanel panelTableroEspecial1;
    private javax.swing.JPanel panelTableroEspecial2;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JLabel txtVidaJugador1;
    private javax.swing.JLabel txtVidaJugador2;
    // End of variables declaration//GEN-END:variables

}
