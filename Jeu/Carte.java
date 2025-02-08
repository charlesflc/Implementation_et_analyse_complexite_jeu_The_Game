package Jeu;
public class Carte {

    private static int cpt = 2;
    public final int valeur;


    public Carte(){
        if(cpt>99){
            System.out.println("Erreur, trop de carte demandées, on ne peut pas créer plus de 99 cartes.") ;
        }
        this.valeur = cpt;
        cpt++;
    }
    public Carte(int num_carte){

        if(num_carte <99 && num_carte >1){
        this.valeur = num_carte;
        cpt++;
        }
        else{
            System.err.println("Erreur valeur carte");
            valeur = -1;
        }

    }


    public String toString(){
        return "Carte "+valeur;
    }
    public Integer getValeur(){
        return valeur;
    }
    public static void reset(){
        cpt = 2;
    }


    public boolean equals(Carte obj){
        return this.valeur == obj.valeur;
    }
}
