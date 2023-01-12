package Graphics.Type;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.List;

/**
 * Representation d'un tableau d'object
 */

public class Array implements Type<List<Type<?>>> {
    private String valueRaw;
    private String[] valuesRaw;
    private List<Type<?>> value;
    private Color color;

    public Array(String valueRaw) {
        this.valueRaw = valueRaw.substring(1, valueRaw.length() - 1);
        this.valuesRaw = new String[] {};
        this.value = new LinkedList<>();

        this.Run();
    }

    private void Run() {
        String[] spliced = this.valuesRaw;
        List<Type<?>> list = new LinkedList<>();

        for (int i = 0; i <= spliced.length - 1; i++) {
            switch (this.getType(spliced[i])) {
                case "string": {
                    list.add(new Chaine(String.valueOf(spliced[i])));
                    break;
                }

                case "int": {
                    list.add(new Entier(Integer.valueOf(spliced[i])));
                    break;
                }

                case "array": {
                    System.out.println(spliced[i]);
                    list.add(new Array(spliced[i]));
                    break;
                }

                case "double": {
                    list.add(new Flottant(Double.valueOf(spliced[i])));
                    break;
                }

                case "boolean": {
                    list.add(new Bool(Boolean.valueOf(spliced[i])));
                    break;
                }
            }
        }

        this.value = list;
    }

    private String getType(String value) {
        if (value.contains("{")) {
            return "object";
        } else if (value.contains("[")) {
            return "array";
        } else if (value.contains("\"")) {
            return "string";
        } else if (value.contains("true") || value.contains("false")) {
            return "boolean";
        } else if (value.contains(".")) {
            return "double";
        } else if (value.contains("null")) {
            return "null";
        } else {
            return "int";
        }
    }

    public String getValueRaw() {
        return this.valueRaw;
    }

    @Override
    public List<Type<?>> listGet() {
        return this.value;
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public String display() {
        StringBuilder str = new StringBuilder();

        str.append("[ ");

        for (int i = 0; i <= this.value.size() - 1; i++) {
            str.append(this.value.get(i).display() + ", ");
        }

        str.deleteCharAt(str.length() - 1);
        str.deleteCharAt(str.length() - 1);

        str.append(" ]");

        return str.toString();
    }

    @Override
    public String getType() {
        return "array";
    }

    @Override
    public List<Type<?>> getValue() {
        return this.value;
    }
}
