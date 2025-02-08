package Algorithmes;

import Jeu.*;
import java.lang.Math;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
;


public class Minimiser_gachis extends Algomere{

    public static double a = 0.5;

    public Minimiser_gachis(int nb_cartes, Jeu jeu){
        super(nb_cartes, jeu);
        this.nom = "Minimiser_gachis";
    }


    public void strategie(Jeu jeu){
        int asc1 = jeu.baseasc1.getLatestCarte();
        int asc2 = jeu.baseasc2.getLatestCarte();
        int desc1 = jeu.basedesc1.getLatestCarte();
        int desc2 = jeu.basedesc2.getLatestCarte();

        Carte deck[] = main.getDeck();

        double  tout = 100.0;
        Double discu = 0.0 ;
        if(jeu.discussion_bool)
            discu = 0.1;

        for(int i =0; i< deck.length ; i++){
            Carte carte = deck[i];
            if(carte == null){
                continue;
            }
            int potentiel_ecart = carte.getValeur() - asc1;
            int potentiel_gachis = 0;
            for(int j = Math.max(asc1,12); j<carte.valeur; j++){ 
                if((!jeu.dejajoue.contains(j))&&(!jeu.dejajoue.contains(j-10)))potentiel_gachis++;
            }
            if(((carte.getValeur() - asc1)== -10 ) || (((carte.getValeur() - asc1) > 0) && ((potentiel_ecart+ a *potentiel_gachis +  (discu*best_val_discussion(1, jeu)) ) < tout))){
                tout = potentiel_ecart+a*potentiel_gachis +  (discu*best_val_discussion(1, jeu));
                baseouJouer = jeu.baseasc1;
                indice_carte_a_jouer = i;
            }

            potentiel_ecart = carte.getValeur() - asc2;
            potentiel_gachis = 0;
            for(int j = Math.max(asc2,12); j<carte.valeur; j++){
                if((!jeu.dejajoue.contains(j))&&(!jeu.dejajoue.contains(j-10)))potentiel_gachis++;
            }
            if(((carte.getValeur() - asc2)== -10 ) || (((carte.getValeur() - asc2) > 0) && ((potentiel_ecart + a*potentiel_gachis +  ( discu*best_val_discussion(2, jeu))) < tout))){
                tout = potentiel_ecart+a*potentiel_gachis +  ( discu*best_val_discussion(2, jeu));
                baseouJouer = jeu.baseasc2;
                indice_carte_a_jouer = i;
            }

            potentiel_ecart =  desc1 - carte.getValeur();
            potentiel_gachis = 0;
            for(int j =carte.valeur; j<Math.min(desc1, 88); j++){
                if((!jeu.dejajoue.contains(j))&&(!jeu.dejajoue.contains(j+10))) potentiel_gachis++;
            }
            if(((desc1 - carte.getValeur())== - 10 ) || (((desc1 - carte.getValeur()) > 0) && ((potentiel_ecart  + a*potentiel_gachis + ( discu*best_val_discussion(3, jeu))) < tout))){
                tout = potentiel_ecart+a*potentiel_gachis + ( discu*best_val_discussion(3, jeu));
                baseouJouer = jeu.basedesc1;
                indice_carte_a_jouer = i;
            }
 
            potentiel_ecart =  desc2 - carte.getValeur();
            potentiel_gachis = 0;
            for(int j =carte.valeur; j<Math.min(desc2, 88); j++){
                if((!jeu.dejajoue.contains(j))&&(!jeu.dejajoue.contains(j+10))) potentiel_gachis++;
            }
            if(((desc2 - carte.getValeur())== - 10 ) || (((desc2 - carte.getValeur()) > 0) && ((potentiel_ecart + a*potentiel_gachis +  (discu*best_val_discussion(4, jeu))) < tout))){
                tout = potentiel_ecart + a*potentiel_gachis +  (discu*best_val_discussion(4, jeu));
                baseouJouer = jeu.basedesc2;
                indice_carte_a_jouer = i;
            }
        }
    }

    public void strategie2(Jeu jeu){
        System.out.println("erreur ! "+this.nom+ "est une strategie à 1 carte, mais elle appelle stategie2");
    }

    public  void discute(Jeu jeu){
       int asc1 = jeu.baseasc1.getLatestCarte();
        int asc2 = jeu.baseasc2.getLatestCarte();
        int desc1 = jeu.basedesc1.getLatestCarte();
        int desc2 = jeu.basedesc2.getLatestCarte();

        int basesFirsts[] = {asc1, asc2, desc1, desc2};

        Carte deck[] = main.getDeck();
        

        List<Map<Carte, Double>> listeOuJouer = new ArrayList<Map<Carte, Double>>(4);
        for(int i=0;i<4;i++){
            listeOuJouer.add(new HashMap<Carte, Double>());
        }
        for(int i=0;i<basesFirsts.length;i++){
            int pouvoir= 0;

            for(int j=0;j<deck.length;j++){
                
                if(deck[j] == null){
                    continue;
                }

                if(i<2){
                    int gachis = 0;
                    for(int k = Math.max(basesFirsts[i],12); k<j; k++){ 
                        if((!jeu.dejajoue.contains(k))&&(!jeu.dejajoue.contains(k-10)))gachis++;//calcul du gachis
                    }

                    if(deck[j].getValeur() == basesFirsts[i]+1){
                        listeOuJouer.get(i).put(deck[j], 98.0);

                    }else if(deck[j].getValeur() == basesFirsts[i]-10 ){
                        listeOuJouer.get(i).put(deck[j], 100.0);

                    }else if(deck[j].getValeur() > basesFirsts[i]){
                        listeOuJouer.get(i).put(deck[j], 95 - (deck[j].getValeur() - basesFirsts[i] + a * gachis));
                    }
                }

                else{

                    int gachis = 0;
                    for(int k=j; k<Math.min(basesFirsts[i], 88); k++){
                        if((!jeu.dejajoue.contains(j))&&(!jeu.dejajoue.contains(j+10))) gachis++;
                    }

                    if(deck[j].getValeur() == basesFirsts[i]-1){
                        listeOuJouer.get(i).put(deck[j], 98.0);

                    }
                    if(deck[j].getValeur() == basesFirsts[i]+10 ){
                        listeOuJouer.get(i).put(deck[j], 100.0);

                    }
                    if(deck[j].getValeur() < basesFirsts[i]){
                        listeOuJouer.get(i).put(deck[j], 95 - (basesFirsts[i] - deck[j].getValeur() + a * gachis));

                    }
                }

                

            }
        }

        int taboujouer[] = {-1,-1,-1,-1};

        
        for(int j=0;j<4;j++){
            int best_base = -1;
            Double best_val = -1.0;
            Carte carteaDel = null;

            for(int i=0; i<4; i++){

                
                Map<Carte,Double> current = listeOuJouer.get(i) ;
                for (Map.Entry<Carte, Double> entry : current.entrySet()) {
                    if(entry.getValue() > best_val){
                        best_val = entry.getValue();
                        best_base = i;
                        carteaDel = entry.getKey();
                    }
                }
            }

            if(carteaDel != null){

                for(int i=0; i<4; i++){
                    listeOuJouer.get(i).remove(carteaDel);
                }
                listeOuJouer.get(best_base).clear();
                taboujouer[best_base] = 1;
                jeu.getHashMap().get(this)[best_base] = Double.valueOf(best_val);
            }

        }
        for(int i=0;i<4;i++){
            if(taboujouer[i] == -1){
                jeu.getHashMap().get(this)[i] = 0.0;
            }
        }

    }

}