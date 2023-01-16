package fr.sae.JSonInspector.Graphics;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ArrayObjectListener implements MouseListener {
    private final Line line;
    private final Frame frame;

    /**
     *
     * @param line  La ligne écoutée
     * @param frame la frame contenant la ligne écoutée
     */
    public ArrayObjectListener(Line line, Frame frame) {
        this.line = line;
        this.frame = frame;
    }

    /**
     * Change le statut d'affichage de la ligne
     * 
     * @param e L'event en question
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (line.isShow()) {
            line.retreat();
        } else {
            line.unfold();
        }
        frame.repaintFile();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
