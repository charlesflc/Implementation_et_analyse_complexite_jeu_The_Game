package Exceptions;

import Jeu.Joueur;

public class ExceptionJoueurPeutPasJouer extends ExceptionTheGame{

    public ExceptionJoueurPeutPasJouer(Joueur joueur){
        super("Le joueur "+joueur.getNom()+" ne peut pas jouer");
    }
    
}
