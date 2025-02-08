import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Jeu.*;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.logging.Level;

import Algorithmes.Minimiser_gachis;

public class Simulation {

    private static Dictionary<Integer, String> dict_algo_val;


    public static void main(String[] args) {

        // Quel niveau de log ?
                
        {
            System.out.println("Quel niveau de log ?");
            System.out.println("1 : Severe");
            System.out.println("2 : Waring : que les victoires");
            System.out.println("3 : Info : afficher les détails des tours");
            System.out.println("4 : Fine");
            System.out.println("5 : Finest");
            // off
            System.out.println("0 : Off ");
        }
        int res_log = -1;

        while (res_log < 0 || res_log > 5) {

            System.out.println("Entrez un nombre entre 0 et 5 : ");

            String resS = System.console().readLine();
            try {
                res_log = Integer.parseInt(resS);
            } catch (NumberFormatException e) {
                System.out.println("Entrez un nombre valide");
            }
        }

        // set le niveau de log
        switch (res_log) {
            case 1:
                Jeu.logger.setLevel(Level.SEVERE);
                break;
            case 2:
                Jeu.logger.setLevel(Level.WARNING);
                break;
            case 3:
                Jeu.logger.setLevel(Level.INFO);
                break;
            case 4:
                Jeu.logger.setLevel(Level.FINE);
                break;
            case 5:
                Jeu.logger.setLevel(Level.FINEST);
                break;
            case 0:
                Jeu.logger.setLevel(Level.OFF);
                break;
            default:
                Jeu.logger.setLevel(Level.INFO);
                break;
        }

        //ask cbn de joueur ? 
        System.out.println("Combien de joueurs ?");
        int res = -1;
        while (res < 1 || res > 100) {

            System.out.println("Entrez un nombre entre 1 et 100 ");

            String resS = System.console().readLine();
            try {
                res = Integer.parseInt(resS);
            } catch (NumberFormatException e) {
                System.out.println("Entrez un nombre valide");
            }
        }
        
        int[] tab_type_joueur = new int[res];

        dict_algo_val = new Hashtable<Integer, String>();
        dict_algo_val.put(1, "Mini_ecart");
        dict_algo_val.put(2, "Mini_ecart_s10");
        dict_algo_val.put(3, "FullRand(nul)");
        dict_algo_val.put(4, "Interac");
        dict_algo_val.put(5, "Rand");
        dict_algo_val.put(6, "RandV2");
        dict_algo_val.put(7, "Mini_ecart_carte");
        dict_algo_val.put(8, "Mini_gachis");
        dict_algo_val.put(9, "Tab_2cartes");

        boolean flag = false;

        //ask type de joueurs et set type
        for (int i = 0; i < res; i++) {
            System.out.println("Quel algorithme voulez vous utiliser pour le joueur" + (i + 1) + "?");
            System.out.println("2 : Minimiser ecart avec saut de 10");
            System.out.println("4 : Interactif");
            System.out.println("6 : RandomV2");
            System.out.println("7 : Minimiser ecart avec saut de 10 et compte cartes");
            System.out.println("8 : Minimiser gachis de saut de 10");
            System.out.println("9 : Tab_2cartes");




            int algoj = -1;
            while (algoj != 2 && algoj !=4 && algoj !=6 && algoj !=7 && algoj != 8 && algoj !=9) {

                System.out.println("Entrez un nombre parmis : 2, 4, 6, 7, 8, 9");

                String resS = System.console().readLine();
                try {
                    algoj = Integer.parseInt(resS);
                } catch (NumberFormatException e) {
                    System.out.println("Entrez un nombre valide");
                }
            }
            tab_type_joueur[i] = algoj;
            if (algoj == 8) flag = true;
        }

        if(flag){
            Double newa = -1.0;
            while (newa < 0 || newa > 10) {

                System.out.println("une valeur pour a entre 0 et 10 pour gachis :");
                String resA = System.console().readLine();
                try {
                    newa = Double.valueOf(resA);
                } catch (NumberFormatException e) {
                    System.out.println("Entrez un nombre valide");
                }
            }
            
            Minimiser_gachis.a = newa;
        
        }

        // ask nombre de parties à réaliser :
        System.out.println("Combien de parties voulez vous réaliser ?");
        int res2 = -1;

        while (res2 < 0) {

            System.out.println("Entrez un nombre :");

            String resS = System.console().readLine();
            try {
                res2 = Integer.parseInt(resS);
            } catch (NumberFormatException e) {
                System.out.println("Entrez un nombre valide");
            }
        }

        //ask seed
        System.out.println("Entrez une seed : (-1 pour une seed aléatoire)");
        int seed = -2;

        while (seed < -1) {

            System.out.println("Entrez un nombre :");

            String resS = System.console().readLine();
            try {
                seed = Integer.parseInt(resS);
            } catch (NumberFormatException e) {
                System.out.println("Entrez un nombre valide");
            }
        }

        //ask nb_cartes_reflexion
        System.out.println("Entrez un nombre de cartes pour la reflexion des algos (1 ou 2) :");
        int nb_cartes_reflexion = -1;
        while(nb_cartes_reflexion != 1 && nb_cartes_reflexion != 2){
            System.out.println("Entrez un nombre valide :");
            String resS = System.console().readLine();
            try {
                nb_cartes_reflexion = Integer.parseInt(resS);
            } catch (NumberFormatException e) {
                System.out.println("Entrez un nombre valide");
            }
        }


        //ask discussion entre les joueurs
        
        System.out.println("Entrez 1 pour activer les discussions entre les joueurs, 0 sinon :");
        int res_discussion = -1;
        while (res_discussion != 0 && res_discussion != 1) {
            System.out.println("Entrez un nombre valide :");
            String resS = System.console().readLine();
            try {
                res_discussion = Integer.parseInt(resS);
            } catch (NumberFormatException e) {
                System.out.println("Entrez un nombre valide");
            }
        }


        //Ask si tour à tour
        System.out.println("Entrez 1 pour activer le tour à tour, 0 sinon :");
        int res_tour_a_tour = -1;
        while (res_tour_a_tour != 0 && res_tour_a_tour != 1) {
            System.out.println("Entrez un nombre valide :");
            String resS = System.console().readLine();
            try {
                res_tour_a_tour = Integer.parseInt(resS);
            } catch (NumberFormatException e) {
                System.out.println("Entrez un nombre valide");
            }
        }
        boolean tour_a_tour = res_tour_a_tour == 1;


        Pioche pioche = new Pioche(seed);
        System.out.println("Pioche : " + pioche.toString());
        FileWriter writer;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_HH'h'mm");
        String fileName = "Data/"+"data_"+LocalDateTime.now().format(formatter)+"_";
        for(int i = 0; i < tab_type_joueur.length; i++){
            fileName += dict_algo_val.get(tab_type_joueur[i]) + "--";
        }
        fileName = fileName.substring(0, fileName.length() - 2);

        if(res2 == 10000000)
            fileName += "_10M";
        if(res2 == 1000000)
            fileName += "_1M";
        if(res2 == 100000)
            fileName += "_100k";
        if(res2 == 10000)
            fileName += "_10k";
        if(res2 == 1000)
            fileName += "_1k";
        if(res2 == 100)
            fileName += "_100";
        if(res2 == 10)
            fileName += "_10";
        if(res2 == 1)
            fileName += "_1";



        if(seed == -1)
            fileName += "_seedAlea";
        else
            fileName += "_seed"+seed;


        if(res_discussion == 1)
            fileName += "_discussion";
        
        fileName +=".csv";
        

        try {
            writer = new FileWriter(fileName, true);
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur ouverture fichier");
            return;
            }


        System.out.println("OK c'est Parti !");
        int seedInitiale = seed;
        
        for (int i = 0; i < res2; i++) {

            Jeu jeu = new Jeu(tab_type_joueur, pioche, nb_cartes_reflexion, res_discussion == 1,tour_a_tour);
            
            jeu.jouer();

            try {
                writer.write(i + ";" + jeu.getScore() + "\n");
                Jeu.logger.warning("Partie "+i+" ; Score : " + jeu.getScore());
            } catch (IOException e) {
                e.printStackTrace();
            }

           jeu.pioche.reset((seed == -1) ? seed : ++seed);
        }
        
        String plotName = "";
        for(int i = 0; i < tab_type_joueur.length; i++){
            plotName += dict_algo_val.get(tab_type_joueur[i]) + "--";
        }
        plotName = plotName.substring(0, plotName.length() - 2);
        
        if(res2 == 10000000)
            plotName += "_10M";
        if(res2 == 1000000)
            plotName += "_1M";
        if(res2 == 100000)
            plotName += "_100k";
        if(res2 == 10000)
            plotName += "_10k";
        if(res2 == 1000)
            plotName += "_1k";
        if(res2 == 100)
            plotName += "_100";
        if(res2 == 10)
            plotName += "_10";
        if(res2 == 1)
            plotName += "_1";

        if(seed == -1)
            plotName += "_seedAlea";
        else
            plotName += "_seed"+seedInitiale;
        
        if(res_discussion == 1)
            plotName += "_discussion";
        
        Export_Graphiques.export(fileName, plotName);
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur fermeture fichier");
            return;
        }
    }
}
