/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import modelo.Carta;
import modelo.Juego;

import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import modelo.Jugador;

/**
 *
 * @author Alvaro
 */
public class JFInicio extends javax.swing.JFrame {

    /**
     * Creates new form JFInicio
     */
    private int FILS = 2;
    private int COLS = 3;
    JButton[][] CUADRO;
    private Juego juego;

    private int ANCHO_CARTA = 60;
    private int LARGO_CARTA = 80;
    private int xPosCarta = 0, yPosCarta = 0;

    public JFInicio() {
        initComponents();
        this.juego = new Juego();
        this.juego.inicializar();
        this.setLocationRelativeTo(null);
        //setMatrix();
        this.panelMano2.setPreferredSize(new Dimension(ANCHO_CARTA * 7, LARGO_CARTA));
        this.panelManoEspecial1.setPreferredSize(new Dimension(ANCHO_CARTA * 6, LARGO_CARTA));
    }

    public void addCartaPanelManoDuelo(Jugador j, Carta c, int x, int y) {
        if (juego.jugador.equals(j)) {
            JToggleButton btnCarta = new JToggleButton();
            btnCarta.setText(c.toString());
            btnCarta.setBounds(xPosCarta, yPosCarta, ANCHO_CARTA, LARGO_CARTA);
            this.gBtnMano1.add(btnCarta);
            this.panelMano1.add(btnCarta);
            btnCarta.updateUI();//refresca la vista
        } else if (juego.oponente.equals(j)) {
            JToggleButton btnCarta = new JToggleButton();
            btnCarta.setText(c.toString());
            btnCarta.setBounds(xPosCarta, yPosCarta, ANCHO_CARTA, LARGO_CARTA);
            this.gBtnMano2.add(btnCarta);
            this.panelMano2.add(btnCarta);
            btnCarta.updateUI();//refresca la vista
        } else {
            System.out.println("Error!!!!");
        }

    }

    public void addCartaPanelManoEspecial(Jugador j, Carta c, int x, int y) {
        if (juego.jugador.equals(j)) {
            JToggleButton btnCarta = new JToggleButton();
            btnCarta.setText(c.toString());
            btnCarta.setBackground(Color.CYAN);
            btnCarta.setBackground(new Color(204,255,255));
            btnCarta.setBounds(x, y, ANCHO_CARTA, LARGO_CARTA);
            this.gBtnMano1.add(btnCarta);
            this.panelManoEspecial1.add(btnCarta);
            btnCarta.updateUI();//refresca la vista
        } else if (juego.oponente.equals(j)) {
            JToggleButton btnCarta = new JToggleButton();
            btnCarta.setText(c.toString());
            btnCarta.setBackground(new Color(204,255,255));
            btnCarta.setBounds(x, y, ANCHO_CARTA, LARGO_CARTA);
            this.gBtnMano2.add(btnCarta);
            this.panelManoEspecial2.add(btnCarta);
            btnCarta.updateUI();//refresca la vista
        } else {
            System.out.println("Error!!!!");
        }
    }

    private void actualizarJuego() {
        //actualizando labels
        this.txtVidaJugador1.setText(String.valueOf(juego.jugador.vida));
        this.txtVidaJugador2.setText(String.valueOf(juego.oponente.vida));
        if (juego.jugador.vida == 0) {
            JOptionPane.showMessageDialog(this, "GANÓ EL JUGADOR 2");
            System.exit(0);
        } else if (juego.oponente.vida <= 0) {
            JOptionPane.showMessageDialog(this, "GANÓ EL JUGADOR 1");
            System.exit(0);
        }

        //JUGADOR 1
        panelMano1.removeAll();
        xPosCarta = 0;
        yPosCarta = 0;
        this.juego.mostrarEstado();
        //cartas de duelo
        for (Carta c : this.juego.jugador.mano.cartasD) {
            addCartaPanelManoDuelo(juego.jugador, c, xPosCarta, yPosCarta);
            xPosCarta += 60;
        }
        //cartas especiales
        panelManoEspecial1.removeAll();
        xPosCarta = 0;
        yPosCarta = 0;
        for (Carta c : this.juego.jugador.mano.cartasE) {
            addCartaPanelManoEspecial(juego.jugador, c, xPosCarta, yPosCarta);
            xPosCarta += 60;
        }
        panelManoEspecial1.updateUI();
        panelMano1.updateUI();

        //JUGADOR 2
        panelMano2.removeAll();
        xPosCarta = 0;
        yPosCarta = 0;
        this.juego.mostrarEstado();
        //cartas de duelo
        for (Carta c : this.juego.oponente.mano.cartasD) {
            addCartaPanelManoDuelo(juego.oponente, c, xPosCarta, yPosCarta);
            xPosCarta += 60;
        }
        panelManoEspecial2.removeAll();
        xPosCarta = 0;
        yPosCarta = 0;
        //cartas especiales
        for (Carta c : this.juego.oponente.mano.cartasE) {
            addCartaPanelManoEspecial(juego.oponente, c, xPosCarta, yPosCarta);
            xPosCarta += 60;
        }
        panelManoEspecial2.updateUI();
        panelMano2.updateUI();

        this.actualizarVisibilidad();
        this.actualizarCartasTablero();
    }

