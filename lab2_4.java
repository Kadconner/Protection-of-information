import java.util.Arrays;
import java.io.*;
import java.math.BigInteger;

import static libraries.lab1_4.*;

public class lab2_4 {

    public static void Shamir() throws IOException{
        BigInteger q, p, buf, Ca, Cb, Da, Db, X1, X2, X3, X4;
        X4 = BigInteger.ZERO;
        BigInteger m = BigInteger.ZERO;
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


        FileInputStream inputStream = new FileInputStream("Shamir/data.txt");
        //BufferedReader inputStream = new BufferedReader(new FileReader("data.txt"));
        FileOutputStream outputshif = new FileOutputStream("Shamir/dataShif.txt");
        //BufferedWriter outputshif = new BufferedWriter(new FileWriter("dataShif.txt"));
        FileOutputStream outputStream = new FileOutputStream("Shamir/dataDesh.txt");
        //BufferedWriter outputStream = new BufferedWriter(new FileWriter("dataDesh.txt"));
        int i0;
        while((i0=inputStream.read())!= -1) {
            m = BigInteger.valueOf(i0);


            X1 = Fast_Modulo_Exponentiation(m, Ca, p);
            X2 = Fast_Modulo_Exponentiation(X1, Cb, p);

            int i01;
            i01 = X2.intValue();

            outputshif.write(i01); //записать блок(часть блока) во второй поток
            X3 = Fast_Modulo_Exponentiation(X2, Da, p);
            X4 = Fast_Modulo_Exponentiation(X3, Db, p);

            int i02;
            i02 = X4.intValue();

            outputStream.write(i02); //записать блок(часть блока) во второй поток
        }

        System.out.println("X4 = " + X4);
        System.out.println(" m = " + m);






        FileInputStream inputStream1 = new FileInputStream("Shamir/picture.png");
        FileOutputStream outputshif1 = new FileOutputStream("Shamir/pictShif.png");
        FileOutputStream outputStream1 = new FileOutputStream("Shamir/pictDesh.png");
        int i1;
        while((i1=inputStream1.read())!= -1) {
            m = BigInteger.valueOf(i1);


            X1 = Fast_Modulo_Exponentiation(m, Ca, p);
            X2 = Fast_Modulo_Exponentiation(X1, Cb, p);

            int i11;
            i11 = X2.intValue();

            outputshif1.write(i11); //записать блок(часть блока) во второй поток
            X3 = Fast_Modulo_Exponentiation(X2, Da, p);
            X4 = Fast_Modulo_Exponentiation(X3, Db, p);

            int i12;
            i12 = X4.intValue();

            outputStream1.write(i12); //записать блок(часть блока) во второй поток
        }



        FileInputStream inputStream2 = new FileInputStream("Shamir/pedef.pdf");
        FileOutputStream outputshif2 = new FileOutputStream("Shamir/pedefShif.pdf");
        FileOutputStream outputStream2 = new FileOutputStream("Shamir/pedefDesh.pdf");
        int i2;
        while((i2=inputStream2.read())!= -1) {
            m = BigInteger.valueOf(i2);


            X1 = Fast_Modulo_Exponentiation(m, Ca, p);
            X2 = Fast_Modulo_Exponentiation(X1, Cb, p);

            int i21;
            i21 = X2.intValue();

            outputshif2.write(i21); //записать блок(часть блока) во второй поток
            X3 = Fast_Modulo_Exponentiation(X2, Da, p);
            X4 = Fast_Modulo_Exponentiation(X3, Db, p);

            int i22;
            i22 = X4.intValue();

            outputStream2.write(i22); //записать блок(часть блока) во второй поток
        }


    }



