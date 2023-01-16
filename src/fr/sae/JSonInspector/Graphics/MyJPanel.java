package fr.sae.JSonInspector.Graphics;

import fr.sae.JSonInspector.Settings.Parameters;

import javax.swing.*;
import java.awt.*;

public class MyJPanel extends JPanel {
    /**
     * Construit un JPanel qui aligne tous ses éléments sur la gauche
     * 
     * @param color la couleur de fond du panel
     */
    public MyJPanel(Color color) {
        super();
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.setBackground(color);
    }

    /**
     * Construit un JPanel qui aligne tous ses éléments sur la gauche
     */
    public MyJPanel() {
        super();
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.setBackground(Parameters.BACKGROUND_COLOR);
    }
}
