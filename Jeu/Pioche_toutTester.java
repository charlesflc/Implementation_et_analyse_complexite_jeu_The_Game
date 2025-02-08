package Jeu;

import java.util.ArrayList;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.List;

/**
 * Pioche_toutTester
 */
public class Pioche_toutTester extends Pioche{


        public List<List<Carte>> permutations = new ArrayList<>();
   

    public Pioche_toutTester(int seed, int taille) {
        super(seed, taille);

    }
    public BigInteger factorial(int n) {
        BigInteger result = BigInteger.valueOf(1);
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    // Génération de la n-ième permutation
    public List<Carte> getNthPermutation(BigInteger n) {
        List<Carte> result = new ArrayList<>(this.pioche);
        List<Carte> finalPermutation = new ArrayList<>();
        int listSize = result.size();
        BigInteger factorial = factorial(listSize - 1);
        BigInteger index = n;

        for (BigInteger i = BigInteger.valueOf(listSize); i.compareTo(BigInteger.ZERO) > 0; i=i.subtract(BigInteger.ONE)) {
            int pos = (index.divide(factorial)).intValue();

            finalPermutation.add(result.remove(pos));
            index  = index.mod(factorial);
            //if(i.compareTo(BigInteger.ONE) > 0)
            if(i.compareTo(BigInteger.ONE) > 0)
                factorial  = factorial.divide(i.subtract(BigInteger.ONE));
        }

        return finalPermutation;
    }

    public void setPioche(List<Carte> list){
        this.pioche = new ArrayDeque<Carte>(list);
        
    }
    
}