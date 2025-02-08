package Algorithmes;

import Jeu.*;


public class Minimiser_ecart_carte extends Algomere{

    public Minimiser_ecart_carte(int nb_cartes, Jeu jeu){
        super(nb_cartes, jeu);
        this.nom = "Minimiser_ecart_carte";
    }

    public void strategie(Jeu jeu){
        int asc1 = jeu.baseasc1.getLatestCarte();
        int asc2 = jeu.baseasc2.getLatestCarte();
        int desc1 = jeu.basedesc1.getLatestCarte();
        int desc2 = jeu.basedesc2.getLatestCarte();

        Carte deck[] = main.getDeck();
        int ecart = 100 ;

        for(int i =0; i< deck.length ; i++){
            Carte carte = deck[i];
            if(carte == null){
                continue;
            }

            int potentiel_ecart = carte.getValeur() - asc1;
            for(int j = asc1; j<carte.valeur; j++){
                if (jeu.dejajoue.contains(j))potentiel_ecart--;
            }
            if(((carte.getValeur() - asc1)== -10 ) || (((carte.getValeur() - asc1) > 0) && ((potentiel_ecart) < ecart))){
                ecart = potentiel_ecart;
                baseouJouer = jeu.baseasc1;
                indice_carte_a_jouer = i;
            }
            
            potentiel_ecart = carte.getValeur() - asc2;
            for(int j = asc2; j<carte.valeur; j++){
                if (jeu.dejajoue.contains(j)) potentiel_ecart--;
            }
            if(((carte.getValeur() - asc2)== -10 ) || (((carte.getValeur() - asc2) > 0) && ((potentiel_ecart) < ecart))){
                ecart = potentiel_ecart;
                baseouJouer = jeu.baseasc2;
                indice_carte_a_jouer = i;
            }

            potentiel_ecart = desc1 - carte.getValeur();
            for(int j = carte.valeur; j<desc2; j++){
                if (jeu.dejajoue.contains(j)) potentiel_ecart--;
            }

            if(((desc1 - carte.getValeur())== - 10 ) || (((desc1 - carte.getValeur()) > 0) && ((potentiel_ecart) < ecart))){
                ecart = potentiel_ecart;
                baseouJouer = jeu.basedesc1;
                indice_carte_a_jouer = i;
            }

            potentiel_ecart = desc2 - carte.getValeur();
            for(int j = carte.valeur; j<desc2; j++){
                if (jeu.dejajoue.contains(j)) potentiel_ecart--;
            }
            if(((desc2 - carte.getValeur())== - 10 ) || (((desc2 - carte.getValeur()) > 0) && ((potentiel_ecart) < ecart))){
                ecart = potentiel_ecart;
                baseouJouer = jeu.basedesc2;
                indice_carte_a_jouer = i;
            }
        }
    }
    public void strategie2(Jeu jeu){
        System.out.println("erreur ! "+this.nom+ "est une strategie à 1 carte, mais elle appelle stategie2");
    }

    public  void discute(Jeu jeu){
        System.out.println("Je suis un joueur de type Minimiser_ecart_carte, je ne discute pas");
    }
}
