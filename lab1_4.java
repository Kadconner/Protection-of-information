import java.math.*;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

public class lab1_4 {


    public static boolean SimplicityCheck(BigInteger value) {

        boolean counter;

            counter = true;
            for (BigInteger i = BigInteger.TWO; i.compareTo(value.sqrt()) < 0; i = i.add(BigInteger.ONE)) {
                if ((value.mod(i)).equals(BigInteger.ZERO)) {
                    counter = false;
                }
            }


        return counter;
    }

    public static BigInteger nextRandomBigInteger(BigInteger n) {
        Random rand = new Random();
        BigInteger result = new BigInteger(n.bitLength(), rand);
        while( result.compareTo(n) >= 0 ) {
            result = new BigInteger(n.bitLength(), rand);
        }
        return result;
    }

    public static BigInteger Fast_Modulo_Exponentiation(BigInteger a, BigInteger x, BigInteger p) {

        System.out.println("a = " + a);
        System.out.println("x = " + x);
        System.out.println("p = " + p);


        BigInteger y = BigInteger.valueOf(1);
        BigInteger xt;

        int t = 1;

        xt=x;
        while (xt.compareTo(BigInteger.ONE) == 1){                     //вычисление t, в котором хранится размер массива
            xt = xt.divide(BigInteger.TWO);                            //двоичного представления числа x,
            t++;                                                       //а также размер массива решений
        }

        BigInteger[] massBinaryX = new BigInteger[t];


        int i1 = 0;
        while (x.compareTo(BigInteger.ONE) == 1){
            massBinaryX[i1] = x.mod(BigInteger.TWO);
            x = x.divide(BigInteger.TWO);
            i1++;
            if (x.equals(BigInteger.ONE)) {
                massBinaryX[i1] = BigInteger.valueOf(1);
            }
        }

        BigInteger[] ArrayOfSolutions = new BigInteger[t];
        ArrayOfSolutions[0] = a.mod(p);
        for (int i = 1; i < t; i++) {
                ArrayOfSolutions[i] = ((ArrayOfSolutions[i - 1]).multiply(ArrayOfSolutions[i - 1])).mod(p);

        }

        for (int i = 0; i < t; i++) {
            if (massBinaryX[i].equals(BigInteger.ONE)) {
                y = (y.multiply(ArrayOfSolutions[i])).mod(p);
            }

        }

        System.out.println("y = " + y);
        return y;
    }


    public static BigInteger[] gcd(BigInteger a, BigInteger b) {
        //BigInteger a = new BigInteger ("19");
        //BigInteger b = new BigInteger ("28");

        System.out.println("a = " + a);
        System.out.println("b = " + b);

        BigInteger x, y, q, swap;

        if (a.compareTo(b) == -1) {
            swap = a;
            a = b;
            b = swap;

        }

        BigInteger[] U = new BigInteger[] {a, BigInteger.ONE, BigInteger.ZERO};
        BigInteger[] V = new BigInteger[] {b, BigInteger.ZERO, BigInteger.ONE};
        BigInteger[] T = new BigInteger[3];

       while (U[0].compareTo(BigInteger.ZERO) != 0) {
           q = U[0].divide(V[0]);
           T[0] = U[0].mod(V[0]);
           T[1] = U[1].subtract(q.multiply(V[1]));
           T[2] = U[2].subtract(q.multiply(V[2]));
           for (int i = 0; i < 3; i++) {
               U[i] = V[i];
               V[i] = T[i];
           }
           if (V[0].compareTo(BigInteger.ZERO) == 0) {
               break;
           }

       }
        System.out.println("gcd = " + U[0]);
        System.out.println("x = " + U[1]);
        System.out.println("y = " + U[2]);
       return U;

    }


