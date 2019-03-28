package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

   private String hostname;
   private int port;
   protected Socket client = null;
   protected PrintWriter output;
   protected BufferedReader input;
   protected String username = null;
   protected boolean isConnected;

    public Client(String hostname, int port) {

       this.hostname = hostname;
       this.port = port;
        try {

            Socket client = this.client = new Socket(this.hostname, this.port);
            PrintWriter output = this.output = new PrintWriter(client.getOutputStream(), true);
            BufferedReader input = this.input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(System.in));

            this.isConnected = true;
            System.out.println("connected to the server " + this.client.getRemoteSocketAddress());

            while (this.username == null) {
                System.out.println("Enter in a username");
                System.out.print("> ");

                if ((this.username = stdInput.readLine()) != null) {
                    break;
                }
            }

            String handle = this.username + "> ";
            //display the username before the cursor in the client's chat
            System.out.print(handle);

            String message = stdInput.readLine();
            while (message.compareTo("/quit") != 0) {
                //this sends the client input to the server
                output.println(handle + message);

                //this is what we get back
                System.out.println(input.readLine());

                //re-display the username handle for the client's chat
                System.out.print(handle);

                //make the next user input the new message
                message = stdInput.readLine();

                //this prints out what we get from the server to the client locally
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.isConnected = false;
        }

    }

    public PrintWriter getOutput() {
        return this.output;
    }

    public BufferedReader getInput() {
        return this.input;
    }

    public static void main (String[] args) throws IOException {
//        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//
//        System.out.print("Enter in your username: ");
//        System.out.print("> ");
//        String username = input.readLine();


        new Client("localhost", 59000);
    }


}
