/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import modelo.BtnCarta;
import modelo.Carta;
import modelo.Juego;
import modelo.Jugador;

/**
 *
 * @author Alvaro
 */
public class DialogoRespuesta extends javax.swing.JFrame {

    JFInicio inicio;
    Juego juego;
    BtnCarta btnCartaAtacante;
    BtnCarta btnCartaAtacada;
    int x, y;
    private int ANCHO_CARTA = 60;
    private int LARGO_CARTA = 80;

    ArrayList<BtnCarta> btnCartasTrampa;

    /**
     * Creates new form DialogoRespuesta
     */
    public DialogoRespuesta() {
        initComponents();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public DialogoRespuesta(JFInicio inicio, Juego juego, BtnCarta btnCartaAtacante, BtnCarta btnCartaAtacada) {
        initComponents();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.inicio = inicio;
        this.juego = juego;
        this.btnCartaAtacante = btnCartaAtacante;
        this.btnCartaAtacada = btnCartaAtacada;
        btnCartasTrampa = new ArrayList<BtnCarta>();

        actualizarVista();
    }

    public void actualizarVista() {
        this.txtAtacante.setText(this.txtAtacante.getText() + this.btnCartaAtacante.carta.valor);
        this.txtAtacada.setText(this.txtAtacada.getText() + this.btnCartaAtacada.carta.valor);
        if (juego.turno == 1) {
            actualizarCartasTablero(juego.tablero.zonaEspecialJugador, panelCartasTrampa, gBtnEspecial, btnCartasTrampa);
        } else {
            actualizarCartasTablero(juego.tablero.zonaEspecialOponente, panelCartasTrampa, gBtnEspecial, btnCartasTrampa);
        }
    }

    private void actualizarCartasTablero(ArrayList<Carta> cartas, JPanel panel, ButtonGroup grupo, ArrayList<BtnCarta> btnCartas) {
        panel.removeAll();
        btnCartas.clear();
        x = 0;
        y = 0;
        for (Carta c : cartas) {
            if(c.tipo.equals("trampa")){
                BtnCarta btnCarta = new BtnCarta(c, x, y, LARGO_CARTA, ANCHO_CARTA, true);
                panel.add(btnCarta);
                grupo.add(btnCarta);
                btnCarta.updateUI();
                btnCartas.add(btnCarta);
                x += LARGO_CARTA;
            }
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

    public void activarCartaTrampa() {
        int resultado = -1;
        BtnCarta cartaSelect = getBtnCartaSeleccionada(btnCartasTrampa);
        if (cartaSelect != null) {
            juego.activarCartaTrampa(cartaSelect.carta.valor, btnCartaAtacada.carta.valor, btnCartaAtacante.carta.valor);
            this.dispose();
            inicio.actualizarJuego();
            JOptionPane.showMessageDialog(null, "Carta activada!!");
            if (cartaSelect.carta.valor != 11) {
                if (juego.turno == 1) {
                    resultado = juego.duelo(juego.jugador, btnCartaAtacada.carta, juego.oponente, btnCartaAtacante.carta);
                    mostrarResultado(resultado);
                } else {
                    resultado = juego.duelo(juego.oponente, btnCartaAtacada.carta, juego.jugador, btnCartaAtacante.carta);
                    mostrarResultadoInv(resultado);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Carta destruida");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una carta trampa");
        }

    }

    public void mostrarResultado(int resultado) {
        inicio.actualizarJuego();
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
    }
    
    public void mostrarResultadoInv(int resultado) {
        inicio.actualizarJuego();
        switch (resultado) {
            case 1:
                JOptionPane.showMessageDialog(this, "El duelo lo ganó el jugador 2");
                break;
            case 2:
                JOptionPane.showMessageDialog(this, "El duelo lo ganó el jugador 1");
                break;
            case 0:
                JOptionPane.showMessageDialog(this, "Ambos empataron");
                break;
            default:
                System.out.println("ERROROROROROROR");
                break;
        }
    }

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

    private void cancelar() {
        this.dispose();
        int resultado = -1;
        if (juego.turno == 1) {
            resultado = juego.duelo(juego.jugador, btnCartaAtacada.carta, juego.oponente, btnCartaAtacante.carta);
            mostrarResultado(resultado);
        } else {
            resultado = juego.duelo(juego.oponente, btnCartaAtacada.carta, juego.jugador, btnCartaAtacante.carta);
            mostrarResultado(resultado);
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

        gbtnDuelo = new javax.swing.ButtonGroup();
        gBtnEspecial = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panelCartasTrampa = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        txtAtacante = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtAtacada = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("TE QUIEREN ATACAR BRO");

        jLabel2.setText("Tienes cartas TRAMPA que puedes usar");

        javax.swing.GroupLayout panelCartasTrampaLayout = new javax.swing.GroupLayout(panelCartasTrampa);
        panelCartasTrampa.setLayout(panelCartasTrampaLayout);
        panelCartasTrampaLayout.setHorizontalGroup(
            panelCartasTrampaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 255, Short.MAX_VALUE)
        );
        panelCartasTrampaLayout.setVerticalGroup(
            panelCartasTrampaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 71, Short.MAX_VALUE)
        );

        jButton1.setText("ACTIVAR!!");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtAtacante.setText("Carta oponente: ");

        jLabel4.setText("DUELO");

        txtAtacada.setText("Tu carta: ");

        jButton2.setText("CANCELAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(panelCartasTrampa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(10, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(91, 91, 91)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(txtAtacante)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(txtAtacada)))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAtacante)
                    .addComponent(jLabel4)
                    .addComponent(txtAtacada))
                .addGap(35, 35, 35)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelCartasTrampa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.activarCartaTrampa();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.cancelar();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(DialogoRespuesta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogoRespuesta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogoRespuesta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogoRespuesta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DialogoRespuesta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup gBtnEspecial;
    private javax.swing.ButtonGroup gbtnDuelo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel panelCartasTrampa;
    private javax.swing.JLabel txtAtacada;
    private javax.swing.JLabel txtAtacante;
    // End of variables declaration//GEN-END:variables
}
