import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 5000;

    public static void main(String[] args) {
        try {
            // server connection
            Socket socket = new Socket(SERVER_IP, PORT);
            System.out.println("\033[1m[CLIENT]\033[0m Connected to " + SERVER_IP + ":" + PORT);

            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            // server i/o
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // thread to recive messages
            new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (IOException e) {
                    System.out.println("\033[1m[CLIENT]\033[0m Connection closed.");
                }
            }).start();

            // main thread: send messages
            String userInput;
            while ((userInput = input.readLine()) != null) {
                out.println(userInput);
            }

            socket.close();
        } catch (IOException e) {
            System.out.println("\033[1m[CLIENT]\033[0m Could not connect: " + e.getMessage());
        }
    }
}
