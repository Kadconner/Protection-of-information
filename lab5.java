import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.Scanner;

import static libraries.lab1_4.*;
import static libraries.lab3.*;

public class lab5 {

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

    public static void blind_signature_protocol (BigInteger v, String FIO) throws IOException {
        Random rand = new Random();
        BigInteger P, Q, N, F, c, d, r = BigInteger.ZERO, h_, s, s_, r_1 = BigInteger.ZERO;
        String h = "";
        BigInteger range1 = (((BigInteger.TEN.pow(9)).subtract(BigInteger.TWO)).add(BigInteger.ONE));
        BigInteger range2 = new BigInteger(1024, rand);

        P = nextRandomBigInteger(range2).add(BigInteger.TWO).nextProbablePrime();
        Q = nextRandomBigInteger(range2).add(BigInteger.TWO).nextProbablePrime();

        N = P.multiply(Q);
        F = (P.subtract(BigInteger.ONE)).multiply(Q.subtract(BigInteger.ONE));

        inversion Inv = new inversion();
        Inv.GenerateKeys(F);
        c = Inv.GetValue1();
        d = Inv.GetValue2();

        BigInteger rnd = new BigInteger(512, rand);
        String nString =(rnd + "" + v);
        BigInteger n =new BigInteger(nString);

        BigInteger[] GcdXY = new BigInteger[3];

        do {
            r = nextRandomBigInteger(range1).add(BigInteger.TWO);
            GcdXY = gcd(r, N);
        } while (!GcdXY[0].equals(BigInteger.ONE));


        h = md5(n.toString());

        h_ = (Fast_Modulo_Exponentiation(r, d, N).multiply((new BigInteger(h)).mod(N))).mod(N);

        s_ = Fast_Modulo_Exponentiation(h_, c, N);


        do {
            GcdXY = gcd(N, r);
            if (GcdXY[0].equals(BigInteger.ONE)) {
                r_1 = GcdXY[2];
                while (r_1.compareTo(BigInteger.ZERO) < 1) {
                    r_1  = r_1.add(N);
                }
                if (!((r.multiply(r_1)).mod(N).equals(BigInteger.ONE))) {
                    r_1 = GcdXY[1];
                    while (r_1.compareTo(BigInteger.ZERO) < 1) {
                        r_1  = r_1.add(F);
                    }
                }
            }
            else {
                r = nextRandomBigInteger(range1);
            }

        } while (!((r.multiply(r_1)).mod(N).equals(BigInteger.ONE)));


        s = (s_.multiply(r_1)).mod(N);

        /*
        System.out.println("h =          " + h);
        System.out.println("(s^d)mod N = " + Fast_Modulo_Exponentiation(s, d, N));
        System.out.println("n = " + n);
         */


        File file = new File("bulletin/signature.txt"); //Your file
        FileOutputStream fos = new FileOutputStream(file);
        PrintStream ps = new PrintStream(fos);
        System.setOut(ps);
        System.out.println(n);
        System.out.println(s.toString(16));

        File file1 = new File("bulletin/FIO.txt"); //Your file
        FileOutputStream fos1 = new FileOutputStream(file1);
        PrintStream ps1 = new PrintStream(fos1);
        System.setOut(ps1);
        System.out.println(FIO);

    }

    public static void main(String[] args) throws IOException {
        //BigInteger v = new BigInteger("12345");

        System.out.println("Enter FIO");
        InputStream inputStream = System.in;
        Reader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String FIO = bufferedReader.readLine(); //читаем строку с клавиатуры

        BigInteger v;
        Scanner s = new Scanner(System.in);
        System.out.println("Enter answer option 0, 1 or 2");
        v = new BigInteger(s.next());
        if (v.equals(BigInteger.ZERO)) {
            v = BigInteger.valueOf(123450);
        }
        else if (v.equals(BigInteger.ONE)) {
            v = BigInteger.valueOf(123451);
        }
        else if (v.equals(BigInteger.TWO)) {
            v = BigInteger.valueOf(123452);
        }
        else{
            System.out.println("Please enter correct data");
            return;
        }

        blind_signature_protocol(v, FIO);
    }
}
