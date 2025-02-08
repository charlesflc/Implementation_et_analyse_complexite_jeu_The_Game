package Algorithmes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Jeu.*;


public class Minimiser_ecart_saut10 extends Algomere{


    public Minimiser_ecart_saut10(int nb_cartes, Jeu jeu) {
        super(nb_cartes, jeu);
        this.nom = "Minimiser_ecart saut 10";
    }

    public void strategie(Jeu jeu){
        int asc1 = jeu.baseasc1.getLatestCarte();
        int asc2 = jeu.baseasc2.getLatestCarte();
        int desc1 = jeu.basedesc1.getLatestCarte();
        int desc2 = jeu.basedesc2.getLatestCarte();

        Carte deck[] = main.getDeck();
        Double ecart = 10000.0 ;
        Double discu = 0.0 ;
        if(jeu.discussion_bool)
            discu = 0.1;

        for(int i =0; i< deck.length ; i++){
            Carte carte = deck[i];
            if(carte == null){
                continue;
            }
            int potentiel_ecart = carte.getValeur() - asc1;
            if( (potentiel_ecart == -10 ) ||  (((potentiel_ecart ) > 0) && ((potentiel_ecart + ( discu*best_val_discussion(1, jeu)))  < ecart))){
                ecart = potentiel_ecart + discu*best_val_discussion(1, jeu);
                baseouJouer = jeu.baseasc1;
                indice_carte_a_jouer = i;
            }
            potentiel_ecart = carte.getValeur() - asc2;
            if( (potentiel_ecart == -10) || (((potentiel_ecart) > 0) && ((potentiel_ecart + discu*best_val_discussion(2, jeu)) < ecart ))){
                ecart = potentiel_ecart+discu*best_val_discussion(2, jeu);
                baseouJouer = jeu.baseasc2;
                indice_carte_a_jouer = i;

            }
            potentiel_ecart = desc1 - carte.getValeur();
            if( potentiel_ecart == -10 || ((potentiel_ecart ) > 0 && (potentiel_ecart + discu* best_val_discussion(3, jeu) ) < ecart)){
                ecart = potentiel_ecart+ discu*best_val_discussion(3, jeu);
                baseouJouer = jeu.basedesc1;
                indice_carte_a_jouer = i;

            }
            potentiel_ecart = desc2 - carte.getValeur();
            if( potentiel_ecart == -10 || ((potentiel_ecart ) > 0 && (potentiel_ecart + discu*best_val_discussion(4, jeu)) < ecart)){
                ecart = potentiel_ecart+ discu*best_val_discussion(4, jeu);
                baseouJouer = jeu.basedesc2;
                indice_carte_a_jouer = i;

            }
        }
        if(baseouJouer != null)
            Jeu.logger.info(" L'algo "+this.nom+" joue : carte : "+deck[indice_carte_a_jouer] + " dans "+baseouJouer.nom+", ecart: "+ecart);


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

        List<Map<Carte, Integer>> listeOuJouer = new ArrayList<Map<Carte, Integer>>(4);
        for(int i=0;i<4;i++){
            listeOuJouer.add(new HashMap<Carte, Integer>());
        }

        for(int i=0;i<basesFirsts.length;i++){
            int pouvoir= 0;

            for(int j=0;j<deck.length;j++){
                if(deck[j] == null){
                    continue;
                }
                if(i<2){
                    if(deck[j].getValeur() == basesFirsts[i]+1){
                        listeOuJouer.get(i).put(deck[j], 98);

                    }else if(deck[j].getValeur() == basesFirsts[i]-10 ){
                        listeOuJouer.get(i).put(deck[j], 100);

                    }else if(deck[j].getValeur() > basesFirsts[i]){
                        listeOuJouer.get(i).put(deck[j], 95 - (deck[j].getValeur() - basesFirsts[i]));

                    }
                    

                }else{
                    if(deck[j].getValeur() == basesFirsts[i]-1){
                        listeOuJouer.get(i).put(deck[j], 98);

                    }
                    if(deck[j].getValeur() == basesFirsts[i]+10 ){
                        listeOuJouer.get(i).put(deck[j], 100);

                    }
                    if(deck[j].getValeur() < basesFirsts[i]){
                        listeOuJouer.get(i).put(deck[j], 95 - (basesFirsts[i] - deck[j].getValeur()));

                    }
                }
                
            }
            
            //DEBUG
            //System.out.println(""+jeu.getHashMap().get(this)[i]+" vs " + pouvoir);

        }
        int taboujouer[] = {-1,-1,-1,-1};

        
        for(int j=0;j<4;j++){
            int best_base = -1;
            int best_val = -1;
            Carte carteaDel = null;

            for(int i=0; i<4; i++){

                
                Map<Carte,Integer> current = listeOuJouer.get(i) ;
                for (Map.Entry<Carte, Integer> entry : current.entrySet()) {
                    if(entry.getValue() > best_val){
                        best_val = entry.getValue();
                        best_base = i;
                        carteaDel = entry.getKey();
                    }
                }
            }

            if(carteaDel != null){
                //delete carteAdel from any hasheset from list
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
