package fr.sae.JSonInspector;

import fr.sae.JSonInspector.Exception.JsonSyntaxException;
import fr.sae.JSonInspector.Graphics.Frame;
import fr.sae.JSonInspector.Storage.Tree;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            new Frame();
        } else {
            try {
                URL url = new File(args[0]).toURI().toURL();
                String toSend = getJsonInOneLine(url);
                Tree receive = new Tree(toSend);
                System.out.println(receive);
            } catch (MalformedURLException e) {
                System.err.println("[!] Chemin du fichier invalide");
                System.exit(1);
            } catch (JsonSyntaxException e) {
                System.err.println("[!] Syntaxe du fichier JSON invalide");
                System.exit(1);
            }
        }
    }

    public static String getJsonInOneLine(URL f) {
        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader buff = new BufferedReader(new InputStreamReader(f.openStream()));
            String line;
            while ((line = buff.readLine()) != null) {
                sb.append(line);
            }

            buff.close();
        } catch (IOException e) {
            System.err.println("[!] Probleme lors de l'ouverture du fichier");
        }

        return sb.toString();
    }

    public static String cleanFile(String file) {
        char[] chars = file.toCharArray();
        boolean inString = false, inValue = false;
        String cleanedFile = "";

        for (char currentChar : chars) {
            if (!inString && !inValue) {
                if (currentChar == '"') {
                    inString = true;
                    cleanedFile += currentChar;
                } else if (isJsonSyntax(currentChar)) {
                    cleanedFile += currentChar;
                }
            } else if (currentChar == '"') {
                inString = false;
                cleanedFile += currentChar;
            } else if (currentChar == ':') {
                inValue = true;
                cleanedFile += currentChar;
            } else {
                cleanedFile += currentChar;
            }
        }

        return cleanedFile;
    }

    private static boolean isJsonSyntax(char character) {
        boolean openingArray = character == '[', closingArray = character == ']';
        boolean openingObject = character == '{', closingObject = character == '}';
        boolean virgule = character == ',', deuxPoints = character == ':';

        if (openingArray || closingArray || openingObject || closingObject || virgule || deuxPoints) {
            return true;
        }
        return false;
    }
}
