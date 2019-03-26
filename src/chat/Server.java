package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    protected ServerSocket server;
    protected int port;

    public Server(int port) {
       this.port = port;

       try (
           var server = new ServerSocket(this.port)
       ){

           this.server = server;
           System.out.println("Server is running...");
           Socket client = server.accept();
           PrintWriter output = new PrintWriter(client.getOutputStream(), true);
           BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
           if (client.isConnected()) {
               System.out.println("client connected");
           }
           String message;

           //the message is what is received from the client
           while ((message = input.readLine()) != null) {
               //this prints the client message locally to the server
               System.out.println(message);
               //this sends the client message back to the client
               output.println("Server: " + message);
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
    }

    public static void main(String[] args) {
        new Server(59000);
    }
}
