package Jeu;
import java.util.ArrayDeque;

public abstract class Base {
    protected ArrayDeque<Carte> base;
    public final String nom;
    // Classe mère de base asc et base desc à faire

    public Base(String nom){
        this.nom = nom;
        this.base = new ArrayDeque<Carte>();
    }

    public abstract int ajouterCarte(Carte carte);
    public String toString(){
        return ""+this.base.toString();
    }
    public int getLatestCarte(){
        if(this.base.isEmpty()){
            if(this instanceof BaseAsc)
                return 1;
            if(this instanceof BaseDesc)
                return 99;
        }        
        return this.base.getFirst().getValeur();
    }
    public int getSize(){
        return this.base.size();
    }
}
