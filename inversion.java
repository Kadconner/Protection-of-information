import com.sun.jdi.Value;

import java.math.BigInteger;
import java.util.Random;

import static libraries.lab1_4.*;
import static libraries.lab3.*;

public class inversion {

    Random rand = new Random();
   private BigInteger range = new BigInteger(512, rand);


   private BigInteger Value1 = BigInteger.ZERO;
   private BigInteger Value2 = BigInteger.ZERO;
   private BigInteger[] GcdXY = new BigInteger[3];

    inversion () {}

    void GenerateKeys (BigInteger BaseOfInversion) {
        do {
            this.Value1 = nextRandomBigInteger(range).add(BigInteger.TWO);
            GcdXY = gcd(BaseOfInversion, this.Value1);
            if (GcdXY[0].equals(BigInteger.ONE)) {
                this.Value2 = GcdXY[2];
                while (this.Value2.compareTo(BigInteger.ZERO) < 1) {
                    this.Value2  = this.Value2.add(BaseOfInversion);
                }
                if (!((this.Value1.multiply(this.Value2)).mod(BaseOfInversion).equals(BigInteger.ONE))) {
                    this.Value2 = GcdXY[1];
                    while (this.Value2.compareTo(BigInteger.ZERO) < 1) {
                        this.Value2  = this.Value2.add(BaseOfInversion);
                    }
                }
            }
            else {
                this.Value1 = nextRandomBigInteger(range);
            }

        } while (!((this.Value1.multiply(this.Value2)).mod(BaseOfInversion).equals(BigInteger.ONE)));
    }


    BigInteger GetValue1 () { return Value1; }

    BigInteger GetValue2 () {
        return Value2;
    }

}