    public static void ElGamal() throws IOException {

        BigInteger p, buf, q, g, m;
        BigInteger Ca, Cb, Cc, Da, Db, Dc;
        BigInteger k, e, r;

        BigInteger range = (((BigInteger.TEN.pow(9)).subtract(BigInteger.TWO)).add(BigInteger.ONE));
        do {
            buf = nextRandomBigInteger(range).add(BigInteger.TWO);
            q = buf.nextProbablePrime();
            p = (BigInteger.TWO.multiply(q)).add(BigInteger.ONE);
        }
        while (!SimplicityCheck(q) || !SimplicityCheck(p));

        System.out.println("p = " + p);

        do {
            g = nextRandomBigInteger(range).add(BigInteger.TWO);

        }
        while (Fast_Modulo_Exponentiation(g, q, p).compareTo(BigInteger.ONE) == 0);

        System.out.println("g = " + g);

        do {
            Ca = nextRandomBigInteger(range).add(BigInteger.TWO);
        }
        while (Ca.compareTo(p.subtract(BigInteger.ONE)) != -1);
        Da = Fast_Modulo_Exponentiation(g, Ca, p);
        System.out.println("Ca = " + Ca);
        System.out.println("Da = " + Da);

        do {
            Cb = nextRandomBigInteger(range).add(BigInteger.TWO);
        }
        while (Cb.compareTo(p.subtract(BigInteger.ONE)) != -1);
        Db = Fast_Modulo_Exponentiation(g, Cb, p);
        System.out.println("Cb = " + Cb);
        System.out.println("Db = " + Db);

        do {
            Cc = nextRandomBigInteger(range).add(BigInteger.TWO);
        }
        while (Cc.compareTo(p.subtract(BigInteger.ONE)) != -1);
        Dc = Fast_Modulo_Exponentiation(g, Cc, p);
        System.out.println("Cc = " + Cc);
        System.out.println("Dc = " + Dc);

        do {
            k = nextRandomBigInteger(range).add(BigInteger.TWO);
        }
        while (k.compareTo(p.subtract(BigInteger.TWO)) != -1);
        System.out.println("k = " + k);

        r = Fast_Modulo_Exponentiation(g, k ,p);
        System.out.println("r = " + r);

        FileInputStream inputStream = new FileInputStream("ElGamal/data.txt");
        FileOutputStream outputshif = new FileOutputStream("ElGamal/dataShif.txt");
        FileOutputStream outputStream = new FileOutputStream("ElGamal/dataDesh.txt");
        int i0;
        while((i0=inputStream.read())!= -1) {
            m = BigInteger.valueOf(i0);


            e = ((m.mod(p)).multiply(Fast_Modulo_Exponentiation(Db, k, p))).mod(p);

            int i01;
            i01 = e.intValue();

            outputshif.write(i01);


            int i02;
            i02 = ((e.mod(p).multiply(Fast_Modulo_Exponentiation(r, p.subtract(BigInteger.ONE).subtract(Cb), p))).mod(p)).intValue();

            outputStream.write(i02);
        }


        FileInputStream inputStream1 = new FileInputStream("ElGamal/picture.png");
        FileOutputStream outputshif1 = new FileOutputStream("ElGamal/pictShif.png");
        FileOutputStream outputStream1 = new FileOutputStream("ElGamal/pictDesh.png");
        int i1;
        while((i1=inputStream1.read())!= -1) {
            m = BigInteger.valueOf(i1);


            e = ((m.mod(p)).multiply(Fast_Modulo_Exponentiation(Db, k, p))).mod(p);

            int i11;
            i11 = e.intValue();

            outputshif1.write(i11);


            int i12;
            i12 = ((e.mod(p).multiply(Fast_Modulo_Exponentiation(r, p.subtract(BigInteger.ONE).subtract(Cb), p))).mod(p)).intValue();

            outputStream1.write(i12);
        }


        FileInputStream inputStream2 = new FileInputStream("ElGamal/pedef.pdf");
        FileOutputStream outputshif2 = new FileOutputStream("ElGamal/pedefShif.pdf");
        FileOutputStream outputStream2 = new FileOutputStream("ElGamal/pedefDesh.pdf");
        int i2;
        while((i2=inputStream2.read())!= -1) {
            m = BigInteger.valueOf(i2);


            e = ((m.mod(p)).multiply(Fast_Modulo_Exponentiation(Db, k, p))).mod(p);

            int i21;
            i21 = e.intValue();

            outputshif2.write(i21);


            int i22;
            i22 = ((e.mod(p).multiply(Fast_Modulo_Exponentiation(r, p.subtract(BigInteger.ONE).subtract(Cb), p))).mod(p)).intValue();

            outputStream2.write(i22);
        }



        //System.out.println("e = " + e);

        //System.out.println("m' = " + (e.mod(p).multiply(Fast_Modulo_Exponentiation(r, p.subtract(BigInteger.ONE).subtract(Cb), p))).mod(p));

    }

