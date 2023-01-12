package Graphics;

import java.io.InputStream;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Graphics.Type.Type;
import Graphics.Type.Type.*;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.net.URL;

/**
 * [Romain]
 * Pour gerer l'affichage graphique du code JSON
 */

public class GraphicFile extends JPanel {
    /**
     * @param url Le chemin vers le fichier JSON
     */
    public GraphicFile(URL url) {
        super();
        try {
            System.out.println("[+] Lecture de " + url);
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            this.setLocation(500, 500);

            InputStream jsonReader = url.openStream();
            Traitable fileTraited = new Traitable(jsonReader);
            jsonReader.close();

            HashMap<String, Type<?>> allVariables = fileTraited.getVariableMap();
            JPanel core = new JPanel(new GridBagLayout());
            GridBagConstraints settings = new GridBagConstraints();
            settings.gridx = 0;
            settings.gridy = 0;
            settings.anchor = GridBagConstraints.WEST;

            core.add(new JLabel("{"), settings);

            int rows = 1;
            settings.gridy = 1;
            settings.gridx = 2;

            for (String key : allVariables.keySet()) {
                JPanel fusion = new JPanel(new GridLayout(1, 1));

                settings.gridy = rows;

                JLabel name = new JLabel("\"" + key + "\": ");
                name.setForeground(new Color(163, 90, 0));
                fusion.add(name);
                if (allVariables.get(key).getClass().getName() != "Graphics.Type.Array") {
                    JLabel value = new JLabel(allVariables.get(key).display());
                    value.setForeground(allVariables.get(key).getColor());
                    fusion.add(value);
                } else {
                    JPanel speForArray = new JPanel();
                    speForArray.add(new JLabel("[ "));
                    for (int i = 0; i <= allVariables.get(key).listGet().size() - 1; i++) {
                        JLabel tmp = new JLabel(String.valueOf(allVariables.get(key).listGet().get(i).display()));
                        tmp.setForeground(allVariables.get(key).listGet().get(i).getColor());
                        speForArray.add(tmp);
                    }
                    speForArray.add(new JLabel(" ]"));
                    fusion.add(speForArray);
                }

                core.add(fusion, settings);
                rows++;
            }

            settings.gridx = 0;
            settings.gridy = rows;
            core.add(new JLabel("}"), settings);

            this.add(core);

            this.setVisible(true);
        } catch (IOException e) {
            System.out.println("[!] Fichier " + url.getFile() + " n'existe pas");
        }
    }
}
