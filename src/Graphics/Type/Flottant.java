package Graphics.Type;

import java.awt.Color;
import java.util.List;

/**
 * Representation du type double
 */

public class Flottant implements Type<Double> {
    private Double value;
    private Color color;

    public Flottant(Double value) {
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
        return "double";
    }

    @Override
    public Double getValue() {
        return this.value;
    }
}