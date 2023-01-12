package Graphics.Type;

import java.awt.Color;
import java.util.List;

/**
 * Representation du type int
 */

public class Entier implements Type<Integer> {
    public Integer value;
    private Color color;

    public Entier(Integer value) {
        this.value = value;
        this.color = Color.BLUE;
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
        return "int";
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
