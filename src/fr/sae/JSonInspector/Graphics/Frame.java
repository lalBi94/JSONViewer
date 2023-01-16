package fr.sae.JSonInspector.Graphics;

import fr.sae.JSonInspector.Exception.JsonSyntaxException;
import fr.sae.JSonInspector.Main;
import fr.sae.JSonInspector.Settings.Parameters;
import fr.sae.JSonInspector.Storage.Tree;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

public class Frame extends JFrame {
    private static final Dimension DEFAULT_FRAME_SIZE = new Dimension(800, 600);
    private static final Dimension MINIMUM_FRAME_SIZE = new Dimension(600, 500);
    private final CardLayout cards = new CardLayout();
    private boolean php = false;
    private JPanel secondCard;
    private GraphicFile file;
    private Tree tree;

    public Frame() {
        super("Inspecteur JSON - Romain Besson & Bilal Boudjemline");
        init();
        this.setVisible(true);
    }

    private void init() {
        this.setSize(DEFAULT_FRAME_SIZE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(MINIMUM_FRAME_SIZE);
        this.setLayout(cards);
        this.add(firstCard());
        // this.add(secondCard());
        cards.first(this.getContentPane());
    }

    /**
     * Créer le premier panel où l'on entre l'URL du fichier
     * 
     * @return le panel créé
     */
    private JPanel firstCard() {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        JTextField textField = new JTextField(30);
        JButton button = new JButton("Valider");
        button.addActionListener((event) -> validationAction(textField));
        JPanel panel = new JPanel();
        panel.setLayout(layout);

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel label = new JLabel("URL :");
        label.setForeground(Parameters.DEFAULT_TEXT_COLOR);
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(textField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(button, gbc);

        panel.setBackground(Parameters.IHM_COLOR);
        return panel;
    }

    /**
     * Créer le deuxième panel où l'on voit le fichier
     * 
     * @return le panel créé
     */
    private JPanel secondCard() {
        file = new GraphicFile(this, tree, php);
        return initSecondCard(file);
    }

    /**
     * Créer le deuxième panel où l'on voit le fichier
     * 
     * @return le panel créé
     */
    private JPanel secondCard(GraphicFile file) {
        this.file = file;
        return initSecondCard(file);
    }

    private JPanel initSecondCard(GraphicFile file) {
        JButton unfoldButton = new JButton("Tout déplier");
        JButton retreatButton = new JButton("Tout replier");
        JButton convertButton = new JButton();
        JButton backButton = new JButton("Retour");
        JPanel mainPanel = new JPanel(), southPanel = new JPanel();
        JScrollPane scroll = new JScrollPane();
        mainPanel.setLayout(new BorderLayout());

        if (php) {
            convertButton.setText("Convertir en JSON");
        } else {
            convertButton.setText("Convertir en PHP");
        }

        southPanel.setBackground(Parameters.IHM_COLOR);
        southPanel.add(backButton);
        backButton.addActionListener((event) -> backAction(mainPanel));
        southPanel.add(unfoldButton);
        unfoldButton.addActionListener((event) -> unfoldAction());
        southPanel.add(retreatButton);
        retreatButton.addActionListener((event) -> retreatAction());
        southPanel.add(convertButton);
        convertButton.addActionListener((event) -> convertAction());

        mainPanel.add(file);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        mainPanel.add(scroll);

        scroll.setViewportView(file);
        return mainPanel;
    }

    /**
     * Passe de la version PHP du fichier à la version JSON et vice-versa
     */
    private void convertAction() {
        if (!php) {
            php = true;
        } else {
            php = false;
        }
        file = new GraphicFile(this, tree, php);
        repaintFile();
    }

    /**
     * Déplit l'élément cliqué
     */
    private void unfoldAction() {
        file.showAll();
        repaintFile();
    }

    /**
     * Replit l'élément cliqué
     */
    private void retreatAction() {
        file.retreatAll();
        repaintFile();
    }

    /**
     * Met à jour le fichier
     */
    public void repaintFile() {
        file = new GraphicFile(this, file.getLines());
        this.remove(secondCard);
        secondCard = secondCard(file);
        this.add(secondCard);
        cards.last(this.getContentPane());
    }

    /**
     * Passe du premier panel au second
     * 
     * @param field le champ contenant l'URL
     */
    private void validationAction(JTextField field) {
        try {
            URL url = new URL(field.getText());
            String file = Main.getJsonInOneLine(url);

            if (file.length() <= 2) {
                throw new FileNotFoundException();
            }

            tree = new Tree(file);
            secondCard = secondCard();
            this.add(secondCard);
            cards.last(this.getContentPane());

        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(this, "[!] URL invalide", "Error", JOptionPane.ERROR_MESSAGE);

        } catch (JsonSyntaxException j) {
            JOptionPane.showMessageDialog(this, "[!] Erreur de syntax dans le fichier", "Error",
                    JOptionPane.ERROR_MESSAGE);

        } catch (FileNotFoundException f) {
            JOptionPane.showMessageDialog(this, "[!]Impossible trouver le fichier", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Passe du second panel au premier
     * 
     * @param panel
     */
    private void backAction(JPanel panel) {
        this.remove(panel);
        cards.first(this.getContentPane());
    }
}
