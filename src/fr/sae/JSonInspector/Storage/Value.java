package fr.sae.JSonInspector.Storage;

/**
 * Représente une valeur
 * 
 * @param <T> le type de la valeur
 */
public class Value<T> {
    private T value;

    public Value(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public boolean isObjectOrArray() {
        if (value.getClass().equals(Node.class)) {
            Node node = (Node) value;
            if (node.getType() == Type.OBJECT || node.getType() == Type.ARRAY) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean isNode() {
        return value.getClass().equals(Node.class);
    }

    public boolean isString() {
        return value.getClass().equals(String.class);
    }

    public boolean isNumber() {
        return value.getClass().equals(Integer.class) || value.getClass().equals(Double.class);
    }
}
