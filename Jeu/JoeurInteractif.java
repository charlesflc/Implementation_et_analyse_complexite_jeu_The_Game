package Jeu;

import Exceptions.ExceptionJoueurPeutPasJouer;

import java.util.HashSet;
import java.util.Set;

public class JoeurInteractif extends Joueur{
        
        public JoeurInteractif(int nb_cartes, Jeu jeu){
            super(nb_cartes, jeu);
            System.out.println("Entrez le nom du joueur : ");
            this.nom = System.console().readLine();
            
        }
        
        public void jouer(Jeu jeu) throws ExceptionJoueurPeutPasJouer{

            int nb_min = jeu.getNbCartesAJouer();
            while(true){
                jeu.clearScreen();
                System.out.println("Joueur : "+ this.nom +"\n");
                jeu.BasesQuestion();

                //Detecter fin de tour
                if(nb_min <= 0){

                if(jeu.getNbJoueurs() != 1){ //Si pas solo

                    showDeck();
                    int fin_tour = askFindeTour();
                    if(fin_tour == 1){
                        main.refill();
                        break;
                    }
                    jeu.clearScreen();
                    System.out.println("Joueur : "+ this.nom +"\n");
                  jeu.BasesQuestion();
                }else{
                    main.refill();
                }
            }

                
                //Demander quelle carte il veut jouer
                int choix = askDeck();
                if(choix == -1){
                   continue;
                }

                Carte choiCarte = main.get_Carte(choix);

                //demander dans quel base il veut jouer
                int choix_base = askBase(choiCarte);
                int possible = -1;
                switch(choix_base){
                    case -1:
                        continue;
                    case 1:
                        possible = jeu.baseasc1.ajouterCarte(choiCarte);
                        if(possible>0)
                            main.utiliser_Carte(choix);
                        break;
                        
                    case 2:
                        possible = jeu.baseasc2.ajouterCarte(choiCarte);
                        if(possible>0)
                            main.utiliser_Carte(choix); 

                        break;
                    case 3:
                        possible = jeu.basedesc1.ajouterCarte(choiCarte);
                        if(possible>0)
                            main.utiliser_Carte(choix);
                        break;
                    case 4:
                        possible = jeu.basedesc2.ajouterCarte(choiCarte);
                        if(possible>0)
                            main.utiliser_Carte(choix);
                        break;
                }
                if(possible == -1){
                    System.out.println("Erreur, la carte ne peut pas être jouée dans cette base");
                    try {
                        Thread.sleep(3000);
                      } catch (InterruptedException e) {
                        //print erreur
                        System.err.println("Erreur de sleep dans JoeurInteractif.jouer()");
                        Thread.currentThread().interrupt();
                      }

                    continue;
                }

        
                nb_min--;
                if(0==1){            
                    throw new ExceptionJoueurPeutPasJouer(this);
                }



            }
        }


        public void showDeck(){
            System.out.println("====== Votre Deck ======");
            int i = 1;
            for(Carte elem : main.getDeck()){
                if(elem != null){
                    System.out.println(i + " : " + elem.toString());
                }
                i++;
            } 
            System.out.println("");
        }

        public  int askDeck(){
            System.out.println("====== Votre Deck ======");
            int i = 1;
            Set<Integer> carte_jouable = new HashSet<>();
            for(Carte elem : main.getDeck()){
                if(elem != null){
                    System.out.println(i + " : " + elem.toString());
                    carte_jouable.add(i);
                }

                i++;
            } 
            System.out.println("");

            int choix = -1;

            while( (! carte_jouable.contains(choix)) || main.getDeck()[choix-1] == null){
                System.out.println("Quelle carte jouer ? "+carte_jouable.toString()+" (-1 : retour) : ");
                //vérifier si c'est un int : 
                String res = System.console().readLine();
                try{
                    choix = Integer.parseInt(res);
                        if((choix == -1))
                            return -1;
                }catch(NumberFormatException e){
                    System.out.println("Entrez un nombre valide");
                }

             }
            return choix-1;
        }

        public int askBase(Carte choix){

            System.out.println("Dans quelle Base jouer la "+choix.toString()+" ?");
                int choix_base = -2;
                while((choix_base < 1 || choix_base > 4) && choix_base != -1){
                    System.out.println("Entrez un nombre entre 1 et 4 (-1: retour): ");
                    String res = System.console().readLine();
                    try{
                        choix_base = Integer.parseInt(res);
                    }catch(NumberFormatException e){
                        System.out.println("Entrez un nombre valide");
                    }
                }
                return choix_base;
        }

        public int askFindeTour(){

            System.out.println("Arréter de jouer ? ");
                    System.out.println("1 : Oui");
                    System.out.println("2 : Non");
                    int choix_arret = -1;
                    while(choix_arret < 1 || choix_arret > 2){
                        System.out.println("Entrez un nombre entre 1 et 2 : ");
                        String res = System.console().readLine();
                        try{
                            choix_arret = Integer.parseInt(res);
                        }catch(NumberFormatException e){
                            System.out.println("Entrez un nombre valide");
                        }
                    }
                    return choix_arret;

        }
        public void discute(Jeu jeu){
        }
}