    private void actualizarCartasZona(ArrayList<Carta> zona, ButtonGroup grupo) {
        int i = 0;
        for (Enumeration<AbstractButton> buttons = grupo.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            button.setText("");
            if (i < zona.size()) {
                String valor = String.valueOf(zona.get(i).valor);
                button.setText(valor);
                i++;
            }
        }
    }

    private void actualizarVisibilidad() {
        if (juego.turno == 1) {
            setVisibleBotones(gBtnMano1, true);
            setVisibleBotones(gBtnMano2, false);
            setVisibleBotones(gBtnTableroEspecial2, false);
            setVisibleBotones(gBtnTableroEspecial1, true);
        } else if (juego.turno == 2) {
            setVisibleBotones(gBtnMano2, true);
            setVisibleBotones(gBtnMano1, false);
            setVisibleBotones(gBtnTableroEspecial1, false);
            setVisibleBotones(gBtnTableroEspecial2, true);
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
        int seleccion = 0;
        if (juego.turno == 1) {
            String valor = getValorJToggleButton(gBtnMano1);
            if (valor != null) {
                System.out.println(valor);
                int v = Integer.parseInt(valor);
                seleccion = Integer.parseInt(valor);
                if (4 <= v && v <= 10) {
                    if (!juego.colocarCartaEnZona(juego.jugador, seleccion, juego.tablero.zonaDueloJugador)) {
                        JOptionPane.showMessageDialog(this, "No hay espacio en el tablero");
                    } else {
                        setJToggleButton(gBtnTableroDuelo1, valor);
                    }
                } else {
                    if (!juego.colocarCartaEnZona(juego.jugador, seleccion, juego.tablero.zonaEspecialJugador)) {
                        JOptionPane.showMessageDialog(this, "No hay espacio en el tablero");
                    } else {
                        setJToggleButton(gBtnTableroEspecial1, valor);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una carta, CAPO");
            }

        } else if (juego.turno == 2) {
            String valor = getValorJToggleButton(gBtnMano2);
            if (valor != null) {
                System.out.println(valor);
                int v = Integer.parseInt(valor);
                seleccion = Integer.parseInt(valor);
                if (4 <= v && v <= 10) {
                    if (!juego.colocarCartaEnZona(juego.oponente, seleccion, juego.tablero.zonaDueloOponente)) {
                        JOptionPane.showMessageDialog(this, "No hay espacio en el tablero");
                    } else {
                        setJToggleButton(gBtnTableroDuelo2, valor);
                    }
                } else {
                    if (!juego.colocarCartaEnZona(juego.oponente, seleccion, juego.tablero.zonaEspecialOponente)) {
                        JOptionPane.showMessageDialog(this, "No hay espacio en el tablero");
                    } else {
                        setJToggleButton(gBtnTableroEspecial2, valor);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona una carta, CAPO");
            }
        }
        gBtnTableroDuelo1.clearSelection();
        gBtnMano1.clearSelection();
        gBtnTableroDuelo2.clearSelection();
        gBtnMano2.clearSelection();
        gBtnTableroEspecial1.clearSelection();
        gBtnTableroEspecial2.clearSelection();

        actualizarJuego();
    }

    private void actualizarCartasTablero() {
        this.actualizarCartasZona(juego.tablero.zonaDueloJugador, gBtnTableroDuelo1);
        this.actualizarCartasZona(juego.tablero.zonaEspecialJugador, gBtnTableroEspecial1);

        this.actualizarCartasZona(juego.tablero.zonaDueloOponente, gBtnTableroDuelo2);
        this.actualizarCartasZona(juego.tablero.zonaEspecialOponente, gBtnTableroEspecial2);
    }

    private void atacar() {
        int i = 0;
        int j = 0;
        String valor1 = getValorJToggleButton(gBtnTableroDuelo1);
        String valor2 = getValorJToggleButton(gBtnTableroDuelo2);
        if (valor1 != null && valor2 != null && !valor1.isEmpty() && !valor2.isEmpty()) {
            if (juego.turno == 1) {
                i = Integer.parseInt(getValorJToggleButton(gBtnTableroDuelo1));
                j = Integer.parseInt(getValorJToggleButton(gBtnTableroDuelo2));
                juego.atacarCarta(juego.jugador, i, juego.oponente, j);
                juego.pasarTurno();
            } else if (juego.turno == 2) {
                i = Integer.parseInt(getValorJToggleButton(gBtnTableroDuelo2));
                j = Integer.parseInt(getValorJToggleButton(gBtnTableroDuelo1));
                juego.atacarCarta(juego.oponente, i, juego.jugador, j);
                juego.pasarTurno();
            } else {
                System.out.println("Error!!!!");
            }
            this.actualizarJuego();
        } else {
            JOptionPane.showMessageDialog(this, "Se requiere seleccionar dos cartas");
        }

    }

    private String getValorJToggleButton(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }

    private void setJToggleButton(ButtonGroup buttonGroup, String texto) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                button.setText(texto);
            }
        }
    }
    
    private void setJToggleButtonAll(ButtonGroup buttonGroup, String texto) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            button.setText(texto);
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
        panelBotones = new javax.swing.JPanel();
        jToggleButton4 = new javax.swing.JToggleButton();
        jToggleButton5 = new javax.swing.JToggleButton();
        jToggleButton7 = new javax.swing.JToggleButton();
        jToggleButton8 = new javax.swing.JToggleButton();
        jToggleButton9 = new javax.swing.JToggleButton();
        jToggleButton10 = new javax.swing.JToggleButton();
        jToggleButton11 = new javax.swing.JToggleButton();
        jToggleButton12 = new javax.swing.JToggleButton();
        jToggleButton13 = new javax.swing.JToggleButton();
        jToggleButton14 = new javax.swing.JToggleButton();
        jToggleButton15 = new javax.swing.JToggleButton();
        jToggleButton16 = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        btnColocar = new javax.swing.JButton();
        btnRobarCarta = new javax.swing.JButton();
        btnRepartir = new javax.swing.JButton();
        btnPasarTurno = new javax.swing.JButton();
        btnAtacar = new javax.swing.JButton();
        txtVidaJugador2 = new javax.swing.JLabel();
        txtVidaJugador1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelMano2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelManoEspecial1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        panelManoEspecial2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        panelMano1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(500, 600));

        panelBotones.setLayout(null);

        jToggleButton4.setBackground(new java.awt.Color(204, 255, 255));
        gBtnTableroEspecial1.add(jToggleButton4);
        panelBotones.add(jToggleButton4);
        jToggleButton4.setBounds(230, 290, 100, 60);

        jToggleButton5.setBackground(new java.awt.Color(204, 255, 255));
        gBtnTableroEspecial1.add(jToggleButton5);
        panelBotones.add(jToggleButton5);
        jToggleButton5.setBounds(10, 290, 100, 60);

        jToggleButton7.setBackground(new java.awt.Color(204, 255, 255));
        gBtnTableroEspecial1.add(jToggleButton7);
        panelBotones.add(jToggleButton7);
        jToggleButton7.setBounds(120, 290, 100, 60);

        gBtnTableroDuelo1.add(jToggleButton8);
        panelBotones.add(jToggleButton8);
        jToggleButton8.setBounds(230, 220, 100, 60);

        gBtnTableroDuelo1.add(jToggleButton9);
        panelBotones.add(jToggleButton9);
        jToggleButton9.setBounds(120, 220, 100, 60);

        gBtnTableroDuelo1.add(jToggleButton10);
        jToggleButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton10ActionPerformed(evt);
            }
        });
        panelBotones.add(jToggleButton10);
        jToggleButton10.setBounds(10, 220, 100, 60);

        gBtnTableroDuelo2.add(jToggleButton11);
        panelBotones.add(jToggleButton11);
        jToggleButton11.setBounds(230, 80, 100, 60);

        gBtnTableroDuelo2.add(jToggleButton12);
        panelBotones.add(jToggleButton12);
        jToggleButton12.setBounds(120, 80, 100, 60);

        gBtnTableroDuelo2.add(jToggleButton13);
        panelBotones.add(jToggleButton13);
        jToggleButton13.setBounds(10, 80, 100, 60);

        jToggleButton14.setBackground(new java.awt.Color(204, 255, 255));
        gBtnTableroEspecial2.add(jToggleButton14);
        panelBotones.add(jToggleButton14);
        jToggleButton14.setBounds(230, 10, 100, 60);

        jToggleButton15.setBackground(new java.awt.Color(204, 255, 255));
        gBtnTableroEspecial2.add(jToggleButton15);
        panelBotones.add(jToggleButton15);
        jToggleButton15.setBounds(120, 10, 100, 60);

        jToggleButton16.setBackground(new java.awt.Color(204, 255, 255));
        gBtnTableroEspecial2.add(jToggleButton16);
        panelBotones.add(jToggleButton16);
        jToggleButton16.setBounds(10, 10, 100, 60);
        panelBotones.add(jSeparator1);
        jSeparator1.setBounds(10, 210, 320, 10);
        panelBotones.add(jSeparator2);
        jSeparator2.setBounds(10, 150, 320, 10);

        btnColocar.setText("Colocar");
        btnColocar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnColocarActionPerformed(evt);
            }
        });
        panelBotones.add(btnColocar);
        btnColocar.setBounds(230, 160, 100, 24);

        btnRobarCarta.setText("RobarCarta");
        btnRobarCarta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRobarCartaActionPerformed(evt);
            }
        });
        panelBotones.add(btnRobarCarta);
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
        panelBotones.add(btnRepartir);
        btnRepartir.setBounds(10, 160, 99, 24);

        btnPasarTurno.setText("Pasar turno");
        btnPasarTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasarTurnoActionPerformed(evt);
            }
        });
        panelBotones.add(btnPasarTurno);
        btnPasarTurno.setBounds(230, 180, 100, 24);

        btnAtacar.setText("¡Atacar!");
        btnAtacar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtacarActionPerformed(evt);
            }
        });
        panelBotones.add(btnAtacar);
        btnAtacar.setBounds(120, 170, 100, 24);
        panelBotones.add(txtVidaJugador2);
        txtVidaJugador2.setBounds(340, 70, 40, 20);
        panelBotones.add(txtVidaJugador1);
        txtVidaJugador1.setBounds(340, 280, 40, 20);

        panelMano2.setLayout(null);
        jScrollPane1.setViewportView(panelMano2);

        panelManoEspecial1.setLayout(null);
        jScrollPane2.setViewportView(panelManoEspecial1);

        panelManoEspecial2.setLayout(null);
        jScrollPane3.setViewportView(panelManoEspecial2);

        panelMano1.setLayout(null);
        jScrollPane4.setViewportView(panelMano1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRepartirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRepartirMouseClicked
        //this.actualizarMano();
    }//GEN-LAST:event_btnRepartirMouseClicked

    private void btnRobarCartaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRobarCartaActionPerformed
        this.robarCarta();
    }//GEN-LAST:event_btnRobarCartaActionPerformed

    private void btnRepartirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRepartirActionPerformed
        this.actualizarJuego();

    }//GEN-LAST:event_btnRepartirActionPerformed

    private void btnColocarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnColocarActionPerformed
        this.colocarCarta();
    }//GEN-LAST:event_btnColocarActionPerformed

    private void jToggleButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton10ActionPerformed

    }//GEN-LAST:event_jToggleButton10ActionPerformed

    private void btnPasarTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasarTurnoActionPerformed
        juego.pasarTurno();
        this.actualizarJuego();
    }//GEN-LAST:event_btnPasarTurnoActionPerformed

    private void btnAtacarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtacarActionPerformed
        this.atacar();

    }//GEN-LAST:event_btnAtacarActionPerformed

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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JToggleButton jToggleButton10;
    private javax.swing.JToggleButton jToggleButton11;
    private javax.swing.JToggleButton jToggleButton12;
    private javax.swing.JToggleButton jToggleButton13;
    private javax.swing.JToggleButton jToggleButton14;
    private javax.swing.JToggleButton jToggleButton15;
    private javax.swing.JToggleButton jToggleButton16;
    private javax.swing.JToggleButton jToggleButton4;
    private javax.swing.JToggleButton jToggleButton5;
    private javax.swing.JToggleButton jToggleButton7;
    private javax.swing.JToggleButton jToggleButton8;
    private javax.swing.JToggleButton jToggleButton9;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelMano1;
    private javax.swing.JPanel panelMano2;
    private javax.swing.JPanel panelManoEspecial1;
    private javax.swing.JPanel panelManoEspecial2;
    private javax.swing.JLabel txtVidaJugador1;
    private javax.swing.JLabel txtVidaJugador2;
    // End of variables declaration//GEN-END:variables

}
