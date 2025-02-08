package Algorithmes;

import Jeu.*;


import Exceptions.ExceptionJoueurPeutPasJouer;

public abstract class Algomere extends Joueur {

    protected Base baseouJouer;
    protected int indice_carte_a_jouer;
    protected Base baseouJouer2;
    protected int indice_carte_a_jouer2;


    protected Algomere(int nb_cartes, Jeu jeu){
        super(nb_cartes, jeu);
        this.nom = "Algo mere";
        this.baseouJouer = null;
        this.indice_carte_a_jouer = -1;
        this.baseouJouer2 = null;
        this.indice_carte_a_jouer2 = -1;
    }

    public abstract void strategie(Jeu jeu);
    public abstract void strategie2(Jeu jeu);

    public void jouer(Jeu jeu) throws Exceptions.ExceptionJoueurPeutPasJouer{
        int nb_cartes_reflexion = jeu.nb_cartes_reflexion;

        if(nb_cartes_reflexion == 1){
            jouer_1carte(jeu);
        }else if(nb_cartes_reflexion == 2){
            jouer_2cartes(jeu);
        }else{
            System.out.println("Erreur, le nombre de cartes à jouer n'est pas correcte.");
        }
    }

    protected void jouer_1carte(Jeu jeu) throws Exceptions.ExceptionJoueurPeutPasJouer{

        int nb_min_a_jouer = jeu.getNbCartesAJouer();

        for(int j = 0; j < nb_min_a_jouer; j++){
            baseouJouer = null;
            indice_carte_a_jouer = -1;

            jeu.BasesQuestion();
            showDeck();
            Carte deck[] = main.getDeck();

            strategie(jeu);
            
            if(baseouJouer == null){
            
                if(main.estVide()){
                    if(jeu.pioche.size() == 0){
                        Jeu.logger.info("L'algo "+this.nom+" a joué toute ses cartes et la pioche est vide.");
                        return;
                    }
                    else{
                        Jeu.logger.info("L'algo "+this.nom+" n'a plus de carte. il pioche.");
                        main.refill();
                        j--; //continue va augmenter j or pas de carte jouée 
                        continue;
                    }
                }else{
                    throw new ExceptionJoueurPeutPasJouer(this);
                }
            }
            else{
                //jeu.logger.info("Ou l'algo joue : carte : "+deck[indice_carte_a_jouer] + "dans "+baseouJouer.nom+", ecart:"+ecart);
                int possible = baseouJouer.ajouterCarte(deck[indice_carte_a_jouer]);
                if(possible>0){
                    jeu.dejajoue.add(deck[indice_carte_a_jouer].getValeur());
                    main.utiliser_Carte(indice_carte_a_jouer);
                }else{
                    Jeu.logger.warning("Erreur, l'algo "+this.nom+" ne peut pas jouer, il a pourtant vérifié qu'il pouvait jouer.");
                    throw new ExceptionJoueurPeutPasJouer(this);
                }   
            }
        jeu.discuter();
        }

        Carte deck[] = main.getDeck();
        baseouJouer = null;
        indice_carte_a_jouer = -1;
        jouer_utile(jeu);

        while(baseouJouer != null){

            int possible = baseouJouer.ajouterCarte(deck[indice_carte_a_jouer]);
            if(possible>0){
                jeu.dejajoue.add(deck[indice_carte_a_jouer].getValeur());
                main.utiliser_Carte(indice_carte_a_jouer);
            }else{
                Jeu.logger.warning("Erreur, l'algo "+this.nom+" ne peut pas jouer, il a pourtant vérifié qu'il pouvait jouer.");
                throw new ExceptionJoueurPeutPasJouer(this);
            }
            baseouJouer = null;
            indice_carte_a_jouer = -1;
            jeu.discuter();
            jouer_utile(jeu);
        
        }
    
        main.refill();

    }


