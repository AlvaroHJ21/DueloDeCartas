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
        
        this.btnCartasTableroDuelo1 = new ArrayList<BtnCarta>();
        this.btnCartasTableroDuelo2 = new ArrayList<BtnCarta>();
        this.btnCartasTableroEspecial1 = new ArrayList<BtnCarta>();
        this.btnCartasTableroEspecial2 = new ArrayList<BtnCarta>();
        
        
    }

    private void actualizarJuego() {
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
        
        actualizarCartasGrupoPanel(juego.jugador.mano.cartasD, panelManoDuelo1, gBtnMano1);
        actualizarCartasGrupoPanel(juego.jugador.mano.cartasE, panelManoEspecial1, gBtnMano1);
        actualizarCartasGrupoPanel(juego.oponente.mano.cartasD, panelManoDuelo2, gBtnMano2);
        actualizarCartasGrupoPanel(juego.oponente.mano.cartasE, panelManoEspecial2, gBtnMano2);

        this.actualizarVisibilidad();
        this.actualizarCartasTablero();

        this.juego.mostrarEstado();
    }

    public void addCartaPanel(Carta c, JPanel panel, ButtonGroup grupo, int x, int y, boolean textVisible) {
        BtnCarta btnCarta = new BtnCarta(c, x, y, ANCHO_CARTA, LARGO_CARTA, textVisible);
        grupo.add(btnCarta);
        panel.add(btnCarta);
        btnCarta.updateUI();
    }

    private void actualizarCartasGrupoPanel(ArrayList<Carta> cartas, JPanel panel, ButtonGroup grupo) {
        x = 0;
        y = 0;
        panel.removeAll();
        for (Carta c : cartas) {
            addCartaPanel(c, panel, grupo, x, y, true);
            x += ANCHO_CARTA;
        }
        panel.updateUI();
    }

    public void addCartaTablero(Carta c, JPanel panel, ButtonGroup grupo, ArrayList<BtnCarta> btnCartas, int x, int y, boolean textVisible) {
        BtnCarta btnCarta = new BtnCarta(c, x, y, LARGO_CARTA, ANCHO_CARTA, textVisible);
        btnCartas.add(btnCarta);
        grupo.add(btnCarta);
        panel.add(btnCarta);
        btnCarta.updateUI();
    }

    private void actualizarCartasTablero() {
        x = 0;
        y = 0;
        panelTablero2.removeAll();
        btnCartasTableroEspecial2.clear();
        btnCartasTableroDuelo2.clear();
        for (Carta c : juego.tablero.zonaEspecialOponente) {
            boolean flag = true;
            if(juego.turno == 1){
                flag = false;
            }
            addCartaTablero(c, panelTablero2, gBtnTableroEspecial2, btnCartasTableroEspecial2, x, y, flag);
            x += LARGO_CARTA;
        }
        x = 0;
        y = ANCHO_CARTA;
        for (Carta c : juego.tablero.zonaDueloOponente) {
            addCartaTablero(c, panelTablero2, gBtnTableroDuelo2, btnCartasTableroDuelo2, x, y, true);
            x += LARGO_CARTA;
        }
        panelTablero2.updateUI();

        x = 0;
        y = 0;
        panelTablero1.removeAll();
        btnCartasTableroDuelo1.clear();
        btnCartasTableroEspecial1.clear();
        for (Carta c : juego.tablero.zonaDueloJugador) {
            addCartaTablero(c, panelTablero1, gBtnTableroDuelo1, btnCartasTableroDuelo1, x, y, true);
            x += LARGO_CARTA;
        }
        x = 0;
        y = ANCHO_CARTA;
        for (Carta c : juego.tablero.zonaEspecialJugador) {
            boolean flag = true;
            if(juego.turno == 2){
                flag = false;
            }
            addCartaTablero(c, panelTablero1, gBtnTableroEspecial1, btnCartasTableroEspecial1, x, y, flag);
            x += LARGO_CARTA;
        }
        panelTablero1.updateUI();
    }
    
    private BtnCarta getBtnCartaSeleccionada(ArrayList<BtnCarta> btnCartas){
        BtnCarta btnCarta = null;
        for(BtnCarta b: btnCartas){
            if(b.isSelected()){
                btnCarta = b;
                break;
            }
        }
        return btnCarta;
    }
    
    void mostrarBtnCartas(ArrayList<BtnCarta> btnCartas){
        for(BtnCarta btn: btnCartas){
            System.out.print(btn.carta.valor+" ");
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
    
    private void setVisibilidadBtnCartas(ArrayList<BtnCarta> btnCartas, boolean visible){
        for(BtnCarta b: btnCartas){
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
        if (juego.turno == 1) {
            this.juego.robarCarta(this.juego.jugador);
        } else if (juego.turno == 2) {
            this.juego.robarCarta(this.juego.oponente);
        }
        actualizarJuego();
    }

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
        } else{
            JOptionPane.showMessageDialog(this, "Error");
        }
        gBtnTableroDuelo1.clearSelection();
        gBtnMano1.clearSelection();
        gBtnTableroDuelo2.clearSelection();
        gBtnMano2.clearSelection();
        gBtnTableroEspecial1.clearSelection();
        gBtnTableroEspecial2.clearSelection();

        actualizarJuego();
    }

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
                    int opcion = JOptionPane.showConfirmDialog(this, "Te quieren atacar loko, y tienes cartas que puedes activar, deseas hacerlo?:");
                    if (opcion == JOptionPane.OK_OPTION) {
                        int cartaActivada = Integer.parseInt(JOptionPane.showInputDialog("Carta (especial) que desea activar"));
                        int cartaPotenciada = Integer.parseInt(JOptionPane.showInputDialog("Carta (duelo) que desea potenciar"));

                        juego.pasarTurno();//2->1
                        juego.atacarCarta(juego.jugador, valor1, juego.oponente, valor2, true, cartaActivada, cartaPotenciada);
                        juego.pasarTurno();//1->2
                    } else {
                        JOptionPane.showMessageDialog(this, "aea jodete");
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
                    int opcion = JOptionPane.showConfirmDialog(this, "Te quieren atacar loko, y tienes cartas que puedes activar, deseas hacerlo?:");
                    if (opcion == JOptionPane.OK_OPTION) {
                        int cartaActivada = Integer.parseInt(JOptionPane.showInputDialog("Carta (especial) que desea activar"));
                        int cartaPotenciada = Integer.parseInt(JOptionPane.showInputDialog("Carta (duelo) que desea potenciar"));

                        juego.pasarTurno();//1->2
                        juego.atacarCarta(juego.oponente, valor2, juego.jugador, valor1, true, cartaActivada, cartaPotenciada);
                        juego.pasarTurno();//2->1
                    } else {
                        JOptionPane.showMessageDialog(this, "aea jodete");
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
    }

    private void activarCartaEspecial() {
        int cartaActiva = 0;
        int cartaPotenciada = 0;
        if (juego.turno == 1) {
            cartaActiva = getValorJToggleButton(gBtnTableroEspecial1);
            cartaPotenciada = getValorJToggleButton(gBtnTableroDuelo1);
            juego.activarCarta(juego.jugador, cartaActiva, cartaPotenciada);
        } else if (juego.turno == 2) {
            cartaActiva = getValorJToggleButton(gBtnTableroEspecial2);
            cartaPotenciada = getValorJToggleButton(gBtnTableroDuelo2);
            juego.activarCarta(juego.oponente, cartaActiva, cartaPotenciada);
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
        panelTablero2 = new javax.swing.JPanel();
        panelTablero1 = new javax.swing.JPanel();

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
        jSeparator1.setBounds(10, 210, 320, 10);
        panelTablero.add(jSeparator2);
        jSeparator2.setBounds(10, 150, 320, 10);

        btnColocar.setText("Colocar");
        btnColocar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnColocarActionPerformed(evt);
            }
        });
        panelTablero.add(btnColocar);
        btnColocar.setBounds(230, 160, 100, 24);

        btnRobarCarta.setText("RobarCarta");
        btnRobarCarta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRobarCartaActionPerformed(evt);
            }
        });
        panelTablero.add(btnRobarCarta);
        btnRobarCarta.setBounds(10, 180, 100, 24);

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
        btnRepartir.setBounds(10, 160, 99, 24);

        btnPasarTurno.setText("Pasar turno");
        btnPasarTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasarTurnoActionPerformed(evt);
            }
        });
        panelTablero.add(btnPasarTurno);
        btnPasarTurno.setBounds(120, 180, 100, 24);

        btnAtacar.setText("¡Atacar!");
        btnAtacar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtacarActionPerformed(evt);
            }
        });
        panelTablero.add(btnAtacar);
        btnAtacar.setBounds(120, 160, 100, 24);
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
        btnActivar.setBounds(230, 180, 100, 24);

        javax.swing.GroupLayout panelTablero2Layout = new javax.swing.GroupLayout(panelTablero2);
        panelTablero2.setLayout(panelTablero2Layout);
        panelTablero2Layout.setHorizontalGroup(
            panelTablero2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );
        panelTablero2Layout.setVerticalGroup(
            panelTablero2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );

        panelTablero.add(panelTablero2);
        panelTablero2.setBounds(0, 0, 330, 140);

        javax.swing.GroupLayout panelTablero1Layout = new javax.swing.GroupLayout(panelTablero1);
        panelTablero1.setLayout(panelTablero1Layout);
        panelTablero1Layout.setHorizontalGroup(
            panelTablero1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );
        panelTablero1Layout.setVerticalGroup(
            panelTablero1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );

        panelTablero.add(panelTablero1);
        panelTablero1.setBounds(0, 220, 330, 140);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panelTablero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                        .addComponent(jScrollPane1)
                        .addComponent(jScrollPane3)
                        .addComponent(jScrollPane4))
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 830, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(panelTablero, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        scroll.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRepartirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRepartirMouseClicked
        //this.actualizarMano();
    }//GEN-LAST:event_btnRepartirMouseClicked

    private void btnRobarCartaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRobarCartaActionPerformed
        this.robarCarta();
        this.btnRobarCarta.setEnabled(false);
    }//GEN-LAST:event_btnRobarCartaActionPerformed

    private void btnRepartirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRepartirActionPerformed
        this.actualizarJuego();

    }//GEN-LAST:event_btnRepartirActionPerformed

    private void btnColocarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnColocarActionPerformed
        this.colocarCarta();
    }//GEN-LAST:event_btnColocarActionPerformed

    private void btnPasarTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasarTurnoActionPerformed
        juego.pasarTurno();
        this.btnRobarCarta.setEnabled(true);
        this.actualizarJuego();
    }//GEN-LAST:event_btnPasarTurnoActionPerformed

    private void btnAtacarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtacarActionPerformed
        this.atacar();

    }//GEN-LAST:event_btnAtacarActionPerformed

    private void btnActivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActivarActionPerformed
        this.activarCartaEspecial();
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
    private javax.swing.JPanel panelTablero1;
    private javax.swing.JPanel panelTablero2;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JLabel txtVidaJugador1;
    private javax.swing.JLabel txtVidaJugador2;
    // End of variables declaration//GEN-END:variables

}
