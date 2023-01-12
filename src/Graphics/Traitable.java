package Graphics;

import java.util.HashMap;
import java.util.LinkedHashMap;

import Graphics.Type.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * [Bilal]
 * Classe qui sert a stocke les valeurs contenue dans le JSON dans une liste.
 */

public class Traitable {
    private final HashMap<String, String> contentRaw;
    private final HashMap<String, Type<?>> content;
    private final InputStream file;

    public Traitable(InputStream file) {
        this.contentRaw = new LinkedHashMap<>();
        this.content = new LinkedHashMap<>();
        this.file = file;
        this.Run();
    }

    /**
     * Lancement automatique une fois que Traitable est instantie
     * 
     * @see Graphics.GraphicFile
     */
    private void Run() {
        System.out.println("[+] Preparation...");

        try {
            try {
                StringBuilder allJson = new StringBuilder();
                StringBuilder tmp = new StringBuilder();
                int i = 0;

                for (int cursor = this.file.read(); cursor != -1; cursor = this.file.read()) {
                    char c = (char) cursor;
                    allJson.append(c);
                }

                allJson = this.ajustementVirguleEnd(allJson.toString());

                while (i < allJson.length()) {
                    if (allJson.charAt(i) == '"') {
                        while (allJson.charAt(i) != ',') {
                            if (allJson.charAt(i) == '[') {
                                while (allJson.charAt(i) != ']') {
                                    tmp.append(allJson.charAt(i));
                                    i++;
                                }
                            } else if (allJson.charAt(i) == '{') {
                                while (allJson.charAt(i) != '}') {
                                    tmp.append(allJson.charAt(i));
                                    i++;
                                }
                            } else {
                                tmp.append(allJson.charAt(i));
                                i++;
                            }
                        }

                        String[] varInfo = this.getInfoOfRecord(tmp);
                        this.saveValue(varInfo[0], varInfo[1]);
                        tmp.setLength(0);

                        i++;
                    }

                    i++;
                }

            } catch (StringIndexOutOfBoundsException ignore) {
            }
        } catch (IOException e) {
            System.out.println("[!] Probleme lors de la lecture du fichier");
        }
    }

    /**
     * Enregistre dans la HashMap la valeur
     * 
     * @param name  Le nom de l'entree
     * @param value La valeur de l'entree
     * 
     * @see Graphics.Type.Type
     * @see java.util.HashMap
     */
    private void saveValue(String name, String value) {
        String typeOfValue = this.getType(value);

        switch (typeOfValue) {
            case "string": {
                this.content.put(name, new Chaine(String.valueOf(value)));
                break;
            }

            case "int": {
                this.content.put(name, new Entier(Integer.valueOf(value)));
                break;
            }

            case "double": {
                this.content.put(name, new Flottant(Double.valueOf(value)));
                break;
            }

            case "boolean": {
                this.content.put(name, new Bool(Boolean.valueOf(value)));
                break;
            }

            case "array": {
                this.content.put(name, new Array(value));
                break;
            }
        }
    }

    /**
     * Sert a detecter le type d'une valeur
     * 
     * @param value La chaine a evolue pour determiner son type
     * @return Le type
     */
    private String getType(String value) {
        if (value.contains("{")) {
            return "object";
        } else if (value.contains("[")) {
            return "array";
        } else if (value.contains("true") || value.contains("false")) {
            return "boolean";
        } else if (value.contains(".")) {
            return "double";
        } else if (value.contains("null")) {
            return "null";
        } else if (value.contains("\"")) {
            return "string";
        } else {
            return "int";
        }
    }

    /**
     * Recuperer le nom et la valeur divise sous forme de tableau
     * 
     * @param sb La phrase a separer
     * @return Un tableau { 0 = nom, 1 = value }
     */
    private String[] getInfoOfRecord(StringBuilder sb) {
        String[] info = sb.toString().split(":");
        info[0] = this.removeSpeChar(info[0]);
        info[1] = this.removeFirstSpaceIfThere(info[1]);

        return info;
    }

    /**
     * Retourne une valeur JSON qui, si elle contient un espace au debut, le
     * supprime
     * 
     * @param str La valeur JSON
     * @return La valeur JSON sans l'espace au debut si il y en a un
     */
    private String removeFirstSpaceIfThere(String str) {
        if (str.length() > 0 && str.charAt(0) == ' ') {
            str = str.substring(1);
        }

        return str;
    }

    /**
     * Sert a retirer le charactere guillemet
     * 
     * @param str La phrase a soustraire le symbole guillemet
     * @return Retourner une chaine sans le charactere guillemet
     */
    private String removeSpeChar(String str) {
        StringBuilder tmp = new StringBuilder();

        for (int i = 0; i <= str.length() - 1; i++) {
            if (str.charAt(i) != '"') {
                tmp.append(str.charAt(i));
            }
        }

        return tmp.toString();
    }

    /**
     * Sert a rajouter une virgule a l'avant dernier charactre du fichier (pour ne
     * pas skipper la derniere cle valeur)
     * 
     * @param str Le fichier json en une ligne
     * @return Le fichier avec la fameuse virgule
     */
    private StringBuilder ajustementVirguleEnd(String str) {
        int longueur = str.length();
        char avantDernierCaractere = str.charAt(longueur - 2);
        String nouvelleChaine = str.substring(0, longueur - 2) + avantDernierCaractere + ","
                + str.substring(longueur - 1);

        return new StringBuilder(nouvelleChaine);
    }

    /**
     * Recuperer le jeu de cles valeurs dans GrahicFile
     * 
     * @see Graphics.GraphicFile
     * @return Le jeu de cles valeurs
     */
    public HashMap<String, Type<?>> getVariableMap() {
        return this.content;
    }
}