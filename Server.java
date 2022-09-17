import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static libraries.lab1_4.*;
import static libraries.lab3.*;

public class Server {

    private static Socket clientSocket; //сокет для общения
    private static ServerSocket server; // серверсокет
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет

    public static void main(String[] args) {
        try {
            try {
                server = new ServerSocket(4004); // серверсокет прослушивает порт 4004
                System.out.println("Сервер запущен!"); // хорошо бы серверу
                //   объявить о своем запуске
                clientSocket = server.accept(); // accept() будет ждать пока
                //кто-нибудь не захочет подключиться
                try { // установив связь и воссоздав сокет для общения с клиентом можно перейти
                    // к созданию потоков ввода/вывода.
                    // теперь мы можем принимать сообщения
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    // и отправлять
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                    int t = 20 + (int)(Math.random() * 21);
                    out.write(String.valueOf(t) + "\n");
                    out.flush();
                    BigInteger P = BigInteger.ZERO, Q = BigInteger.ZERO, N = BigInteger.ZERO, y, x, V;
                    BigInteger range1 = (((BigInteger.TEN.pow(9)).subtract(BigInteger.TWO)).add(BigInteger.ONE));
                    do {
                        P = nextRandomBigInteger(range1).add(BigInteger.TWO).nextProbablePrime();
                        Q = nextRandomBigInteger(range1).add(BigInteger.TWO).nextProbablePrime();
                        N = P.multiply(Q);
                    } while (BigFerma(N));

                    FileOutputStream output = new FileOutputStream("Открытые ключи.txt");
                    String N_String = (N + "\n");
                    byte[] bufferN = N_String.getBytes();
                    output.write(bufferN, 0, bufferN.length);
                    out.write(String.valueOf(N) + "\n");
                    out.flush();


                    String V_String = in.readLine();
                    V = new BigInteger(V_String);
                    byte[] bufferV = V_String.getBytes();
                    output.write(bufferV, 0, bufferV.length);

                    for (int i = 0; i < t; i++) {
                        String x_String = in.readLine();
                        x = new BigInteger(x_String);
                        //System.out.println(x_String);
                        BigInteger e = nextRandomBigInteger(range1).add(BigInteger.TWO).mod(BigInteger.TWO);

                        out.write(e.toString() + "\n");
                        out.flush();

                        String y_String = in.readLine();

                        y = new BigInteger(y_String);

                        if (e.equals(BigInteger.ZERO)) {
                            if ((y.multiply(y)).mod(N).equals(x.mod(N))) {
                                System.out.println("Переход к следующей аккредитации...");
                            }
                            else {
                                System.out.println("Знание не доказано");
                                out.write("Попался мошенник!" + "\n");
                                out.flush();
                                return;
                            }
                        }
                        else {
                            if ((y.multiply(y)).mod(N).equals(x.multiply(V).mod(N))) {
                                System.out.println("Переход к следующей аккредитации...");
                            }
                            else {
                                System.out.println("Знание не доказано");
                                out.write("Попался мошенник!" + "\n");
                                out.flush();
                                return;
                            }
                        }

                    }
                    System.out.println("Знание доказано");
                    out.write("Знание доказано!" + "\n");
                    out.flush();

                    //String word = in.readLine(); // ждём пока клиент что-нибудь нам напишет
                   // System.out.println(word);
                    // не долго думая отвечает клиенту
                    //out.write("Привет, это Сервер! Подтверждаю, вы написали : " + word + "\n");

                } finally { // в любом случае сокет будет закрыт
                    clientSocket.close();
                    in.close();
                    out.close();
                }
            } finally {
                System.out.println("Сервер закрыт!");
                server.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}