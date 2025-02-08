package Jeu;
import Exceptions.ExceptionJoueurPeutPasJouer;

public abstract class Joueur {
    protected String nom;
    protected Main main;

    public Joueur(int nb_cartes, Jeu jeu ){
        main = new Main(nb_cartes, jeu.pioche);
    }
    public void piocher(){
        main.refill();
    }

    public String getNom(){
        return nom;
    }

    public String toString(){
        return "Nom : " + nom + ", main : " + main.toString();
    }
    public abstract void jouer(Jeu jeu) throws ExceptionJoueurPeutPasJouer;
    public abstract void discute(Jeu jeu);
    

}
