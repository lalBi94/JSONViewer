package Graphics.Type;

import java.awt.Color;
import java.util.List;

public interface Type<T> {
    /**
     * Retourner le type de la variable
     * 
     * @return le type en string
     */
    public String getType();

    /**
     * 
     * @return
     */
    public T getValue();

    /**
     * Recuperer la couleur de syntaxe d'un type
     */
    public Color getColor();

    /**
     * Afficher la valeur / toutes les valeurs
     */
    public String display();

    /**
     * UNIQUEMENT POUR Graphics.Type.Chaine
     * 
     * @return La liste contenant les valeurs du tableau
     */
    public List<Type<?>> listGet();
}
