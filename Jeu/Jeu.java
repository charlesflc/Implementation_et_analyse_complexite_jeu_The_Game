package Jeu;

import Algorithmes.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;



import Exceptions.ExceptionJoueurPeutPasJouer;

public class Jeu {
    public static final Logger logger = Logger.getLogger(Jeu.class.getName());

    public Pioche pioche;

    public BaseAsc baseasc1;
    public BaseAsc baseasc2;

    public BaseDesc basedesc1;
    public BaseDesc basedesc2;
    
    public HashSet<Integer> dejajoue;

    private int nb_cartes_a_jouer; // Quand il n'y a plus de carte dans la pioche, le minimum de carte par personne
                                   // à jouer passe de 2 à 1

    private boolean tour_a_tour;
    private int nb_joueurs; // Nombre de joueurs
    private Joueur tab_joueurs[]; // Tableau des joueurs
    private int nb_tours; // Nombre de tours
    private int score; // Score sur 98
    public final int nb_cartes_reflexion ;

    public final boolean discussion_bool;
    private HashMap<Joueur, Double[]> discussion ;

    static {
        //Pour les logs propres : 
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new CustomFormatter());
        logger.addHandler(consoleHandler);
            // Désactivez le handler par défaut pour éviter les doublons :
        logger.setUseParentHandlers(false);

    }
    public Jeu(int[] tab_type_joueur, Pioche pioche, int nb_cartes_reflexion, boolean discu, boolean tat) {

        tour_a_tour = tat;
        baseasc1 = new BaseAsc("baseasc1");
        baseasc2 = new BaseAsc("baseasc2");
        basedesc1 = new BaseDesc("basedesc1");
        basedesc2 = new BaseDesc("basedesc2");
        nb_cartes_a_jouer = 2;
        nb_joueurs = tab_type_joueur.length;
        tab_joueurs = new Joueur[nb_joueurs];
        this.pioche = pioche;
        score = 0;
        this.nb_cartes_reflexion = nb_cartes_reflexion;
        this.discussion_bool = discu;

        dejajoue = new HashSet<Integer>();
        discussion = new HashMap<Joueur, Double[]>();


        int nb_cartes_par_joueur = -1;

        switch (nb_joueurs) {
            case 1:
                nb_cartes_par_joueur = 8;
                break;
            case 2:
                nb_cartes_par_joueur = 7;
                break;

            default:
                nb_cartes_par_joueur = 6;
                break;
        }
        for (int i = 0; i < nb_joueurs; i++) {


            switch (tab_type_joueur[i]) {
               
                case 2:
                    tab_joueurs[i] = new Minimiser_ecart_saut10(nb_cartes_par_joueur, this);
                    break;
                case 4:
                    tab_joueurs[i] = new JoeurInteractif(nb_cartes_par_joueur, this);
                    break;
                case 6:
                    tab_joueurs[i] = new Randomv2(nb_cartes_par_joueur,this);
                    break;
                case 7:
                    tab_joueurs[i] = new Minimiser_ecart_carte(nb_cartes_par_joueur,this);
                    break;
                case 8:
                    tab_joueurs[i] = new Minimiser_gachis(nb_cartes_par_joueur,this);
                    break;
                case 9:
                    tab_joueurs[i] = new Tab_2cartes(nb_cartes_par_joueur, this);
                    break;
                default:
                    return;
            }
            Double[] tab = {0.0,0.0,0.0,0.0};
            discussion.put(tab_joueurs[i], tab);
        }
    }


    public void jouer() {
        int i = 0;
        nb_tours = 0;

        while (true) {
            try {
                if(tour_a_tour){
                    //Demander à l'utilisateur d'appuyer sur entrée pour continuer
                    logger.info("Appuyez sur entrée pour continuer");
                    try {
                        System.in.read();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                this.clearScreen();
                logger.info("Joueur n°"+i+" : " + tab_joueurs[i].getNom() + "\n");
                logger.info("Nb cartes à jouer : " + nb_cartes_a_jouer + "\n");

      
                discuter();
                showDiscussion();
                tab_joueurs[i].jouer(this);
                
                score = baseasc1.getSize() + baseasc2.getSize() + basedesc1.getSize() + basedesc2.getSize();
                 if(score == 98){
                    logger.severe("Bravo, vous avez gagné !");
                    break;
                }
                if(pioche.size() == 0)
                    nb_cartes_a_jouer = 1;

            } catch (ExceptionJoueurPeutPasJouer e) {
                score = baseasc1.getSize() + baseasc2.getSize() + basedesc1.getSize() + basedesc2.getSize();
                
                logger.info("Le joueur "+i+" ne peut pas jouer, fin du jeu");
                //logger.info("Score : " + score + "/98");

/*                 if (score >=97){
                    logger.severe("Cartes restantes dans la main : \n"+tab_joueurs[i].main.toString());
                    logger.severe("1 : BaseAsc1  : " + baseasc1.getLatestCarte());
                    logger.severe("2 : BaseAsc2   : " + baseasc2.getLatestCarte());
                    logger.severe("3 : BaseDesc1  : " + basedesc1.getLatestCarte());
                    logger.severe("4 : BaseDesc2  : " + basedesc2.getLatestCarte()); 
                    logger.severe("Score : " + score + "/98"); 
                }*/
                if (score == 98)
                    logger.severe("Bravo, vous avez gagné ! Via ExceptionJoueurPeutPasJouer");
                break;
            }
            i = (i + 1) % nb_joueurs;
            nb_tours++;
        }
    }

    public void BasesQuestion() {
        logger.info("====== Etat Bases : =====");
         logger.info("REMOVED FOR PERFORMANCE");
          logger.info("1 : BaseAsc1  : " + baseasc1.toString());
        logger.info("2 : BaseAsc2   : " + baseasc2.toString());
        logger.info("3 : BaseDesc1  : " + basedesc1.toString());
        logger.info("4 : BaseDesc2  : " + basedesc2.toString()); 
 
        logger.info(" ");
 
    }

    public void discuter(){
        if(discussion_bool){
            for(Joueur joueur : tab_joueurs){
                joueur.discute(this);
            }
        }
    }

    public int getNbCartesAJouer() {
        return nb_cartes_a_jouer;
    }
    public HashMap<Joueur,Double[]> getHashMap(){
        return discussion;
    }


    public void showDiscussion(){
        logger.info("====== Discussion : =====");
        for (Joueur key : discussion.keySet()) {
            Double[] val = discussion.get(key);
            logger.info(key.getNom() + " : " + val[0] + " " + val[1] + " " + val[2] + " " + val[3]);
        }
    }


    public void    clearScreen() {
        // System.out.print("\033[H\033[2J");
        logger.info("\n ");
        // System.out.flush();
        logger.info("====== The Game ======");
        logger.info("Tour n°" + nb_tours + "");
        logger.info("Nombre de cartes restantes dans la pioche : " + pioche.size() + "/98");
    }

    public int getNbJoueurs() {
        return nb_joueurs;
    }

    public int getScore() {
        return score;
    }
}
