import java.io.File;
import Console.DisplayConsole;
import Graphics.GraphicsCore;

/**
 * [Bilal et Romain]
 * Programme pour afficher un code json dans le teminal ou sur une JFrame (avec
 * les options tel que: la coloration syntaxique, repli de tableau etc...)
 */
public class Core {
    public static void main(String[] args) {
        if (args.length == 1) {
            try {
                new DisplayConsole(new File(args[0]).toURI().toURL());
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (args.length == 0) {
            new GraphicsCore();
        } else {
            System.out.println("[!] Utilisation: ./jsonFormatter <path>");
        }
    }
}