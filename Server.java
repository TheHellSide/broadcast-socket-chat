import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static int PORT = 5000;

    // group of clients
    private static Set<ClientHandler> clients = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("\033[1m[SERVER]\033[0m Listening on port 5000...");

        // infinite connection accepted
        while (true) {
            Socket socket = serverSocket.accept();
            ClientHandler client = new ClientHandler(socket);
            clients.add(client);
            new Thread(client).start(); // start a new client
        }
    }

    // broadcast sender
    static void broadcast(String message) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                client.sendMessage(message);
            }
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private String name;

        public ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            this.out = new PrintWriter(socket.getOutputStream(), true);
        }

        // method to send message
        public void sendMessage(String message) {
            out.println(message);
        }

        public void run() {
            try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
            ) {
                // get name
                out.println("\033[1m[SERVER]\033[0m Welcome!");
                out.println("Please enter your name:");
                name = in.readLine();

                System.out.println("\033[1m[SERVER]\033[0m Client @'" + name + "' connected from " + socket.getInetAddress().getHostAddress() + ":" + socket.getPort());

                sendMessage("\033[1m[SERVER]\033[0m Hello, @" + name + "! You have joined the chat.");
                broadcast("\033[1m[SERVER]\033[0m @" + name + " has joined the chat.");

                String message;
                // send message cicle
                while ((message = in.readLine()) != null) {
                    if (message.trim().isEmpty()) continue;

                    synchronized (clients) {
                        for (ClientHandler client : clients) {
                            if (client != this) {
                                client.sendMessage("\033[1m" + name + "\033[0m: " + message);
                            }
                        }
                    }
                }
            }
            catch (IOException e) {
                System.out.println("\033[1m[SERVER]\033[0m Connection lost with @" + name);
            }
            finally {
                try {
                    socket.close();
                }
                catch (IOException _) {}

                clients.remove(this);
                broadcast("\033[1m[SERVER]\033[0m @" + name + " has left the chat.");
            }
        }
    }
}