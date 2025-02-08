import java.util.ArrayList;
import java.util.logging.Level;

import Jeu.Jeu;
import Jeu.Pioche;

public class Find_seed {
    public static void main(String[] args) {
        int []tab = {2,2};

        
        long seed = 0;
        int scoreDesire = 98;

        ArrayList<Long> seeds = new ArrayList<Long>();

        Jeu.logger.setLevel(Level.WARNING);

        while (seeds.size() < 40) {
            Pioche pioche = new Pioche((int)seed);
            Jeu jeu = new Jeu(tab, pioche, 1,false,false);

            
            jeu.jouer();
            
            if (jeu.getScore() == scoreDesire) {
                System.out.println("Seed trouvé : " + seed);
                seeds.add(seed);
            }

            seed++;
            // Pour éviter une boucle infinie, vous pourriez vouloir limiter le nombre d'itérations
            if (seed == Long.MAX_VALUE) {
                System.out.println("Seed non trouvé.");
                break;
            }
        }
        System.out.println("Liste des seeds : " + seeds);
        
    }
}
