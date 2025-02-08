package Algorithmes;
import Jeu.*;

public class Tab_2cartes extends Algomere{

    public Tab_2cartes(int nb_cartes, Jeu jeu){
        super(nb_cartes, jeu);
        this.nom = "Tab_2cartes";
    }

    private boolean jouableDans(int val, int indice_base, int valeur_base){
        if(indice_base < 2){
            return (val > valeur_base) || (valeur_base - val == 10);
        }
        return (val < valeur_base) || (val - indice_base == 10);
    }

    public void strategie2(Jeu jeu){

        int asc1 = jeu.baseasc1.getLatestCarte();
        int asc2 = jeu.baseasc2.getLatestCarte();
        int desc1 = jeu.basedesc1.getLatestCarte();
        int desc2 = jeu.basedesc2.getLatestCarte();

        int[] tBases ={asc1, asc2, desc1, desc2};
        Carte deck[] = main.getDeck();

        int nbmain = deck.length;
        double [][][][] tab  = new double[nbmain][nbmain][4][4];

        double minecart = 1000;
        //creation et remplissage du tableau 
        for(int c1=0; c1<nbmain; c1++){
            if(deck[c1] == null) continue;

            for(int c2=0; c2<nbmain; c2++){
                if(deck[c2] == null) continue;
                if (c2 == c1) continue;

                for (int b1=0; b1<4; b1++){

                    int ecart1 = 100;
                    if (jouableDans(deck[c1].valeur, b1, tBases[b1])){

                        
                        ecart1 =  (b1<2)? deck[c1].valeur - tBases[b1] : tBases[b1]-deck[c1].valeur;
                    }else continue;

                    for(int b2=0; b2<4; b2++){

                        int ecart2 = 100;
                        if(b2 == b1){ // cas ou tu veux jouer la carte 2 par dessus la 1
                            if (jouableDans(deck[c2].valeur, b2, deck[c1].valeur)){

                                ecart2 =  (b2<2)? deck[c2].valeur - deck[c1].valeur : deck[c1].valeur -deck[c2].valeur;

                            }else{
                                continue;
                            }
                        }
                        else{
                            if (jouableDans(deck[c2].valeur, b2, tBases[b2])){

                                ecart2 =  (b2<2)? deck[c2].valeur - tBases[b2] : tBases[b2]-deck[c2].valeur;
                            }else continue;
                        }
                        tab[c1][c2][b1][b2] = fonction(ecart1, ecart2);
                    }
                }
            }
        }
        //Parcourir le tableau et récupérer les 2 cartes et les 23 
        baseouJouer = null;
        baseouJouer2 = null;
        indice_carte_a_jouer = -1;
        indice_carte_a_jouer2 = -1;
        for(int c1=0; c1<nbmain; c1++){
            if(deck[c1] == null) continue;

            for(int c2=0; c2<nbmain; c2++){
                if(deck[c2] == null) continue;
                if (c2 == c1) continue;

                for (int b1=0; b1<4; b1++){

                    for(int b2=0; b2<4; b2++){
                        if(tab[c1][c2][b1][b2] == 0) continue;
                        if(tab[c1][c2][b1][b2] < minecart ){

                            switch (b1) {
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
                            switch (b2) {
                                case 0:
                                    baseouJouer2 = jeu.baseasc1;
                                    break;
                                case 1:
                                    baseouJouer2 = jeu.baseasc2;
                                    break;
                                case 2:
                                    baseouJouer2 = jeu.basedesc1;
                                    break;
                                case 3:
                                    baseouJouer2 = jeu.basedesc2;
                                    break;
                                }
                            indice_carte_a_jouer = c1;
                            indice_carte_a_jouer2 = c2;
                            minecart = tab[c1][c2][b1][b2];
                        }
                    }
                }
            }
        }

    }

    public double fonction(int a, int b){ //fonction à choisir , ici j'ai mis la moyenne arithmétique. 
        return (a+b)/2.0;
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
            if( (potentiel_ecart == -10 ) ||  (((potentiel_ecart ) > 0) && ((potentiel_ecart) < ecart))){
                ecart = potentiel_ecart;
                baseouJouer = jeu.baseasc1;
                indice_carte_a_jouer = i;
            }
            potentiel_ecart = carte.getValeur() - asc2;
            if( (potentiel_ecart == -10) || (((potentiel_ecart) > 0) && ((potentiel_ecart) < ecart ))){
                ecart = potentiel_ecart;
                baseouJouer = jeu.baseasc2;
                indice_carte_a_jouer = i;

            }
            potentiel_ecart = desc1 - carte.getValeur();
            if( potentiel_ecart == -10 || ((potentiel_ecart ) > 0 && (potentiel_ecart ) < ecart)){
                ecart = potentiel_ecart;
                baseouJouer= jeu.basedesc1;
                indice_carte_a_jouer = i;

            }
            potentiel_ecart = desc2 - carte.getValeur();
            if( potentiel_ecart == -10 || ((potentiel_ecart ) > 0 && (potentiel_ecart) < ecart)){
                ecart = potentiel_ecart;
                baseouJouer = jeu.basedesc2;
                indice_carte_a_jouer = i;

            }
        }
        if(baseouJouer != null)
            Jeu.logger.info(" L'algo "+this.nom+" joue : carte : "+deck[indice_carte_a_jouer] + "dans "+baseouJouer.nom+", ecart:"+ecart);
    }

    public void discute(Jeu jeu){
        System.out.println("Je suis un joeur Tabl2Cartes, je ne discute pas");
        
    }
}
