package Console;

import java.net.URL;
import java.io.InputStream;
import java.io.IOException;

/**
 * [Bilal]
 * Afficher le code JSON dans la console une fois formatter
 */

public class DisplayConsole {
    private URL jsonFile;

    public DisplayConsole(URL jsonFile) {
        this.jsonFile = jsonFile;

        if (this.Display() == null) {
            System.out.println("[!] Probleme lors du formatage de : " + this.jsonFile.getFile());
        } else {
            System.out.println(this.Display());
        }
    }

    private String Display() {
        try {
            InputStream jsonReader = this.jsonFile.openStream();
            StringBuilder containerJsonFormatted = new StringBuilder();

            int indentLevel = 0;
            boolean currentlyInRecord = false;

            int cursor = jsonReader.read();

            while (cursor != -1) {
                char c = (char) cursor;

                switch (c) {
                    case '{': {
                        containerJsonFormatted.append(c);

                        if (!currentlyInRecord) {
                            containerJsonFormatted.append("\n");
                            indentLevel++;
                            this.addIndentation(containerJsonFormatted, indentLevel);
                        }
                        break;
                    }
                    case '[': {
                        containerJsonFormatted.append(c);
                        if (!currentlyInRecord) {
                            containerJsonFormatted.append("\n");
                            indentLevel++;
                            this.addIndentation(containerJsonFormatted, indentLevel);
                        }
                        break;
                    }
                    case '"': {
                        currentlyInRecord = !currentlyInRecord;
                        containerJsonFormatted.append(c);
                        break;
                    }
                    case ':': {
                        containerJsonFormatted.append(c).append(" ");
                        break;
                    }
                    case ',': {
                        containerJsonFormatted.append(c);
                        if (!currentlyInRecord) {
                            containerJsonFormatted.append("\n");
                            this.addIndentation(containerJsonFormatted, indentLevel);
                        }
                        break;
                    }
                    case ']': {
                        if (!currentlyInRecord) {
                            containerJsonFormatted.append("\n");
                            indentLevel--;
                            this.addIndentation(containerJsonFormatted, indentLevel);
                        }

                        containerJsonFormatted.append(c);
                        break;
                    }
                    case '}': {
                        if (!currentlyInRecord) {
                            containerJsonFormatted.append("\n");
                            indentLevel--;
                            this.addIndentation(containerJsonFormatted, indentLevel);
                        }
                        containerJsonFormatted.append(c);
                        break;
                    }

                    default: {
                        containerJsonFormatted.append(c);
                        break;
                    }
                }

                cursor = jsonReader.read();
            }

            jsonReader.close();
            return containerJsonFormatted.toString();
        } catch (IOException e) {
            System.out.println("[!] Fichier " + this.jsonFile.getFile() + " n'existe pas");
            return null;
        }
    }

    private void addIndentation(StringBuilder sb, int indentLevel) {
        for (int i = 0; i < indentLevel; i++) {
            sb.append("\t");
        }
    }
}
