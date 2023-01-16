package fr.sae.JSonInspector.Graphics;

import fr.sae.JSonInspector.Storage.Node;
import java.awt.Color;

/**
 * Représente une ligne dans l'IHM
 */
public class Line extends MyJPanel {
    private boolean show = true;
    private final int depth;
    private final Node node;
    private MyJLabel lastElement;
    private boolean closingElement = false;

    /**
     *
     * @param node  le noeud représenté par cette ligne
     * @param depth la profondeur dans l'arbre
     */
    public Line(Node node, int depth) {
        super();
        this.node = node;
        this.depth = depth;
    }

    /**
     *
     * @param node  le noeud représenté par cette ligne
     * @param str   le texte à afficher sur la ligne
     * @param depth profondeur dans l'arbre
     */
    public Line(Node node, String str, int depth) {
        super();
        this.add(new MyJLabel(str));
        this.node = node;
        this.depth = depth;
    }

    /**
     * Ajoute du texte sur la ligne
     * 
     * @param string le texte à ajouter
     */
    public void add(String string) {
        lastElement = new MyJLabel(string);
        this.add(lastElement);
    }

    /**
     * Ajoute du texte sur la ligne
     * 
     * @param string le texte à ajouter
     * @param color  la couleur du texte
     */
    public void add(String string, Color color) {
        lastElement = new MyJLabel(string, color);
        this.add(lastElement);
    }

    public Node getNode() {
        return node;
    }

    public int getDepth() {
        return depth;
    }

    public boolean isShow() {
        return show;
    }

    /**
     * Change le statut de visibilité de la ligne à 'vrai'
     */
    public void unfold() {
        show = true;
    }

    /**
     * Change le statut de visibilité de la ligne à 'faux'
     */
    public void retreat() {
        show = false;
    }

    /**
     * Définit si la ligne représente la fin d'un noeud
     */
    public void setClosingElement() {
        closingElement = true;
    }

    /**
     * Test si la ligne représente la fin d'un noeud
     * 
     * @return
     */
    public boolean isClosingElement() {
        return closingElement;
    }

    /**
     * Enlève le dernier élément de la ligne
     */
    public void removeClosingLabel() {
        try {
            if (lastElement.getText().equals("...]") || lastElement.getText().equals("...}")) {
                this.remove(lastElement);
            }
        } catch (NullPointerException e) {
            // System.out.println("La ligne est vide");
        }
    }
}
