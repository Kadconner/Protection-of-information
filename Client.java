import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Scanner;

import static libraries.lab1_4.*;

public class Client {

    private static Socket clientSocket; //сокет для общения
    private static BufferedReader reader; // нам нужен ридер читающий с консоли, иначе как
    // мы узнаем что хочет сказать клиент?
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет

    /*
    public static void Scammer () {
        try {
            try {
                // адрес - локальный хост, порт - 4004, такой же как у сервера
                clientSocket = new Socket("localhost", 4004); // этой строкой мы запрашиваем
                //  у сервера доступ на соединение
                reader = new BufferedReader(new InputStreamReader(System.in));
                // читать соообщения с сервера
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                // писать туда же
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));


                // если соединение произошло и потоки успешно созданы - мы можем
                //  работать дальше и предложить клиенту что то ввести
                // если нет - вылетит исключение
                String t_String = in.readLine();
                int t = Integer.parseInt(t_String);
                System.out.println("t = " + t);

                String NString = in.readLine();
                BigInteger N = new BigInteger(NString);
                BigInteger range1 = (((BigInteger.TEN.pow(9)).subtract(BigInteger.TWO)).add(BigInteger.ONE));
                BigInteger V;

                int counter = 0;
                BufferedReader br = new BufferedReader(new FileReader("Открытые ключи.txt"));
                String line;
                while ((line = br.readLine()) != null) {
                    // process the line.
                    counter++;

                    switch(counter){
                        case 2:
                            V = new BigInteger(line);
                            break;


                    }
                }
                br.close();

                BigInteger V = ;
                //System.out.println(V);
                out.write(V.toString() + "\n");
                out.flush();
                BigInteger r = BigInteger.ZERO, y, x, e;
                for (int i = 0; i < t; i++) {
                    do {
                        r = nextRandomBigInteger(range1).add(BigInteger.TWO);
                    } while (r.compareTo(N.subtract(BigInteger.ONE)) == 1);


                    x = Fast_Modulo_Exponentiation(r, BigInteger.TWO, N);
                    out.write(x.toString() + "\n");
                    out.flush();

                    String eString = in.readLine();
                    e = new BigInteger(eString);
                    if (e.equals(BigInteger.ZERO)) {
                        y = r;
                    }
                    else {
                        y = r.multiply(S).mod(N);
                    }
                    //System.out.println(y);
                    out.write(y.toString() + "\n");
                    out.flush();
                }

                //String word = reader.readLine(); // ждём пока клиент что-нибудь
                // не напишет в консоль
                //out.write(word + "\n"); // отправляем сообщение на сервер
                out.flush();
                //String serverWord = in.readLine(); // ждём, что скажет сервер
                //System.out.println(serverWord); // получив - выводим на экран
            } finally { // в любом случае необходимо закрыть сокет и потоки
                System.out.println("Клиент был закрыт...");
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

     */

    public static void Honest_User (String Scammer) {
        try {
            try {
                // адрес - локальный хост, порт - 4004, такой же как у сервера
                clientSocket = new Socket("localhost", 4004); // этой строкой мы запрашиваем
                //  у сервера доступ на соединение
                reader = new BufferedReader(new InputStreamReader(System.in));
                // читать соообщения с сервера
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                // писать туда же
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));


                // если соединение произошло и потоки успешно созданы - мы можем
                //  работать дальше и предложить клиенту что то ввести
                // если нет - вылетит исключение
                String t_String = in.readLine();
                int t = Integer.parseInt(t_String);
                System.out.println("t = " + t);

                String NString = in.readLine();
                BigInteger N = new BigInteger(NString);
                //System.out.println(N);
                BigInteger range1 = (((BigInteger.TEN.pow(9)).subtract(BigInteger.TWO)).add(BigInteger.ONE));
                BigInteger S;
                BigInteger[] GcdXY = new BigInteger[3];
                do {
                    S = nextRandomBigInteger(range1).add(BigInteger.TWO);
                    GcdXY = gcd(S, N);
                } while (!GcdXY[0].equals(BigInteger.ONE) && S.compareTo(N.subtract(BigInteger.ONE)) == 1);
                BigInteger V = Fast_Modulo_Exponentiation(S, BigInteger.TWO, N);
                //System.out.println(V);
                out.write(V.toString() + "\n");
                out.flush();
                BigInteger r = BigInteger.ZERO, y, x, e;

                if (Scammer.equals("0")) {
                    for (int i = 0; i < t; i++) {
                        do {
                            r = nextRandomBigInteger(range1).add(BigInteger.TWO);
                        } while (r.compareTo(N.subtract(BigInteger.ONE)) == 1);


                        x = Fast_Modulo_Exponentiation(r, BigInteger.TWO, N);
                        out.write(x.toString() + "\n");
                        out.flush();

                        String eString = in.readLine();
                        e = new BigInteger(eString);
                        if (e.equals(BigInteger.ZERO)) {
                            y = r;
                        }
                        else {
                            y = r.multiply(S).mod(N);
                        }
                        //System.out.println(y);
                        out.write(y.toString() + "\n");
                        out.flush();
                    }
                    String Final = in.readLine();
                    System.out.println(Final);
                    out.flush();
                }
                else {
                    for (int i = 0; i < t; i++) {
                        do {
                            r = nextRandomBigInteger(range1).add(BigInteger.TWO);
                        } while (r.compareTo(N.subtract(BigInteger.ONE)) == 1);

                        if (nextRandomBigInteger(range1).add(BigInteger.TWO).mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
                            x = Fast_Modulo_Exponentiation(r, BigInteger.TWO, N);
                        }
                        else {
                            x = r.multiply(r).divide(V);
                        }

                        out.write(x.toString() + "\n");
                        out.flush();

                        String eString = in.readLine();
                        //e = new BigInteger(eString);
                        y = r;
                        out.write(y.toString() + "\n");
                        out.flush();
                        String Final = in.readLine();
                        System.out.println(Final);
                        out.flush();
                    }
                }


                //String word = reader.readLine(); // ждём пока клиент что-нибудь
                // не напишет в консоль
                //out.write(word + "\n"); // отправляем сообщение на сервер
                out.flush();
                //String serverWord = in.readLine(); // ждём, что скажет сервер
                //System.out.println(serverWord); // получив - выводим на экран
            } finally { // в любом случае необходимо закрыть сокет и потоки
                System.out.println("Клиент был закрыт...");
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void main(String[] args) throws IOException {

        System.out.println("Введите 1 для активации режима мошенника, или 0 для обычного режима");
        while (true) {
            InputStream inputStream = System.in;
            Reader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String Scammer = bufferedReader.readLine();

            if (Scammer.equals("0")) {
                Honest_User(Scammer);
            }
            else if (Scammer.equals("1")) {
                Honest_User(Scammer);
            }
            else {
                System.out.println("Please enter correct data");
                return;
            }

            //Scammer();
        }

    }
}