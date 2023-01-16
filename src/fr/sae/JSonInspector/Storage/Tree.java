package fr.sae.JSonInspector.Storage;

import fr.sae.JSonInspector.Exception.JsonSyntaxException;
import fr.sae.JSonInspector.Settings.Parameters;

import java.util.ArrayList;
import java.util.List;

public class Tree {
    private final Node firstNode;

    /**
     * Construit un objet de type 'Tree'
     * 
     * @param file le fichier complet sur une ligne
     * @throws JsonSyntaxException exception lever en cas d'erreur de syntaxe
     */
    public Tree(String file) throws JsonSyntaxException {
        firstNode = parseElement(file);
    }

    /**
     * Détermine le type de l'élément passer en argument et le transforme en noeud
     * 
     * @param element
     * @return le noeud correspondant à "element"
     * @throws JsonSyntaxException exception lever en cas d'erreur de syntaxe
     */
    private Node whichType(String element) throws JsonSyntaxException {
        String[] keyValue = splitKeyValue(element);

        if (keyValue.length == 2) {
            String key = keyValue[0], value = keyValue[1];

            if (0 < value.length()) {
                if (value.charAt(0) == '[' && value.charAt(value.length() - 1) == ']') {
                    return parseArray(key, value);

                } else if (value.charAt(0) == '{' && value.charAt(value.length() - 1) == '}') {
                    return parseObject(key, value);

                } else {
                    return parsePair(key, value);
                }
            } else {
                return null;
            }

        } else if (keyValue[0].equals("")) {

            if (keyValue[0].charAt(0) == '{' && keyValue[0].charAt(keyValue[0].length() - 1) == '}') {
                return parseElement(keyValue[0]);
            } else {
                throw new JsonSyntaxException();
            }

        } else {
            throw new JsonSyntaxException();
        }
    }

    /**
     * Créer un noeud de type tableau
     * 
     * @param name      le nom du tableau
     * @param rawValues l'ensemble des caractères contenu entre les crochets
     * @return le noeud correspondant couple clé/valeur fournie
     * @throws JsonSyntaxException exception lever en cas d'erreur de syntaxe
     */
    private Node parseArray(String name, String rawValues) throws JsonSyntaxException {
        Node array = new Node(cleanOpeningExpression(name), Type.ARRAY);
        List<String> elements = splitList(rawValues);

        for (String value : elements) {
            if (0 < value.length()) {
                if (value.charAt(0) == '{') {
                    if (value.charAt(value.length() - 1) == '}') {
                        array.add(parseElement(value));
                    } else {
                        throw new JsonSyntaxException();
                    }

                } else {
                    array.add(value);
                }
            }
        }

        return array;
    }

    /**
     * Créer un noeud de type objet
     * 
     * @param name      nom de l'objet
     * @param rawValues l'ensemble des éléments contenu entre les accolades
     * @return le noeud correspondant couple clé/valeur fournie
     * @throws JsonSyntaxException exception lever en cas d'erreur de syntaxe
     */
    private Node parseObject(String name, String rawValues) throws JsonSyntaxException {
        Node object = new Node(cleanOpeningExpression(name), Type.OBJECT);
        List<String> elements = splitList(rawValues);

        for (String value : elements) {
            object.add(whichType(value));
        }

        return object;
    }

    /**
     * Créer un noeud de type pair
     * 
     * @param name  nom du couple
     * @param value valeur
     * @return le noeud correspondant couple clé/valeur fournie
     */
    private Node parsePair(String name, String value) {
        Node pair = new Node(cleanOpeningExpression(name), Type.PAIR);
        pair.add(value);

        return pair;
    }

    /**
     * Créer un noeud de type element (meme chose que le type object mais sans nom)
     * 
     * @param rawValues l'ensemble des éléments contenu entre les accolades
     * @return le noeud correspondant couple clé/valeur fournie
     * @throws JsonSyntaxException exception lever en cas d'erreur de syntaxe
     */
    private Node parseElement(String rawValues) throws JsonSyntaxException {
        Node element = new Node("", Type.ELEMENT);
        List<String> elements = splitList(rawValues);

        for (String value : elements) {
            element.add(whichType(value));
        }

        return element;
    }

    /**
     * Enlève les caractères ouvrant et fermant
     * 
     * @param strToClean chaîne de caractères à nettoyer
     * @return chaîne de caractères nettoyée
     */
    public static String cleanOpeningExpression(String strToClean) {
        StringBuilder cleanedString = new StringBuilder(strToClean);
        cleanedString.deleteCharAt(0);
        cleanedString.deleteCharAt(cleanedString.length() - 1);
        return cleanedString.toString();
    }

    /**
     * Sépare l'ensemble des éléments contenu dans la chaîne de caractères
     * 
     * @param listToSplit la liste à trier
     * @return l'ensemble des éléments séparé
     */
    private List<String> splitList(String listToSplit) {
        char[] chars = cleanOpeningExpression(listToSplit).toCharArray();
        int depth = 0;
        List<String> elements = new ArrayList<>();
        String buffer = "";

        for (char currentChar : chars) {
            if (currentChar == ',' && depth == 0) {
                elements.add(buffer);
                buffer = "";
            } else if (currentChar == '{' || currentChar == '[') {
                depth += 1;
                buffer += currentChar;
            } else if (currentChar == '}' || currentChar == ']') {
                depth -= 1;
                buffer += currentChar;
            } else {
                buffer += currentChar;
            }
        }
        elements.add(buffer);
        return elements;
    }

