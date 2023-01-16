package fr.sae.JSonInspector.Storage;

/**
 * Un type créé pour les valeurs qui ne sont pas de type String, Double ou
 * Integer (e type est un String dans les faits).
 */
public class Other {
    String value;

    public Other(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
