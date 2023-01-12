package Graphics;

import java.io.File;
import java.net.MalformedURLException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.net.URL;
import java.awt.Dimension;
import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.BorderLayout;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * [Romain]
 * Faire le pont entre la selection du fichier JSON et l'affichage
 * graphique du code JSON
 */

public class GraphicsCore extends JFrame {
    private final Dimension DEFAULT_FRAME_SIZE = new Dimension(800, 600);
    private final Dimension MINIMUM_FRAME_SIZE = new Dimension(600, 500);
    private final CardLayout cards;
    private JLabel textField;
    private URL url;

    public GraphicsCore() {
        super("Inspecteur JSON - Romain & Bilal");

        this.url = null;
        this.textField = new JLabel("Clique ici pour choisir le chemin du fichier JSON ->");

        this.cards = new CardLayout();

        this.init();
        this.add(firstCard());
        cards.last(this.getContentPane());
        this.setVisible(true);
    }

    /**
     * Initalisation des parametres de la Frame par defaut.
     */
    private void init() {
        this.setSize(DEFAULT_FRAME_SIZE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(MINIMUM_FRAME_SIZE);
        this.setLayout(cards);
        this.add(firstCard());
        cards.first(this.getContentPane());
    }

    /**
     * Creation de la fenetre ou l'on nous demande de chemin absolut du fichier JSON
     * 
     * @return Le JPanel de la premiere card du CardLayout
     */
    private JPanel firstCard() {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        this.textField = new JLabel("Clique ici pour choisir le chemin du fichier JSON ->");

        JButton button = new JButton("Valider");
        button.addActionListener((event) -> validationAction(this.textField.getText()));

        JButton selectedFile = new JButton("...");
        selectedFile.addActionListener((event) -> getPathOf());

        JPanel panel = new JPanel();
        panel.setLayout(layout);

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("URL :"), gbc);

        gbc.gridx = 1;
        panel.add(this.textField, gbc);

        gbc.gridx = 2;
        panel.add(selectedFile, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(button, gbc);

        return panel;
    }

    /**
     * Creation de la fenetre ou sera afficher le code du fichier JSON
     * 
     * @return Le JPanel contenant le rendu de Traitable
     * @see Graphics.Traitable
     */
    private JPanel secondCard() {
        GraphicFile file = new GraphicFile(this.url);

        JPanel mainPanel = new JPanel(), southPanel = new JPanel();
        JButton backButton = new JButton("Retour");
        backButton.addActionListener((event) -> backAction(mainPanel));
        JScrollPane scroll = new JScrollPane();
        mainPanel.setLayout(new BorderLayout());

        southPanel.add(backButton);
        southPanel.add(new JButton("Tout déplier"));
        southPanel.add(new JButton("convertir en PHP"));

        mainPanel.add(file, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        mainPanel.add(scroll);

        scroll.setViewportView(file);
        return mainPanel;
    }

    /**
     * Permet de la fenetre ou l'on nous demande de chemin absolut du fichier JSON
     * 
     * @param field Le chemin absolue du fichier JSON
     */
    private void validationAction(String field) {
        try {
            this.url = new File(field).toURI().toURL();
            this.add(secondCard());
            cards.last(this.getContentPane());
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(this, "Invalid URL", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Retourner dans la selection du fichier JSON
     * 
     * @param panel Le JPanel ou l'on demande a l'utilisateur de choisir le fichier
     *              JSON
     */
    private void backAction(JPanel panel) {
        this.remove(panel);
        cards.first(this.getContentPane());
    }

    /**
     * Selection du fichier JSON
     */
    private void getPathOf() {
        JFileChooser jc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        jc.setDialogTitle("Choissez le fichier json: ");
        jc.setFileSelectionMode(JFileChooser.FILES_ONLY);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichier JSON", "json");
        jc.setFileFilter(filter);

        int res = jc.showOpenDialog(this);

        if (res == JFileChooser.APPROVE_OPTION) {
            if (jc.getSelectedFile().isFile()) {
                this.textField.setText(jc.getSelectedFile().getAbsolutePath());
                this.revalidate();
            }
        }
    }
}