    /**
     * Sépare la clé de sa valeur
     * 
     * @param expressionToSplit l'expression à diviser
     * @return le couple clé/valeur créé
     */
    private String[] splitKeyValue(String expressionToSplit) {
        boolean inKey = true;
        char[] chars = expressionToSplit.toCharArray();
        String key = "", value, buffer = "";

        for (char currentChar : chars) {
            if (inKey) {
                if (currentChar == '{' || currentChar == ':') {
                    key = buffer;
                    buffer = "";
                    if (currentChar == '{') {
                        buffer += currentChar;
                    }
                    inKey = false;
                } else {
                    buffer += currentChar;
                }
            } else {
                buffer += currentChar;
            }
        }
        value = buffer;

        return new String[] { key, value };
    }

    /**
     * Affiche l'arbre de manière récursive
     * 
     * @param node  le noeud a afficher
     * @param depth la profondeur dans l'arbre
     * @return le noeud convertit en chaîne de caractères
     */
    public static String printTree(Node node, int depth) {
        String line = "", indentation = "";

        // créé l'indentation de la bonne taille en fonction de la
        // profondeur dans l'arbre
        for (int i = 0; i < depth; i++) {
            indentation += Parameters.CONSOLE_INDENTATION;
        }

        if (!node.isElement()) {
            line += indentation + "\"" + node.getName() + "\"";
        } else {
            line += indentation + node.getName();
        }

        if (node.isObject() || node.isElement()) {
            line += printObjectElement(node, depth, indentation);

        } else if (node.isPair()) {
            line += printPair(node);

        } else if (node.isArray()) {
            line += printArray(node, depth, indentation);
        }

        return line;
    }

    /**
     * Appel les fils suivants d'un noeud père
     * 
     * @param node  le noeud père
     * @param depth la profondeur dans l'arbre
     * @return l'ensemble des noeuds fils convertit en chaîne de caractères
     */
    private static String callNextNodes(Node node, int depth) {
        String line = "";

        for (int i = 0; i < node.getSize(); i++) {
            if (node.get(i).isNode()) {
                line += "\n" + printTree((Node) node.get(i).getValue(), depth + 1);
            }

            if (i != node.getSize() - 1) {
                line += ",";
            }
        }

        return line;
    }

    /**
     * Convertit en chaîne de caractères un noeud de type 'PAIR'
     * 
     * @param node le noeud a afficher
     * @return le noeud convertit en chaîne de caractères
     */
    private static String printPair(Node node) {
        String line = "";

        if (node.getSize() != 0) {
            if (node.get(0).isString()) {
                line += ": \"" + node.get(0).getValue() + "\"";
            } else {
                line += ": " + node.get(0).getValue();
            }

        } else {
            line += ": null";
        }

        return line;
    }

    /**
     * Convertit en chaîne de caractères un noeud de type 'OBJECT' ou 'ELEMENT'
     * 
     * @param node        le noeud à afficher
     * @param depth       la profondeur dans l'arbre
     * @param indentation l'indentation du noeud père
     * @return le noeud convertit en chaîne de caractères
     */
    private static String printObjectElement(Node node, int depth, String indentation) {
        String line = "";

        if (node.getType() == Type.ELEMENT) {
            line += "{";
        } else {
            line += ": {";
        }

        if (node.getSize() == 0) {
            line += "}";
        } else {
            line += callNextNodes(node, depth);
            line += "\n" + indentation + "}";
        }

        return line;
    }

    /**
     * Convertit en chaîne de caractères un noeud de type 'ARRAY'
     * 
     * @param node        le noeud à afficher
     * @param depth       la profondeur dans l'arbre
     * @param indentation l'indentation du noeud père
     * @return le noeud convertit en chaîne de caractères
     */
    private static String printArray(Node node, int depth, String indentation) {
        String line = "";
        line += ": [";

        if (node.getSize() == 0) {
            line += "]";

        } else {
            // Cette boucle parcours les valeurs du tableau
            for (int i = 0; i < node.getSize(); i++) {
                // si la valeur a l'indice i n'est pas une valeur brute alors
                // on appelle de manière récursive la fonction d'affichage de l'arbre
                if (node.get(i).isNode()) {
                    line += "\n" + printTree((Node) node.get(i).getValue(), depth + 1);
                } else {
                    line += "\n" + indentation + Parameters.CONSOLE_INDENTATION;
                    if (node.get(i).isString()) {
                        line += "\"" + node.get(i).getValue() + "\"";
                    } else {
                        line += node.get(i).getValue();
                    }
                }

                // si la valeur n'est pas la dernière alors on lui ajoute une virgule
                if (i != node.getSize() - 1) {
                    line += ",";
                }
            }

            line += "\n" + indentation + "]";
        }

        return line;
    }

    @Override
    public String toString() {
        return printTree(firstNode, 0);
    }

    /**
     * retourne le noeud d'entrer dans l'arbre
     * 
     * @return le premier noeud de l'arbre
     */
    public Node getFirstNode() {
        return firstNode;
    }
}
