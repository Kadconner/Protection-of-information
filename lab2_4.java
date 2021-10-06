
import java.math.BigInteger;

import static libraries.lab1_4.*;

public class lab2_4 {

    public static void Shamir() {
        BigInteger q, p, buf, Ca, Cb, Da, Db, m, X1, X2, X3, X4;
        BigInteger range = (((BigInteger.TEN.pow(9)).subtract(BigInteger.TWO)).add(BigInteger.ONE));
        do {
            buf = nextRandomBigInteger(range).add(BigInteger.TWO);
            q = buf.nextProbablePrime();
            p = (BigInteger.TWO.multiply(q)).add(BigInteger.ONE);
        }
        while (!SimplicityCheck(q) || !SimplicityCheck(p));

        System.out.println("p = " + p);



        Ca = nextRandomBigInteger(range);
        Da = nextRandomBigInteger(range);
        Cb = nextRandomBigInteger(range);
        Db = nextRandomBigInteger(range);
        BigInteger[] GcdXY = new BigInteger[3];


        do {
            GcdXY = gcd(p.subtract(BigInteger.ONE), Ca);
            if (GcdXY[0].equals(BigInteger.ONE)) {
                Da = GcdXY[2];
                if (Da.compareTo(BigInteger.ZERO) < 1) {
                    do {
                        Da  = Da.add(p.subtract(BigInteger.ONE));
                    } while (Da.compareTo(BigInteger.ZERO) < 1);

                }
            }
            else {
                Ca = nextRandomBigInteger(range);
            }
            if (!((Ca.multiply(Da)).mod(p.subtract(BigInteger.ONE)).equals(BigInteger.ONE)) && Da.compareTo(BigInteger.ZERO) > 0) {
                Ca = nextRandomBigInteger(range);
            }
        } while (!((Ca.multiply(Da)).mod(p.subtract(BigInteger.ONE)).equals(BigInteger.ONE)));

        System.out.println("Ca = " + Ca);
        System.out.println("Da = " + Da);


        do {
            GcdXY = gcd(p.subtract(BigInteger.ONE), Cb);
            if (GcdXY[0].equals(BigInteger.ONE)) {
                Db = GcdXY[2];
                if (Db.compareTo(BigInteger.ZERO) < 1) {
                    do {
                        Db  = Db.add(p.subtract(BigInteger.ONE));
                    } while (Db.compareTo(BigInteger.ZERO) < 1);

                }
            }
            else {
                Cb = nextRandomBigInteger(range);
            }
            if (!((Cb.multiply(Db)).mod(p.subtract(BigInteger.ONE)).equals(BigInteger.ONE)) && Db.compareTo(BigInteger.ZERO) > 0) {
                Cb = nextRandomBigInteger(range);
            }
        } while (!((Cb.multiply(Db)).mod(p.subtract(BigInteger.ONE)).equals(BigInteger.ONE)));

        System.out.println("Cb = " + Cb);
        System.out.println("Db = " + Db);

        do {
            m = nextRandomBigInteger(range);
        } while (p.compareTo(m) < 1);

        X1 = Fast_Modulo_Exponentiation(m, Ca, p);
        X2 = Fast_Modulo_Exponentiation(X1, Cb, p);
        X3 = Fast_Modulo_Exponentiation(X2, Da, p);
        X4 = Fast_Modulo_Exponentiation(X3, Db, p);

        System.out.println("X4 = " + X4);
        System.out.println(" m = " + m);
    }


    public static void main(String[] args) {

        Shamir();

    }
}
