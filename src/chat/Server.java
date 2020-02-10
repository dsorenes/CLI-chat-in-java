package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket server;
    private int port;

    Server(int port) {
       this.port = port;

       try (
           var server = new ServerSocket(this.port)
       ){
           this.server = server;
           System.out.println("Server is running...");

           while (true) {
               Socket client = this.server.accept();

               PrintWriter output = new PrintWriter(client.getOutputStream(), true);
               BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));

               ClientHandler handler = new ClientHandler(input, output);

               new Thread(handler).start();

               if (client.isConnected()) {
                   System.out.println("client connected");
                   System.out.println(client.getRemoteSocketAddress());
               }
           }

       } catch (IOException e) {
           e.printStackTrace();
       }
    }

    public static void main(String[] args) {
        new Server(59000);
    }
}