    public static void RSA () throws IOException {

        BigInteger Pa, Qa, Pb, Qb, Pc, Qc, Na, Nb, Nc, Fa, Fb, Fc;
        BigInteger da, db, dc, ca, cb, cc;
        BigInteger m, e;
        BigInteger[] GcdXY = new BigInteger[3];
        BigInteger range = (((BigInteger.TEN.pow(9)).subtract(BigInteger.TWO)).add(BigInteger.ONE));

        Pa = nextRandomBigInteger(range).add(BigInteger.TWO).nextProbablePrime();
        Qa = nextRandomBigInteger(range).add(BigInteger.TWO).nextProbablePrime();
        Pb = nextRandomBigInteger(range).add(BigInteger.TWO).nextProbablePrime();
        Qb = nextRandomBigInteger(range).add(BigInteger.TWO).nextProbablePrime();
        Pc = nextRandomBigInteger(range).add(BigInteger.TWO).nextProbablePrime();
        Qc = nextRandomBigInteger(range).add(BigInteger.TWO).nextProbablePrime();


        Na = Pa.multiply(Qa);
        Nb = Pb.multiply(Qb);
        Nc = Pc.multiply(Qc);

        Fa = (Pa.subtract(BigInteger.ONE)).multiply(Qa.subtract(BigInteger.ONE));
        Fb = (Pb.subtract(BigInteger.ONE)).multiply(Qb.subtract(BigInteger.ONE));
        Fc = (Pc.subtract(BigInteger.ONE)).multiply(Qc.subtract(BigInteger.ONE));

        do {
            db = nextRandomBigInteger(range).add(BigInteger.TWO);
            GcdXY = gcd(db, Fb);
        } while ((db.compareTo(Fb) > -1) && GcdXY[0] != BigInteger.ZERO);

        do {
            dc = nextRandomBigInteger(range).add(BigInteger.TWO);
            GcdXY = gcd(dc, Fc);
        } while ((dc.compareTo(Fc) > -1) && GcdXY[0] != BigInteger.ZERO);


        ca = BigInteger.ZERO;
        cb = BigInteger.ZERO;
        cc = BigInteger.ZERO;

        do {
            do {
                da = nextRandomBigInteger(range);
            } while ((da.compareTo(Fa) > -1));
            GcdXY = gcd(Fa, da);
            if (GcdXY[0].equals(BigInteger.ONE)) {
                ca = GcdXY[2];
                if (ca.compareTo(BigInteger.ZERO) < 1) {
                    do {
                        ca  = ca.add(Fa);
                    } while (ca.compareTo(BigInteger.ZERO) < 1);

                }
                if (!((da.multiply(ca)).mod(Fa).equals(BigInteger.ONE))) {
                    ca = GcdXY[1];
                    if (ca.compareTo(BigInteger.ZERO) < 1) {
                        do {
                            ca  = ca.add(Fa);
                        } while (ca.compareTo(BigInteger.ZERO) < 1);

                    }
                }
            }
            else {
                do {
                    da = nextRandomBigInteger(range);
                } while ((da.compareTo(Fa) > -1));
            }

        } while (!((da.multiply(ca)).mod(Fa).equals(BigInteger.ONE)));



        do {
            do {
                db = nextRandomBigInteger(range);
            } while ((db.compareTo(Fb) > -1));
            GcdXY = gcd(Fb, db);
            if (GcdXY[0].equals(BigInteger.ONE)) {
                cb = GcdXY[2];
                if (cb.compareTo(BigInteger.ZERO) < 1) {
                    do {
                        cb  = cb.add(Fb);
                    } while (cb.compareTo(BigInteger.ZERO) < 1);

                }
                if (!((db.multiply(cb)).mod(Fb).equals(BigInteger.ONE))) {
                    cb = GcdXY[1];
                    if (cb.compareTo(BigInteger.ZERO) < 1) {
                        do {
                            cb  = cb.add(Fb);
                        } while (cb.compareTo(BigInteger.ZERO) < 1);

                    }
                }
            }
            else {
                do {
                    db = nextRandomBigInteger(range);
                } while ((db.compareTo(Fb) > -1));
            }

        } while (!((db.multiply(cb)).mod(Fb).equals(BigInteger.ONE)));



        do {
            do {
                dc = nextRandomBigInteger(range);
            } while ((dc.compareTo(Fc) > -1));
            GcdXY = gcd(Fc, dc);
            if (GcdXY[0].equals(BigInteger.ONE)) {
                cc = GcdXY[2];
                if (cc.compareTo(BigInteger.ZERO) < 1) {
                    do {
                        cc  = cc.add(Fc);
                    } while (cc.compareTo(BigInteger.ZERO) < 1);

                }
                if (!((dc.multiply(cc)).mod(Fc).equals(BigInteger.ONE))) {
                    cc = GcdXY[1];
                    if (cc.compareTo(BigInteger.ZERO) < 1) {
                        do {
                            cc  = cc.add(Fc);
                        } while (cc.compareTo(BigInteger.ZERO) < 1);

                    }
                }
            }
            else {
                do {
                    dc = nextRandomBigInteger(range);
                } while ((dc.compareTo(Fc) > -1));
            }

        } while (!((dc.multiply(cc)).mod(Fc).equals(BigInteger.ONE)));



        FileInputStream inputStream = new FileInputStream("RSA/data.txt");
        FileOutputStream outputshif = new FileOutputStream("RSA/dataShif.txt");
        FileOutputStream outputStream = new FileOutputStream("RSA/dataDesh.txt");
        int i0;
        while((i0=inputStream.read())!= -1) {
            m = BigInteger.valueOf(i0);


            e = Fast_Modulo_Exponentiation(m, db, Nb);

            int i01;
            i01 = e.intValue();

            outputshif.write(i01);


            int i02;
            i02 = (Fast_Modulo_Exponentiation(e, cb, Nb)).intValue();

            outputStream.write(i02);
        }


        FileInputStream inputStream1 = new FileInputStream("RSA/picture.png");
        FileOutputStream outputshif1 = new FileOutputStream("RSA/pictShif.png");
        FileOutputStream outputStream1 = new FileOutputStream("RSA/pictDesh.png");
        int i1;
        while((i1=inputStream1.read())!= -1) {
            m = BigInteger.valueOf(i1);


            e = Fast_Modulo_Exponentiation(m, db, Nb);

            int i11;
            i11 = e.intValue();

            outputshif1.write(i11);


            int i12;
            i12 = (Fast_Modulo_Exponentiation(e, cb, Nb)).intValue();

            outputStream1.write(i12);
        }


        FileInputStream inputStream2 = new FileInputStream("RSA/pedef.pdf");
        FileOutputStream outputshif2 = new FileOutputStream("RSA/pedefShif.pdf");
        FileOutputStream outputStream2 = new FileOutputStream("RSA/pedefDesh.pdf");
        int i2;
        while((i2=inputStream2.read())!= -1) {
            m = BigInteger.valueOf(i2);


            e = Fast_Modulo_Exponentiation(m, db, Nb);

            int i21;
            i21 = e.intValue();

            outputshif2.write(i21);


            int i22;
            i22 = (Fast_Modulo_Exponentiation(e, cb, Nb)).intValue();

            outputStream2.write(i22);
        }





        //System.out.println("m' = " + (Fast_Modulo_Exponentiation(e, cb, Nb));
    }

