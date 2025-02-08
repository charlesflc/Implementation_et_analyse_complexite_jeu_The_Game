package Jeu;

import java.util.ArrayDeque;

public class BaseAsc extends Base{
    public BaseAsc(String nom){
        super(nom);
    }
    public BaseAsc(BaseAsc base){
        super(base.nom);
        this.base = base.base.clone();
        
    }

    public int ajouterCarte(Carte carte){
        if (this.base.size() == 0){
            this.base.addFirst(carte);
            return 1;
        }

        if (carte.valeur > this.base.getFirst().valeur){
            this.base.addFirst(carte);
            return 2;
        }


        if(carte.valeur == (this.base.getFirst().valeur -10)){
            this.base.addFirst(carte);
            return 3;
        
        }
        Jeu.logger.severe("Erreur, la carte " + carte.valeur + " n'est pas plus grande que la première carte de la base: "+ this.base.getFirst().valeur);
        return -1;
        //throw Excpetion


    }
    
}
