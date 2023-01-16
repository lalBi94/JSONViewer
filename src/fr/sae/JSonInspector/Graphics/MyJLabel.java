package fr.sae.JSonInspector.Graphics;

import fr.sae.JSonInspector.Settings.Parameters;

import javax.swing.*;
import java.awt.*;

public class MyJLabel extends JLabel {
    /**
     *
     * @param text  le texte à afficher
     * @param color la couleur du texte
     */
    public MyJLabel(String text, Color color) {
        super(text);
        this.setForeground(color);
    }

    /**
     *
     * @param text le texte à afficher
     */
    public MyJLabel(String text) {
        super(text);
        this.setForeground(Parameters.DEFAULT_TEXT_COLOR);
    }
}
