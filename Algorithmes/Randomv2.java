package Algorithmes;

import Jeu.*;
import java.util.Set;
import java.util.HashSet;


public class Randomv2 extends Algomere{

    private boolean jouableDans(int val, int indice_base, int valeur_base){
        if(indice_base < 2){
            return (val > valeur_base) || (valeur_base - val == 10);
        }
        return (val < valeur_base) || (val - indice_base == 10);
    }

    
    public Randomv2(int nb_cartes, Jeu jeu){
        super(nb_cartes, jeu);
        this.nom = "RandomV2";
    }
    
    public void strategie(Jeu jeu){

        int asc1 = jeu.baseasc1.getLatestCarte();
        int asc2 = jeu.baseasc2.getLatestCarte();
        int desc1 = jeu.basedesc1.getLatestCarte();
        int desc2 = jeu.basedesc2.getLatestCarte();

        Carte deck[] = main.getDeck();
        Set<Carte> dejavu = new HashSet<>();
        int[] tBases ={asc1, asc2, desc1, desc2};
        
        int j = -1;
        int nb_cartes = main.nbcartes();
        while(dejavu.size()<nb_cartes){
            do {
                j = (int) ( Math.random()* deck.length);
            } while((deck[j] == null) || (dejavu.contains(deck[j])));

 
            int k = -1;
            Set<Integer> dejavuBase = new HashSet<>();
            while(dejavuBase.size()<4){
                k = (int) ( Math.random()*4);
                
                if( jouableDans(deck[j].getValeur(), k, tBases[k])){
                    indice_carte_a_jouer = j;
                    switch (k) {
                    case 0:
                        baseouJouer = jeu.baseasc1;
                        break;
                    case 1:
                        baseouJouer = jeu.baseasc2;
                        break;
                    case 2:
                        baseouJouer = jeu.basedesc1;
                        break;
                    case 3:
                        baseouJouer = jeu.basedesc2;
                        break;
                    }
                    return;
                }
                dejavuBase.add(tBases[k]);
            }
            dejavu.add(deck[j]);
        }

    }

    public void strategie2(Jeu jeu){
        System.out.println("erreur ! "+this.nom+ "est une strategie à 1 carte, mais elle appelle stategie2");
    }

    public void discute(Jeu jeu){
        System.out.println("erreur ! "+this.nom+ "est une strategie à 1 carte, mais elle appelle discute");
    }
}