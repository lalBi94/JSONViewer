package Graphics.Type;

import java.awt.Color;
import java.util.List;

/**
 * Representation du type boolean
 */

public class Bool implements Type<Boolean> {
    private Boolean value;
    private Color color;

    public Bool(Boolean value) {
        this.value = value;
        if (value) {
            this.color = Color.GREEN;
        } else {
            this.color = Color.RED;
        }
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
        return "boolean";
    }

    @Override
    public Boolean getValue() {
        return this.value;
    }
}
