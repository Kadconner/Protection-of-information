import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

import static libraries.lab1_4.*;
import static libraries.lab3.*;

public class lab4 {

    public static int mental_poker (int NumberOfPlayers) {
        if (NumberOfPlayers>23 || NumberOfPlayers<2) {
            System.out.println("The number of players must be from 2 inclusive to 24 exclusively");
            return 0;
        }


        BigInteger range = (((BigInteger.TEN.pow(9)).subtract(BigInteger.TWO)).add(BigInteger.ONE));
        BigInteger rangeP = (((BigInteger.TEN.pow(12)).subtract(BigInteger.TWO)).add(BigInteger.ONE));
        BigInteger buf = BigInteger.ONE, Q;

        HashMap <BigInteger, String> DeckForOutput = new HashMap<>();

        String[] KeysForDeck = new String[] {"2 Peaks", "3 Peaks", "4 Peaks", "5 Peaks", "6 Peaks", "7 Peaks",
                "8 Peaks", "9 Peaks", "10 Peaks", "J Peaks", "Q Peaks", "K Peaks", "A Peaks", "2 Worms",
                "3 Worms", "4 Worms", "5 Worms", "6 Worms", "7 Worms", "8 Worms", "9 Worms", "10 Worms",
                "J Worms", "Q Worms", "K Worms", "A Worms", "2 Baptize", "3 Baptize", "4 Baptize", "5 Baptize",
                "6 Baptize", "7 Baptize", "8 Baptize", "9 Baptize", "10 Baptize", "J Baptize", "Q Baptize", "K Baptize",
                "A Baptize", "2 Booby", "3 Booby", "4 Booby", "5 Booby", "6 Booby", "7 Booby", "8 Booby", "9 Booby",
                "10 Booby", "J Booby", "Q Booby", "K Booby", "A Booby"};


        int k = 0;

        while (DeckForOutput.size() != 52) {
            buf = nextRandomBigInteger(range).add(BigInteger.TWO);
            while (DeckForOutput.containsKey(buf)) {
                buf = nextRandomBigInteger(range).add(BigInteger.TWO);
            }
            DeckForOutput.put(buf, KeysForDeck[k]);
            k++;
        }

        ArrayList<BigInteger> ValuesForDeck = new ArrayList<>(DeckForOutput.keySet());

        BigInteger P;

        do {
            Q = nextRandomBigInteger(rangeP).add(BigInteger.TWO);
            Q = Q.nextProbablePrime();
            P = (BigInteger.TWO.multiply(Q)).add(BigInteger.ONE);
        }
        while (!BigFerma(Q) || !BigFerma(P));



        inversion Inv = new inversion();

        Vector<BigInteger> C = new Vector<BigInteger>(NumberOfPlayers);
        Vector<BigInteger> D = new Vector<BigInteger>(NumberOfPlayers);
        for (int i = 0; i < NumberOfPlayers; i++) {
            Inv.GenerateKeys(P.subtract(BigInteger.ONE));
            C.addElement(Inv.GetValue1());
            D.addElement(Inv.GetValue2());


        }
        for (int j = 0; j < C.size(); j++) {
            //System.out.println("c = " + C.get(j));
        }

        for (int j = 0; j < D.size(); j++) {
            //System.out.println("d = " + D.get(j));
        }


        for (int j = 0; j < NumberOfPlayers; j++) {
            for (int i = 0; i < 52; i++) {
                ValuesForDeck.set(i, Fast_Modulo_Exponentiation(ValuesForDeck.get(i), C.get(j), P));
                //System.out.println("Deck shif " + ValuesForDeck.get(i));
            }
            Collections.shuffle(ValuesForDeck);
        }


        for (int i = 0; i < NumberOfPlayers; i++) {
            for (int j = 0; j < NumberOfPlayers; j++) {
                if (i != j) {
                    ValuesForDeck.set((2*(i+1))-1, Fast_Modulo_Exponentiation(ValuesForDeck.get((2*(i+1))-1), D.get(j), P));
                    ValuesForDeck.set(2*i, Fast_Modulo_Exponentiation(ValuesForDeck.get(2*i), D.get(j), P));
                }
            }
            int j = i;
            System.out.println("Player cards number " + i + ":");
            System.out.println("    "+ DeckForOutput.get(Fast_Modulo_Exponentiation(ValuesForDeck.get((2*(i+1))-1), D.get(j), P)) + ": " + Fast_Modulo_Exponentiation(ValuesForDeck.get((2*(i+1))-1), D.get(j), P));
            System.out.println("    "+DeckForOutput.get(Fast_Modulo_Exponentiation(ValuesForDeck.get(2*i), D.get(j), P)) + ": " + Fast_Modulo_Exponentiation(ValuesForDeck.get(2*i), D.get(j), P));
            DeckForOutput.remove(Fast_Modulo_Exponentiation(ValuesForDeck.get((2*(i+1))-1), D.get(j), P));
            DeckForOutput.remove(Fast_Modulo_Exponentiation(ValuesForDeck.get(2*i), D.get(j), P));
        }
        System.out.println();
        System.out.println(DeckForOutput.size() + "cards on the table");


        System.out.println("Deck = " + DeckForOutput);
        return 0;

    }
    public static void main(String[] args) throws IOException {

        Scanner s = new Scanner(System.in);
        System.out.println("Enter the number of players from 2 to 24");
        int n = s.nextInt();
        mental_poker(n);

    }
}
