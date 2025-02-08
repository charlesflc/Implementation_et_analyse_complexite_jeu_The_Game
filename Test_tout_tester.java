
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.time.Duration;


import Jeu.* ;
public class Test_tout_tester {
    
    private boolean jouableDans(int val, int indice_base, int valeur_base){

        if(indice_base <= 2){ //Base Asc
            return ((val > valeur_base) /*||  (val == valeur_base - 10)*/ );

        } //Base Desc
        return ((val < valeur_base) /*|| (val == valeur_base + 10) */ );
    }



    private boolean test_jeu(BaseAsc ba1, BaseAsc ba2, BaseDesc bd1, BaseDesc bd2, Pioche pioche, Main main, int indice_appel){
        if(indice_appel == (pioche.taille_initiale)/2 +1){
        if(main.estVide()){
            int nb_cartes_posees = 0;
            for(Base base : new Base[]{ba1, ba2, bd1, bd2}){
                if (base == null)
                    continue;
                nb_cartes_posees += base.getSize();
            }
            if(nb_cartes_posees != pioche.taille_initiale){
                System.out.println("Erreur : Nombre de cartes posées : " + nb_cartes_posees + " et  Nombre de cartes initiales : " + pioche.taille_initiale);
                return false;
            }
/*                 System.out.println("=== Test réussi : ===");
            System.out.println("Etat pioche : " + pioche.toString());
            System.out.println("Etat bases réussite :");
            if(ba1 != null)
                System.out.println("Base Asc 1 : " + ba1.toString());
            if(ba2 != null) System.out.println("Base Asc 2 : " + ba2.toString());
            if(bd1 != null) System.out.println("Base Desc 1 : " + bd1.toString());
            if(bd2 != null) System.out.println("Base Desc 2 : " + bd2.toString());
  */
          
            return true;
        }
        }



        boolean test = false;
        Base[] bases = {ba1, ba2, bd1, bd2};

        for(Carte carte1 : main.getDeck()){
            if(indice_appel == 1){
               // System.out.println("Appel N° " + indice_appel + " Test n° : " + nb_test + " Carte1 : " + carte1.toString() + " Main : \n" + main.toString());
            }
            if(carte1 == null)
                continue;
            
            //check carte 1
            for(int i = 1;i<=4;i++){
                if(bases[i-1] == null)
                    continue;

                boolean jouableC1 = jouableDans(carte1.getValeur(), i, bases[i-1].getLatestCarte());
                if(jouableC1){
                    for(Carte carte2 : main.getDeck()){
                        if(indice_appel == 1){
                            //System.out.println("Appel N° " + indice_appel + " Carte1 : "+carte1.toString()+ " Carte2 : " + carte2.toString() +" Test n° : " + nb_test + " Main :  \n" + main.toString());
                        }
                        
                        if(carte2 == null || carte1.getValeur() == carte2.getValeur() )
                            continue;

                        for(int j=1;j<=4;j++){
                            if(bases[j-1] == null)
                                continue;

                            //System.out.println("Appel N° " + indice_appel + " Test n° : " + nb_test);

                            boolean jouableC2;
                            if(i==j){
                                jouableC2 = jouableDans(carte2.getValeur(), j, carte1.getValeur());
                            }else{ jouableC2 = jouableDans(carte2.getValeur(), j, bases[j-1].getLatestCarte());
                            }
                            if(jouableC2){

                                Instance instanceaEnvoyer = new Instance(ba1, ba2, bd1, bd2, main, pioche);
                                BaseAsc ba1_res = instanceaEnvoyer.baseasc1;
                                BaseAsc ba2_res = instanceaEnvoyer.baseasc2;
                                BaseDesc bd1_res = instanceaEnvoyer.basedesc1;
                                BaseDesc bd2_res = instanceaEnvoyer.basedesc2;
                                Pioche pioche_res = instanceaEnvoyer.pioche;
                                Main main_res = instanceaEnvoyer.main;
                                main_res.setPioche(pioche_res);
                                Base[] bases_res = {ba1_res, ba2_res, bd1_res, bd2_res};
                                
                                int testC1 = bases_res[i-1].ajouterCarte(carte1);
                                main_res.utiliser_Carte(carte1);
                                if(testC1 == -1){
                                    System.out.println("Erreur : Impossible d'ajouter la carte à la base "+i+ " : " + bases[i-1].toString() + "Carte : " + carte1.toString());
                                }

                                int testC2 = bases_res[j-1].ajouterCarte(carte2);
                                main_res.utiliser_Carte(carte2);
                                if(testC2 == -1){
                                    System.out.println("Erreur : Impossible d'ajouter la carte à la base "+j+ " : " + bases[j-1].toString() + "Carte : " + carte2.toString());
                                }
                                main_res.refill();
                               // main_res.nbcartes();
                                if(test_jeu(ba1_res, ba2_res, bd1_res, bd2_res, pioche_res, main_res,indice_appel+1)){
                                    test = true;
                                    return true;
                                }
                            }else{
                            }
                        }
                    }
                }else{
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {

        System.out.println("Entrez 1 pour tester une pioche ou 0 pour tester tout tester ");
        int flag_tout_tester = -1;
        while (flag_tout_tester != 0 && flag_tout_tester != 1 && flag_tout_tester != 2) { 

            System.out.println("Entrez un nombre valide :");
            String resS = System.console().readLine();
            try {
                flag_tout_tester = Integer.parseInt(resS);
            } catch (NumberFormatException e) {
                System.out.println("Entrez un nombre valide");
            }
        }

        
        if(flag_tout_tester == 1 ){
            /* 
            Pioche_toutTester pioche_tout_tester = new Pioche_toutTester(1,18);

            List<Carte> perm = pioche_tout_tester.getNthPermutation(new BigInteger("15109601945452"));
            pioche_tout_tester.setPioche(perm);

            Pioche pioche = new Pioche(pioche_tout_tester);
 */
            
             //int tabpioche[] = {9, 14, 10, 7, 17, 13, 15, 18, 3, 11, 4, 19, 2, 6, 8, 12, 5, 16} ; // Main de 6 et 2 bases : :)
            // int tabpioche[] = {9,7,8,6,5,3,4,2,11,10,13,12,16,15,14,18};
            //int tabpioche[] = {2,3,4,5,6,7};
            int tabpioche[] = {2,8,14,20,4,10,16,22,6,12,18,24,26,9,11,13} ;


            Pioche pioche = new Pioche(1,tabpioche.length);
            System.out.println("Taille tabpioche : "+tabpioche.length);
            System.out.println("TabePioche : "+tabpioche);
            pioche.clear();
            for(int i = 0; i<tabpioche.length;i++){
                pioche.ajoutQueuePioche(new Carte(tabpioche[i]));
            }
     
            System.out.println("Pioche : " + pioche.toString());
            BaseAsc ba1 = new BaseAsc("baseAsc1");
            BaseAsc ba2 = new BaseAsc("baseAsc2");
            BaseDesc bd1 = new BaseDesc("baseDesc1");
            BaseDesc bd2 = new BaseDesc("baseDesc2");

            Main main = new Main(1, pioche);

            System.err.println("Pioche post main : " + pioche.toString());
            Test_tout_tester test = new Test_tout_tester();
            boolean res = test.test_jeu(ba1,ba2,bd1,bd2,pioche,main,1);

            System.err.println("Résultat pour la pioche : " + res);

            return;  
        }


        


        DecimalFormat df = new DecimalFormat("0.0000000000000000000000");
        Pioche_toutTester pioche_tout_tester = new Pioche_toutTester(1,18);

        System.out.println("Nombre de permutations : " + pioche_tout_tester.permutations.size());
        BigInteger nb_perm = pioche_tout_tester.factorial(pioche_tout_tester.taille_initiale);


        System.out.println("Nombre de permutations : " + nb_perm);


//          

  /*  //Permet de vérifier l'unicité des permutations
if(flag_tout_tester == 2 ){
        List<List<Carte>> perms = new ArrayList<>();

        for(BigInteger i = BigInteger.ZERO; i.compareTo(BigInteger.valueOf(10)) < 0; i=i.add(BigInteger.ONE)){

            List<Carte> perm = pioche_tout_tester.getNthPermutation(i);
            System.out.println(perm);

           
        }
        System.out.println("Fin de la vérification des permutations");
        return;
}*/



        BigInteger nb_test_tout_test = BigInteger.valueOf(100000);
        Test_tout_tester test = new Test_tout_tester();
        BigInteger nb_test_reussi = BigInteger.ZERO;
        BigInteger nb_test = BigInteger.ZERO;
        LocalDateTime startTime = LocalDateTime.now();

        //division par 2 de nb_perm
        //nb_perm = nb_perm.divide(BigInteger.valueOf(2));

        for(BigInteger perm_de_pioche = BigInteger.ZERO; perm_de_pioche.compareTo(nb_perm) < 0; perm_de_pioche = perm_de_pioche.add(nb_perm.divide(nb_test_tout_test))){


            if(perm_de_pioche.mod(BigInteger.valueOf(100)).equals(BigInteger.ZERO)){
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println("======= Test tout tester =======");
                if( nb_test_reussi.compareTo(BigInteger.ZERO) > 0){
                BigDecimal result_reussite = new BigDecimal(nb_test_reussi).multiply(new BigDecimal("100.0")).divide(new BigDecimal(nb_test), MathContext.DECIMAL128);
                System.out.println("Taux de réussite : " + result_reussite + "%");
                }

                BigDecimal result = new BigDecimal(perm_de_pioche).multiply(new BigDecimal("100.0")).divide(new BigDecimal(nb_perm), MathContext.DECIMAL128);

                // Calcul du temps écoulé depuis le début de la tâche
                Duration elapsedTime = Duration.between(startTime, LocalDateTime.now());

                double totalEstimatedTime = elapsedTime.toMinutes() / (result.doubleValue() / 100);
                // Calcul du temps restant
                double remainingTime = totalEstimatedTime - elapsedTime.toMinutes();

                // Affichage du temps restant            
                System.out.println("Permutation " + perm_de_pioche+ " sur " + nb_perm + " : " + df.format(result) + "%");
                System.out.println("Temps restant estimé : " + remainingTime + " minutes");
                //flush screen
            }


            List<Carte> perm = pioche_tout_tester.getNthPermutation(perm_de_pioche);
            Carte.reset();
            pioche_tout_tester.setPioche(perm);

            Pioche pioche_a_envoyer = new Pioche(pioche_tout_tester);


            if(perm_de_pioche.equals(new BigInteger("15109601945452"))){
                System.out.println("Perm : 15109601945452");
                System.out.println("Pioche : " + pioche_a_envoyer);
            }


            BaseAsc ba1 = new BaseAsc("baseAsc1");
            BaseAsc ba2 = new BaseAsc("baseAsc2");
            BaseDesc bd1 = new BaseDesc("baseDesc1");
            BaseDesc bd2 = new BaseDesc("baseDesc2");

            Main main = new Main(6, pioche_a_envoyer);

            if(test.test_jeu(ba1, null, bd1, null, pioche_a_envoyer, main, 1)){
                nb_test_reussi = nb_test_reussi.add(BigInteger.ONE);

                //System.out.println("Test réussi : " + nb_test_reussi + " sur " + nb_perm);
            }else{
                System.out.println("Test échoué, indice de la permutation : " + perm_de_pioche);
                System.out.println("Pioche départ: " + new Pioche(pioche_tout_tester));
                //return;
            }
            nb_test = nb_test.add(BigInteger.ONE);
            
        }
        System.out.println("Nombre de tests réussis : " + nb_test_reussi + " sur " + nb_test);
        BigDecimal result = new BigDecimal(nb_test_reussi).multiply(new BigDecimal("100.0")).divide(new BigDecimal(nb_test), MathContext.DECIMAL128);
        System.out.println("Taux de réussite : " + result + "%");
        System.out.println("Temps d'execution  : "+(Duration.between(startTime, LocalDateTime.now()).toMinutes()));
    }

}