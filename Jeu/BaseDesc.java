package Jeu;
import java.util.ArrayDeque;


public class BaseDesc extends Base {
    public BaseDesc(String nom){
        super(nom);
    }
    public BaseDesc(BaseDesc base){
        super(base.nom);
        this.base = base.base.clone();
    }

    public int ajouterCarte(Carte carte){

        if (this.base.size() == 0){
            this.base.addFirst(carte);
            return 1;
        }
        if (carte.valeur < this.base.getFirst().valeur){
                this.base.addFirst(carte);
                return 2;
        }   
        if(carte.valeur == (this.base.getFirst().valeur +10)){
            this.base.addFirst(carte);
            return 3 ;
        }
        Jeu.logger.severe("Erreur, la carte " + carte.valeur + " n'est pas plus petite que la première carte de la base:  "+ this.base.getFirst().valeur);
        //throw Excpetion
        return -1;

            
    }


    
}
