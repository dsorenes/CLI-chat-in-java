package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Server {

    protected ServerSocket server;
    protected int port;
    protected Set<ClientHandler> clients = new HashSet<>();
    protected MessageBroadcaster broadcaster;
    private Set<ClientHandler> disconnected = new HashSet<>();

    public Server(int port) {
        this.port = port;

        try (
                var server = new ServerSocket(this.port)
        ) {

            this.server = server;
            System.out.println("Server is running...");

            while (true) {
                ClientHandler client = new ClientHandler(this.server.accept());
                new Thread(client).start();
                synchronized (clients) {
                    clients.add(client);
                    System.out.println("Clients connected " + clients.size());
                }

                for (ClientHandler c : clients) {
                    if (c.isConnected() == false) disconnected.add(c);

                }

                synchronized (clients) {
                    clients.removeAll(disconnected);
                    disconnected.clear();
                }

                System.out.println("before " + disconnected.size());




                System.out.println("after " + disconnected.size());
                System.out.println("Clients connected " + clients.size());

                broadcaster = new MessageBroadcaster(this.clients);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new Server(59000);
    }
}
