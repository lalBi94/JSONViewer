package fr.sae.JSonInspector.Storage;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final List<Value<?>> values = new ArrayList<>();
    private final String name;
    private final Type type;

    /**
     *
     * @param name le nom du noeud
     * @param type le type du noeud
     */
    public Node(String name, Type type) {
        this.type = type;
        if (type == Type.ELEMENT) {
            this.name = "";
        } else {
            this.name = name;
        }
    }

    /**
     * Détermine le type de la chaîne de caractère passé en argument
     * 
     * @param value la valeur à classifier
     */
    private void findType(String value) {
        if (value.charAt(0) == '"' && value.charAt(value.length() - 1) == '"') {
            values.add(new Value<String>(Tree.cleanOpeningExpression(value)));
            return;
        }

        try {
            values.add(new Value<Integer>(Integer.parseInt(value)));
        } catch (NumberFormatException nfeInt) {
            try {
                values.add(new Value<Double>(Double.parseDouble(value)));
            } catch (NumberFormatException nfeDouble) {
                values.add(new Value<Other>(new Other(value)));
            }
        }
    }

    /**
     * Ajoute une valeur au noeud
     * 
     * @param value la valeur à ajouter
     * @param <T>   le type de la valeur à ajouter
     */
    public <T> void add(T value) {
        if (value != null) {
            if (value.getClass().equals(Node.class)) {
                values.add(new Value<T>(value));
            } else {
                findType((String) value);
            }
        }
    }

    /**
     *
     * @return le type du noeud
     */
    public Type getType() {
        return type;
    }

    /**
     *
     * @return le nom du noeud
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne la valeur contenue à l'index spécifié
     * 
     * @param index l'index de la valeur
     * @return la valeur trouvée
     */
    public Value<?> get(int index) {
        return values.get(index);
    }

    /**
     * retourne le nombre de valeurs du noeud
     * 
     * @return
     */
    public int getSize() {
        return values.size();
    }

    public boolean isObject() {
        return type == Type.OBJECT;
    }

    public boolean isArray() {
        return type == Type.ARRAY;
    }

    public boolean isElement() {
        return type == Type.ELEMENT;
    }

    public boolean isPair() {
        return type == Type.PAIR;
    }

    /**
     * Test si l'objet est du type 'ELEMENT' ou 'OBJECT'
     * 
     * @return
     */
    public boolean isArrayObjectElement() {
        boolean array = type == Type.ARRAY;
        boolean object = type == Type.OBJECT;
        boolean element = type == Type.ELEMENT;

        if (array || object || element) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        String string = name + " : ";

        for (int i = 0; i < values.size(); i++) {
            string += values.get(i) + ", ";
        }

        return string;
    }
}
