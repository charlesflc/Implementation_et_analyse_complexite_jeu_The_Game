package Jeu;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Pioche{
    protected ArrayDeque<Carte> pioche;
    protected int seed = -1;
    public int taille_initiale;


    public Pioche(int seed, int taille){
        taille_initiale = taille;
        if(seed != 1)
            seed = (int)System.currentTimeMillis();

    this.seed = seed;

    ArrayList<Carte> pioche_liste = new ArrayList<Carte>() ;

    for(int i =0; i<taille;i++){
        pioche_liste.add(new Carte());
    }
    Collections.shuffle(pioche_liste, new Random(seed));
    pioche = new ArrayDeque<Carte>(pioche_liste);

    }




    public Pioche(int seed){
        this(seed, 98);
        }


    public Pioche(Pioche pioche){
        this.seed = pioche.seed;
        this.taille_initiale = pioche.taille_initiale;
        this.pioche = pioche.pioche.clone();
    }
    public Carte piocher(){
        if (pioche.isEmpty()){
            return null;
        }
        Carte res = pioche.removeFirst();
        return res;
    }
    public boolean estDans(Carte carte){
        return pioche.contains(carte);
    }
    public String toString(){
        return pioche.toString();
    }

    public void reset(int newSeed){
        Carte.reset();
        ArrayList<Carte> pioche_liste = new ArrayList<Carte>(98) ;

        for(int i =2; i<100;i++){
            pioche_liste.add(new Carte());
        }
        if(newSeed == -1){
            Collections.shuffle(pioche_liste); 
        }else{
            Collections.shuffle(pioche_liste, new Random(newSeed));
        }
        pioche = new ArrayDeque<Carte>(pioche_liste);
        
    }
    public int size(){
        return pioche.size();
    }


    public void clear(){
        Carte.reset();
        ArrayList<Carte> pioche_liste = new ArrayList<Carte>(pioche.size()) ;
        pioche = new ArrayDeque<Carte>(pioche_liste);
        taille_initiale = 0;
    }

    public void ajoutQueuePioche(Carte c){
        this.pioche.addLast(c);
        taille_initiale ++;
    }


    public void resetSize(int newSeed, int size){
        Carte.reset();
        ArrayList<Carte> pioche_liste = new ArrayList<Carte>(size) ;

        for(int i =2; i<size+2;i++){
            pioche_liste.add(new Carte());
        }
        if(newSeed == -1){
            Collections.shuffle(pioche_liste); 
        }else{
            Collections.shuffle(pioche_liste, new Random(newSeed));
        }
        pioche = new ArrayDeque<Carte>(pioche_liste);
        taille_initiale = size;
    }
}