    protected void jouer_2cartes(Jeu jeu) throws Exceptions.ExceptionJoueurPeutPasJouer{

        int nb_min_a_jouer = jeu.getNbCartesAJouer();
        int j;

        if(nb_min_a_jouer == 1){
            jouer_1carte(jeu);
            return;
        }


    //le premier for c'est si on veut jouer les 2 premieres cartes à chaque fois
        baseouJouer = null;
        indice_carte_a_jouer = -1;
        baseouJouer2 = null;
        indice_carte_a_jouer2 = -1;

        jeu.BasesQuestion();
        showDeck();
        Carte deck[] = main.getDeck();

        strategie2(jeu); //strategie à 2 cartes (quand on peut jouer 2 cartes)

        if(baseouJouer == null || baseouJouer2 == null){
        
            if(main.estVide()){
                if(jeu.pioche.size() == 0){
                    Jeu.logger.info("L'algo "+this.nom+" a joué toute ses cartes et la pioche est vide.");
                    return;
                }
                else{
                    Jeu.logger.info("L'algo "+this.nom+" n'a plus de carte. il pioche.");
                    main.refill();
                    return;
                }
            }else{
                throw new ExceptionJoueurPeutPasJouer(this);
            }
        
        }
        else{
            //jeu.logger.info("Ou l'algo joue : carte : "+deck[indice_carte_a_jouer] + "dans "+baseouJouer.nom+", ecart:"+ecart);

            int possible1 = baseouJouer.ajouterCarte(deck[indice_carte_a_jouer]);
            if(possible1>0){
                jeu.dejajoue.add(deck[indice_carte_a_jouer].getValeur());
                main.utiliser_Carte(indice_carte_a_jouer);
            }else{
                Jeu.logger.warning("Erreur, l'algo "+this.nom+" ne peut pas jouer, il a pourtant vérifié qu'il pouvait jouer.");
                throw new ExceptionJoueurPeutPasJouer(this);
            }
            
            //jeu.logger.info("Ou l'algo joue : carte : "+deck[indice_carte_a_jouer] + "dans "+baseouJouer.nom+", ecart:"+ecart);
            int possible2 = baseouJouer2.ajouterCarte(deck[indice_carte_a_jouer2]);
            if(possible2>0){
                jeu.dejajoue.add(deck[indice_carte_a_jouer2].getValeur());
                main.utiliser_Carte(indice_carte_a_jouer2);
            }else{
                Jeu.logger.warning("Erreur, l'algo "+this.nom+" ne peut pas jouer, il a pourtant vérifié qu'il pouvait jouer.");
                throw new ExceptionJoueurPeutPasJouer(this);
            }

            baseouJouer = null;
            indice_carte_a_jouer = -1;
            jouer_utile(jeu);

            while(baseouJouer != null){

                int possible = baseouJouer.ajouterCarte(deck[indice_carte_a_jouer]);
                if(possible>0){
                    jeu.dejajoue.add(deck[indice_carte_a_jouer].getValeur());
                    main.utiliser_Carte(indice_carte_a_jouer);
                }else{
                    Jeu.logger.warning("Erreur, l'algo "+this.nom+" ne peut pas jouer, il a pourtant vérifié qu'il pouvait jouer.");
                    throw new ExceptionJoueurPeutPasJouer(this);
                }
                baseouJouer = null;
                indice_carte_a_jouer = -1;
                jouer_utile(jeu);
            }
            main.refill();
        }
}


protected void jouer_utile(Jeu jeu){

    int asc1 = jeu.baseasc1.getLatestCarte();
    int asc2 = jeu.baseasc2.getLatestCarte();
    int desc1 = jeu.basedesc1.getLatestCarte();
    int desc2 = jeu.basedesc2.getLatestCarte();

    Carte deck[] = main.getDeck();


    for(int i =0; i< deck.length ; i++){
        Carte carte = deck[i];
        if(carte == null){
            continue;
        }
        if(carte.getValeur() == asc1+1){
            if( (jeu.dejajoue.contains(asc1-10))){
                // On peut jouer car ça ne bloque pas un saut de 10
                baseouJouer = jeu.baseasc1;
                indice_carte_a_jouer = i;
                return;
            }
        }
        if(carte.getValeur() == asc2+1){
            if( (jeu.dejajoue.contains(asc2-10))){
                // On peut jouer car ça ne bloque pas un saut de 10
                baseouJouer = jeu.baseasc2;
                indice_carte_a_jouer = i;
                return;
            }
        }
        if(carte.getValeur() == desc1-1){
            if( (jeu.dejajoue.contains(desc1+10))){
                // On peut jouer car ça ne bloque pas un saut de 10
                baseouJouer = jeu.basedesc1;
                indice_carte_a_jouer = i;
                return;
            }
        }
        if(carte.getValeur() == desc2-1){
            if( (jeu.dejajoue.contains(desc2+10))){
                // On peut jouer car ça ne bloque pas un saut de 10
                baseouJouer = jeu.basedesc2;
                indice_carte_a_jouer = i;
                return;
            }
        return;
        }
    }

}


protected int best_val_discussion(int indice_base,Jeu jeu){
    int best_discu = 0;
    Joueur best = null;
    for (Joueur key : jeu.getHashMap().keySet()) {
        if(key == this){
            continue;
        }
 
        Double val = jeu.getHashMap().get(key)[indice_base-1];
        if(val > best_discu){
            best_discu = val.intValue();
            best = key;
        }
    }
    return best_discu;

}

    public  void showDeck(){
        Jeu.logger.info("====== Deck ======");
        int i = 1;
        Jeu.logger.info("REMOVED FOR PERFORMANCE");
        
        /*   for(Carte elem : main.getDeck()){
            if(elem != null){
                Jeu.logger.info(i + " : " + elem.toString());
            }
            i++;
        } */   
    }
}