    public static void Diffie_Hellman() {


        BigInteger q, p, buf;
        BigInteger rangeP = (((BigInteger.TEN.pow(9)).subtract(BigInteger.TWO)).add(BigInteger.ONE));


        do {
            buf = nextRandomBigInteger(rangeP).add(BigInteger.TWO);
            q = buf.nextProbablePrime();
            p = (BigInteger.TWO.multiply(q)).add(BigInteger.ONE);
        }
        while (!SimplicityCheck(q) || !SimplicityCheck(p));

        System.out.println("q = " + q);
        System.out.println("p = " + p);

        BigInteger rangeG = (p.subtract(BigInteger.ONE).subtract(BigInteger.TWO).add(BigInteger.ONE));
        BigInteger g;

        do {
            g = nextRandomBigInteger(rangeG).add(BigInteger.TWO);

        }
        //while (Fast_Modulo_Exponentiation(g, q, p).compareTo(BigInteger.ONE) == 0);
        while (g.modPow(q, p).compareTo(BigInteger.ONE) == 0);
        System.out.println("g = " + g);

        BigInteger Xa, Xb, Xe, Ya, Yb, Ye, Zba, Zab;

        Xa = nextRandomBigInteger(rangeG).add(BigInteger.TWO);
        Xb = nextRandomBigInteger(rangeG).add(BigInteger.TWO);
        Xe = nextRandomBigInteger(rangeG).add(BigInteger.TWO);

        System.out.println("Xa = " + Xa);
        System.out.println("Xb = " + Xb);
        System.out.println("Xe = " + Xe);

        //Ya = Fast_Modulo_Exponentiation(g, Xa, p);
        //Yb = Fast_Modulo_Exponentiation(g, Xb, p);
        //Ye = Fast_Modulo_Exponentiation(g, Xe, p);

        Ya = g.modPow(Xa, p);
        Yb = g.modPow(Xb, p);
        Ye = g.modPow(Xe, p);

        System.out.println("Ya = " + Ya);
        System.out.println("Yb = " + Yb);
        System.out.println("Ye = " + Ye);

        //Zab = Fast_Modulo_Exponentiation(Yb, Xa, p);
        //Zba = Fast_Modulo_Exponentiation(Ya, Xb, p);

        Zab = Yb.modPow(Xa, p);
        Zba = Ya.modPow(Xb, p);

        if (Zab.compareTo(Zba) == 0) {
            System.out.println("Zab = " + Zab + " = " + Zba + " = Zba");
        }
        else {
            System.out.println("Zab != Zba");
            System.out.println(Zab + " != " + Zba);
        }


    }

    public static void BabyStep_GiantStep(BigInteger a, BigInteger p, BigInteger y) {
        BigInteger x, m, k, buf;
        int count = 0;
        BigInteger range = (((BigInteger.TEN.pow(9)).subtract(BigInteger.TWO)).add(BigInteger.ONE));

        while (!SimplicityCheck(p) || (p.compareTo(y) < 1)) {
            a = nextRandomBigInteger(range).add(BigInteger.TWO);
            y = nextRandomBigInteger(range).add(BigInteger.TWO);
            BigInteger rangeP = (((BigInteger.TEN.pow(9)).subtract(y.add(BigInteger.ONE))).add(BigInteger.ONE));
            buf = nextRandomBigInteger(rangeP).add(BigInteger.TWO);
            p = buf.nextProbablePrime();
        }

        m = p.sqrt().add(BigInteger.ONE);
        k = m;


        System.out.println("a = " + a);
        System.out.println("p = " + p);
        System.out.println("y = " + y);
        System.out.println("m = " + m);
        System.out.println("k = " + k);

        BigInteger j, i, j1;
        

        TreeMap<BigInteger, BigInteger> BabyStep = new TreeMap<>();
        TreeMap<BigInteger, BigInteger> GiantStep = new TreeMap<>();


        for (j1 = BigInteger.ZERO; j1.compareTo(m.subtract(BigInteger.ONE)) < 1; j1 = j1.add(BigInteger.ONE)) {
            BabyStep.put(((y.mod(p)).multiply(a.modPow(j1, p))).mod(p), j1);

        }


        for (i = BigInteger.ONE; i.compareTo(k) < 1; i = i.add(BigInteger.ONE)) {
            GiantStep.put(a.modPow(i.multiply(m), p), i);
            if (BabyStep.containsKey(a.modPow(i.multiply(m), p))) {
                j = BabyStep.get(a.modPow(i.multiply(m), p));
                x = i.multiply(m).subtract(j);
                count++;
                if (a.modPow(x, p).equals(y)) {
                    System.out.println("Решение: x = " + x);
                }
                else {
                    System.out.println(a.modPow(x, p) + " != " + y);
                    System.out.println("x = " + x + " не подходит");
                }

            }

        }
       if (count == 0) {
           System.out.println("Решений нет");
       }


    }



}
