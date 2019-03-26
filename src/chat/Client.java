package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

   private String hostname;
   private int port;
   protected Socket client;

    public Client(String hostname, int port) {

       this.hostname = hostname;
       this.port = port;
        try (
            Socket client = new Socket(this.hostname, this.port);
            PrintWriter output = new PrintWriter(client.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()))
        ){
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(System.in));

            String message;

            while ((message = stdInput.readLine()) != null) {
                //this sends the client input to the server
                output.println(message);

                //this prints out what we get from the server to the client locally
                System.out.println(input.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main (String[] args) {
        new Client("localhost", 59000);
    }
}
