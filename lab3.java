package libraries;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import static libraries.lab1_4.*;


public class lab3 {

    public static boolean BigFerma(BigInteger n) {
        Random rand = new Random();
        BigInteger[] GcdXY = new BigInteger[3];
        for (int i = 0; i < 100; i++) {
            BigInteger a = (BigInteger.valueOf(rand.nextInt()).mod(n.subtract(BigInteger.TWO))).add(BigInteger.TWO);
            GcdXY = gcd(a, n);
            if(!(GcdXY[0].equals(BigInteger.ONE))){
                return false;
            }
            if (Fast_Modulo_Exponentiation(a, n.subtract(BigInteger.ONE), n).compareTo(BigInteger.ONE) != 0) {
                return false;
            }
        }
        return true;
    }

    public static String md5(String st) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            // тут можно обработать ошибку
            // возникает она если в передаваемый алгоритм в getInstance(,,,) не существует
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5 = bigInt.toString(10);


        return md5;
    }

    public static void RSA () throws IOException {
        BigInteger range = (((BigInteger.TWO.pow(64)).subtract(BigInteger.TWO)).add(BigInteger.ONE));
        BigInteger P, Q, N, F, c, d, m, s, w;
        BigInteger y = BigInteger.ZERO;
        String sign = "";
        c = BigInteger.ZERO;
        BigInteger[] GcdXY = new BigInteger[3];
        P = nextRandomBigInteger(range).add(BigInteger.TWO).nextProbablePrime();
        Q = nextRandomBigInteger(range).add(BigInteger.TWO).nextProbablePrime();

        N = P.multiply(Q);
        F = (P.subtract(BigInteger.ONE)).multiply(Q.subtract(BigInteger.ONE));

        do {
            d = nextRandomBigInteger(range).add(BigInteger.TWO);
            GcdXY = gcd(F, d);
            if (GcdXY[0].equals(BigInteger.ONE)) {
                c = GcdXY[2];
                while (c.compareTo(BigInteger.ZERO) < 1) {
                        c  = c.add(F);
                }
                if (!((d.multiply(c)).mod(F).equals(BigInteger.ONE))) {
                    c = GcdXY[1];
                    while (c.compareTo(BigInteger.ZERO) < 1) {
                            c  = c.add(F);
                    }
                }
            }
            else {
                d = nextRandomBigInteger(range);
            }

        } while (!((d.multiply(c)).mod(F).equals(BigInteger.ONE)));


        FileInputStream inputStream = new FileInputStream("RSA/data.txt");
        FileOutputStream output = new FileOutputStream("RSA/signature.txt");

        int FileRead;
        while((FileRead = inputStream.read())!= -1) {
            m = BigInteger.valueOf(FileRead);
            y = y.add(new BigInteger(md5(m.toString())));

        }
        System.out.println("y = " + y);

        s = Fast_Modulo_Exponentiation(y, c, N);

        w = Fast_Modulo_Exponentiation(s, d, N);
        System.out.println("w = " + w);
        System.out.println("s = " + s);

        sign = s.toString(16);
        System.out.println("sign = " + sign);

        byte[] buffer = sign.getBytes();
        output.write(buffer, 0, buffer.length);

    }

    public static void ElGamal () throws IOException {
        BigInteger p, buf, q, g, m, x, y, k, k1, r, u, s;
        k1 = BigInteger.ZERO;
        String sign = "";
        BigInteger h = BigInteger.ZERO;
        BigInteger[] GcdXY = new BigInteger[3];
        BigInteger range = (((BigInteger.TWO.pow(128)).subtract(BigInteger.TWO)).add(BigInteger.ONE));

        do {
            buf = nextRandomBigInteger(range).add(BigInteger.TWO);
            q = buf.nextProbablePrime();
            p = (BigInteger.TWO.multiply(q)).add(BigInteger.ONE);
        }
        while (!BigFerma(q) || !BigFerma(p));

        System.out.println("p = " + p);

        BigInteger rangeGX = (p.subtract(BigInteger.ONE).subtract(BigInteger.TWO).add(BigInteger.ONE));
        do {
            g = nextRandomBigInteger(rangeGX).add(BigInteger.TWO);

        }
        while (Fast_Modulo_Exponentiation(g, q, p).compareTo(BigInteger.ONE) == 0);

        System.out.println("g = " + g);

        x = nextRandomBigInteger(rangeGX).add(BigInteger.TWO);
        System.out.println("x = " + x);

        y = Fast_Modulo_Exponentiation(g, x, p);

        FileInputStream inputStream = new FileInputStream("ElGamal/pict.png");
        FileOutputStream output = new FileOutputStream("ElGamal/signature.txt");

        int FileRead;
        while ((FileRead = inputStream.read()) != -1) {
            m = BigInteger.valueOf(FileRead);
            h = h.add(new BigInteger(md5(m.toString())));
        }
        System.out.println("hesh = "+ h);

        k = nextRandomBigInteger(rangeGX).add(BigInteger.TWO);
        k1 = nextRandomBigInteger(rangeGX).add(BigInteger.TWO);
        System.out.println("k = " + k);
        do {
            k = nextRandomBigInteger(range).add(BigInteger.TWO);
            GcdXY = gcd(p.subtract(BigInteger.ONE), k);
            if (GcdXY[0].equals(BigInteger.ONE)) {
                k1 = GcdXY[2];
                while (k1.compareTo(BigInteger.ZERO) < 1) {
                    k1  = k1.add(p.subtract(BigInteger.ONE));
                }
                if (!((k.multiply(k1)).mod(p.subtract(BigInteger.ONE)).equals(BigInteger.ONE))) {
                    k1 = GcdXY[1];
                    while (k1.compareTo(BigInteger.ZERO) < 1) {
                        k1  = k1.add(p.subtract(BigInteger.ONE));
                    }
                }
            }
            else {
                k = nextRandomBigInteger(range);
            }

            GcdXY = gcd(k1, k);
            if (!GcdXY[0].equals(BigInteger.ONE))
                k = nextRandomBigInteger(range);

        } while (!((k.multiply(k1)).mod(p.subtract(BigInteger.ONE)).equals(BigInteger.ONE)));

        r = Fast_Modulo_Exponentiation(g, k, p);
        u = (h.subtract(x.multiply(r))).mod(p.subtract(BigInteger.ONE));
        s = k1.multiply(u).mod(p.subtract(BigInteger.ONE));

        System.out.println((Fast_Modulo_Exponentiation(y, r, p).multiply(Fast_Modulo_Exponentiation(r, s, p))).mod(p));
        System.out.println(Fast_Modulo_Exponentiation(g, h, p));

        sign = s.toString(16);
        System.out.println("sign = " + sign);

        byte[] buffer = sign.getBytes();
        output.write(buffer, 0, buffer.length);

    }

    public static void GOST () throws IOException {
        Random rand = new Random();
        BigInteger P, Q, b, a = BigInteger.ZERO, PrimeForQ, g, h = BigInteger.ZERO, m = BigInteger.ZERO, k, r, s, x, y;
        String sign = "";

        do {
            PrimeForQ = new BigInteger(128, rand);
            Q = PrimeForQ.nextProbablePrime();
        }
        while (!BigFerma(Q));

        do {
            b = new BigInteger(768, rand);
            P = b.multiply(Q).add(BigInteger.ONE);
        } while (!(BigFerma(P)));

        do {
            g = new BigInteger(30, rand);
        } while (Fast_Modulo_Exponentiation(g, b, P).compareTo(BigInteger.ONE) > 1);
        a = (Fast_Modulo_Exponentiation(g, b, P));

        x = new BigInteger(30, rand);
        y = Fast_Modulo_Exponentiation(a, x, P);

        FileInputStream inputStream = new FileInputStream("GOST/pict.png");
        FileOutputStream output = new FileOutputStream("GOST/signature.txt");

        int FileRead;
        while ((FileRead = inputStream.read()) != -1) {
            m = BigInteger.valueOf(FileRead);
            h = h.add(new BigInteger(md5(m.toString())));
        }

        do {
            do {
                k = new BigInteger(30, rand);
                r = Fast_Modulo_Exponentiation(a, k, P).mod(Q);
            } while (r.equals(BigInteger.ZERO));
            s = (k.multiply(h).add(x.multiply(r))).mod(Q);
        }while (r.equals(BigInteger.ZERO));

        sign = s.toString(16);
        System.out.println("sign = " + sign);

        byte[] buffer = sign.getBytes();
        output.write(buffer, 0, buffer.length);
    }

    public static void main(String[] args) throws IOException {

        RSA();
        ElGamal();
        GOST();
    }

}
