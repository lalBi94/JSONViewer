package fr.sae.JSonInspector.Graphics;

import fr.sae.JSonInspector.Settings.Parameters;
import fr.sae.JSonInspector.Storage.Node;
import fr.sae.JSonInspector.Storage.Tree;
import fr.sae.JSonInspector.Storage.Type;
import fr.sae.JSonInspector.Storage.Value;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphicFile extends JPanel {
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final JPanel alignementPanel = new JPanel();
    private final Frame frame;
    private final ArrayList<Line> lines;
    private boolean php = false;
    private Node firstNode;

    public GraphicFile(Frame frame, Tree tree, boolean php) {
        super();
        this.php = php;
        firstNode = tree.getFirstNode();
        init();
        lines = new ArrayList<>();
        this.frame = frame;
        createFileRecursive(firstNode, 0, false);
        displayLines();
    }

    public GraphicFile(Frame frame, ArrayList<Line> lines) {
        super();
        init();
        this.frame = frame;
        this.lines = lines;
        // displayAllLines();
        displayLines();
    }

    /**
     * initialise l'objet (permet de ne pas répéter de code dans les constructeurs)
     */
    private void init() {
        this.setBackground(Parameters.BACKGROUND_COLOR);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.WEST;
        alignementPanel.setLayout(new GridBagLayout());
        this.add(alignementPanel);
    }

    /**
     * parcours de manière récursive l'arbre pour créer les lignes
     * 
     * @param node    le noeud à afficher
     * @param depth   la profondeur dans l'arbre
     * @param virgule détermine si une virgule doit être affichée à la fin
     */
    private void createFileRecursive(Node node, int depth, boolean virgule) {
        String indentation = "";

        for (int i = 0; i < depth; i++) {
            indentation += Parameters.IHM_INDENTATION;
        }

        if (node.isObject() || node.isElement()) {
            createObjectElement(node, depth, indentation, virgule);

        } else if (node.isArray()) {
            createArray(node, depth, indentation, virgule);

        } else if (node.isPair()) {
            createPair(node, depth, indentation, virgule);
        }
    }

    /**
     * Créé une ligne de type 'PAIR'
     * 
     * @param node        le noeud a afficher
     * @param depth       la profondeur dans l'arbre
     * @param indentation l'indentation du père
     * @param virgule     détermine si une virgule doit être affichée à la fin
     */
    private void createPair(Node node, int depth, String indentation, boolean virgule) {
        Line line = new Line(node, depth);
        line.add(indentation);
        line.add("\"" + node.getName() + "\"", Parameters.KEY_COLOR);
        if (!php) {
            line.add(": ");
        } else {
            line.add(" => ");
        }

        if (node.getSize() != 0) {
            createValue(line, node.get(0));
        } else {
            line.add("null", Parameters.OTHER_COLOR);
        }

        if (virgule) {
            line.add(",");
        }

        lines.add(line);
    }

    /**
     * Créé une ligne de type 'OBJECT' ou 'ELEMENT'
     * 
     * @param node        le noeud a afficher
     * @param depth       la profondeur dans l'arbre
     * @param indentation l'indentation du père
     * @param virgule     détermine si une virgule doit être affichée à la fin
     */
    private void createObjectElement(Node node, int depth, String indentation, boolean virgule) {
        Line line = new Line(node, depth);
        line.add(indentation);

        if (0 < depth && 0 < node.getSize()) {
            line.retreat();
        }

        if (node.getType() == Type.ELEMENT) {
            if (!php) {
                line.add("{");
            } else {
                line.add("(");
            }

        } else {
            line.add("\"" + node.getName() + "\"", Parameters.KEY_COLOR);
            if (!php) {
                line.add(": {");
            } else {
                line.add(" => (");
            }

        }

        if (node.getSize() == 0) {
            if (virgule) {
                if (!php) {
                    line.add(" },");
                } else {
                    line.add(" ),");
                }

            } else {
                if (!php) {
                    line.add(" }");
                } else {
                    line.add(" )");
                }

            }
            lines.add(line);

        } else {
            line.addMouseListener(new ArrayObjectListener(line, frame));
            lines.add(line);
            callNextNodes(node, depth);
            Line endLine;

            if (!php) {
                endLine = new Line(node, indentation + "}", depth);
            } else {
                endLine = new Line(node, indentation + ")", depth);
            }

            endLine.setClosingElement();

            if (virgule) {
                endLine.add(",");
            }

            lines.add(endLine);
        }
    }

    /**
     * Créé une ligne de type 'ARRAY'
     * 
     * @param node        le noeud a afficher
     * @param depth       la profondeur dans l'arbre
     * @param indentation l'indentation du père
     * @param virgule     détermine si une virgule doit être affichée à la fin
     */
    private void createArray(Node node, int depth, String indentation, boolean virgule) {
        Line line = new Line(node, depth);
        line.add(indentation);

        if (0 < depth && 0 < node.getSize()) {
            line.retreat();
        }

        line.add("\"" + node.getName() + "\"", Parameters.KEY_COLOR);
        if (!php) {
            line.add(": [");
        } else {
            line.add(" => (");
        }

        if (node.getSize() == 0) {
            if (virgule) {
                if (!php) {
                    line.add(" ],");
                } else {
                    line.add(" ),");
                }

            } else {
                if (!php) {
                    line.add(" ]");
                } else {
                    line.add(" )");
                }
            }
            lines.add(line);
        } else {
            line.addMouseListener(new ArrayObjectListener(line, frame));
            lines.add(line);

            for (int i = 0; i < node.getSize(); i++) {
                Line valueLine = new Line(new Node("", Type.NULL), depth + 1);

                if (node.get(i).isNode()) {
                    callNextNodes(node, depth);
                } else {
                    String valueString = indentation + Parameters.IHM_INDENTATION;
                    valueLine.add(valueString, Parameters.STRING_COLOR);
                    createValue(valueLine, node.get(i));

                    if (i != node.getSize() - 1) {
                        valueLine.add(",");
                    }
                }

                lines.add(valueLine);
            }

            Line endLine;
            if (!php) {
                endLine = new Line(node, indentation + "]", depth);
            } else {
                endLine = new Line(node, indentation + ")", depth);
            }
            endLine.setClosingElement();

            if (virgule) {
                endLine.add(",");
            }

            lines.add(endLine);
        }
    }

    /**
     * Affiche la valeur d'un noeud
     * 
     * @param line  la ligne sur laquelle afficher la valeur
     * @param value la valeur à afficher
     */
    private void createValue(Line line, Value value) {
        if (value.isNumber()) {
            line.add("" + value.getValue(), Parameters.NUMBER_COLOR);
        } else if (value.isString()) {
            line.add("\"" + value.getValue() + "\"", Parameters.STRING_COLOR);
        } else {
            line.add("" + value.getValue(), Parameters.OTHER_COLOR);
        }
    }

    /**
     * Appelle les fils d'un noeud père
     * 
     * @param node  le noeud père
     * @param depth la profondeur dans l'arbre
     */
    private void callNextNodes(Node node, int depth) {
        boolean virgule;

        for (int i = 0; i < node.getSize(); i++) {
            // si l'élément afficher est le dernier à son niveau "virgule" est faux
            // donc il n'y aura pas de virgule en fin ligne
            virgule = i != node.getSize() - 1;

            if (node.get(i).isNode()) {
                createFileRecursive((Node) node.get(i).getValue(), depth + 1, virgule);
            }
        }
    }

    /**
     * Affiche les lignes dans l'ordre en prenant compte de leur statut de
     * visibilité
     */
    private void displayLines() {
        boolean inArrayObject = false, array, object;
        Node openedArrayObject = lines.get(0).getNode();
        removeAllClosingLabel();

        for (int i = 0; i < lines.size(); i++) {
            if (!inArrayObject) {
                array = lines.get(i).getNode().isArray();
                object = lines.get(i).getNode().isObject();

                // Vérifie si le noeud est du type ARRAY ou du type OBJECT et s'il doit être
                // affiché
                if ((array || object) && !lines.get(i).isShow()) {
                    inArrayObject = true;
                    openedArrayObject = lines.get(i).getNode();

                    if (openedArrayObject.isArray()) {
                        displayOneHidedLine(i, Parameters.ARRAY_CLOSING);
                    } else {
                        displayOneHidedLine(i, Parameters.OBJECT_ELEMENT_CLOSING);
                    }

                    // Sinon affiche la ligne normalement
                } else {
                    displayOneLine(i);
                }

            } else if (lines.get(i).getNode().equals(openedArrayObject)) {
                inArrayObject = false;
            }
        }
    }

    /**
     * Affiche une ligne normalement
     * 
     * @param index le numéro de la ligne
     */
    private void displayOneLine(int index) {
        gbc.gridy = index;

        gbc.gridx = 0;
        alignementPanel.add(lines.get(index), gbc);
    }

    /**
     * Affiche un ligne cacher
     * 
     * @param index     le numéro de la ligne
     * @param endOfLine le texte à afficher en fin de ligne
     */
    private void displayOneHidedLine(int index, String endOfLine) {
        gbc.gridy = index;

        gbc.gridx = 0;
        lines.get(index).add(endOfLine);
        alignementPanel.add(lines.get(index), gbc);
    }

    /**
     * Change tous les statuts de visibilités à vrai
     */
    public void showAll() {
        for (Line line : lines) {
            if (!line.isShow()) {
                line.removeClosingLabel();
            }
            line.unfold();
        }
    }

    /**
     * Change tous les statuts de visibilité à faux pour les éléments que l'on peut
     * cacher
     */
    public void retreatAll() {
        for (Line line : lines) {
            if (line.getNode().isArrayObjectElement()) {
                if (0 < line.getDepth() && 0 < line.getNode().getSize() && !line.isClosingElement()) {
                    line.retreat();
                }
            }
        }
    }

    /**
     * Enlève tous les caractères fermants
     */
    private void removeAllClosingLabel() {
        for (Line line : lines) {
            line.removeClosingLabel();
        }
    }

    /**
     *
     * @return la liste des lignes du fichier
     */
    public ArrayList<Line> getLines() {
        return lines;
    }
}