    public static void Vernam () throws IOException {
        BigInteger m, k, e;
        BigInteger range = (((BigInteger.TEN.pow(9)).subtract(BigInteger.TWO)).add(BigInteger.ONE));
        k = nextRandomBigInteger(range);
        System.out.println("k = " + k);


        FileInputStream inputStream = new FileInputStream("Vernam/data.txt");
        FileOutputStream outputshif = new FileOutputStream("Vernam/dataShif.txt");
        FileOutputStream outputStream = new FileOutputStream("Vernam/dataDesh.txt");
        int i0;
        while((i0=inputStream.read())!= -1) {
            m = BigInteger.valueOf(i0);


            e = m.xor(k);

            int i01;
            i01 = e.intValue();

            outputshif.write(i01);


            int i02;
            i02 = (e.xor(k)).intValue();

            outputStream.write(i02);
        }


        FileInputStream inputStream1 = new FileInputStream("Vernam/picture.png");
        FileOutputStream outputshif1 = new FileOutputStream("Vernam/pictShif.png");
        FileOutputStream outputStream1 = new FileOutputStream("Vernam/pictDesh.png");
        int i1;
        while((i1=inputStream1.read())!= -1) {
            m = BigInteger.valueOf(i1);


            e = m.xor(k);

            int i11;
            i11 = e.intValue();

            outputshif1.write(i11);


            int i12;
            i12 = (e.xor(k)).intValue();

            outputStream1.write(i12);
        }

        FileInputStream inputStream2 = new FileInputStream("Vernam/pedef.pdf");
        FileOutputStream outputshif2 = new FileOutputStream("Vernam/pedefShif.pdf");
        FileOutputStream outputStream2 = new FileOutputStream("Vernam/pedefDesh.pdf");
        int i2;
        while((i2=inputStream2.read())!= -1) {
            m = BigInteger.valueOf(i2);


            e = m.xor(k);

            int i21;
            i21 = e.intValue();

            outputshif2.write(i21);


            int i22;
            i22 = (e.xor(k)).intValue();

            outputStream2.write(i22);
        }




        //System.out.println("e = " + e);
    }

    public static void main(String[] args) throws IOException {

        Shamir();
        ElGamal();
        RSA();
        Vernam();



    }
}
