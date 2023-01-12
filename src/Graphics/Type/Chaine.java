package Graphics.Type;

import java.awt.Color;
import java.util.List;

/**
 * Representation du type string
 */

public class Chaine implements Type<String> {
    private String value;
    private Color color;

    public Chaine(String value) {
        this.value = value;
        this.color = Color.PINK;
    }

    @Override
    public List<Type<?>> listGet() {
        return null;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public String display() {
        return String.valueOf(value);
    }

    @Override
    public String getType() {
        return "string";
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
