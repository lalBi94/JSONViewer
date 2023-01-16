package fr.sae.JSonInspector.Settings;

import java.awt.Font;
import java.awt.Color;

/**
 * Contient uniquement les paramètres de l'application
 */
public class Parameters {
    public static final Font FILE_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
    public static final String IHM_INDENTATION = "      ";
    public static final String CONSOLE_INDENTATION = "  ";
    public static final String ARRAY_CLOSING = "...]";
    public static final String OBJECT_ELEMENT_CLOSING = "...}";

    public static final Color IHM_COLOR = new Color(70, 70, 70);
    public static final Color KEY_COLOR = new Color(70, 189, 204);
    public static final Color OTHER_COLOR = new Color(7, 103, 183);
    public static final Color STRING_COLOR = new Color(203, 109, 80);
    public static final Color NUMBER_COLOR = new Color(133, 192, 95);
    public static final Color DEFAULT_TEXT_COLOR = new Color(220, 220, 220);
    public static final Color BACKGROUND_COLOR = new Color(45, 45, 45);
}
