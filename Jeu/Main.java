package Jeu;

public class Main {

    public final int taille_max;
    private Carte[] deck;
    public int nb_cartes;

    private Pioche pioche;

    public Main(int nb_cartes, Pioche pioche){
        this.deck = new Carte[nb_cartes];
        taille_max = nb_cartes;
        this.pioche = pioche; 
        for(int i =0; i<nb_cartes;i++){
            deck[i] = pioche.piocher();
        }
        this.nb_cartes = nb_cartes;
    }

    public Main(Main main){
        this.taille_max = main.taille_max;
        this.pioche = main.pioche;
        this.deck = main.deck.clone();
        this.nb_cartes = main.nb_cartes;
    }
    public void  setPioche(Pioche pioche){
        this.pioche = pioche;
    }

    public Carte get_Carte(int indice){
        Carte res =  deck[indice];
        return res;
    }
    public void utiliser_Carte(int indice){
        deck[indice] = null;
        nb_cartes--;
    }

    public void utiliser_Carte(Carte carte){
        for(int i = 0; i<taille_max;i++){
            if(deck[i] == carte){
                deck[i] = null;
                nb_cartes--;
                return;
            }
        }
    }

    public Carte[] getDeck(){
        return deck;
    }   

    public void refill(){
        if(pioche.size()==0) return;
        
        for(int i=0; i<taille_max;i++){
            if (deck[i] == null){
                deck[i] = pioche.piocher();
                if(deck[i] != null)nb_cartes++;
            }
        }
    }
    public String toString(){
        String res = "";
        for(Carte elem : deck){
            if(elem != null)
                res += elem.toString() + "\n";
            else
                res += "Pas de carte\n";
        }
        return res;
    }
    public boolean estVide(){
        return nb_cartes == 0;
    }

    public int nbcartes(){
        int res = 0;
        for(int i=0; i< deck.length; i++){
            if(deck[i] != null)res++;
        }
        if(res != nb_cartes)System.err.println("Erreur : nb_cartes : " + nb_cartes + " et res : " + res);
        return res;
    }


}
