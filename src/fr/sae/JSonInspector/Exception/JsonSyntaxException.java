package fr.sae.JSonInspector.Exception;

/**
 * Exception lever lorsqu'une erreur de syntaxe est rencontrée
 */
public class JsonSyntaxException extends Throwable {
    private static final String MESSAGE = "[!] La syntaxe du fichier JSON est incorrect";

    public JsonSyntaxException() {
        super(MESSAGE);
    }
}
