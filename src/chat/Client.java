package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class Client {

   private String hostname;
   private int port;

    Client(String hostname, int port) {
       this.hostname = hostname;
       this.port = port;

        try (
            Socket client = new Socket(this.hostname, this.port);
            PrintWriter output = new PrintWriter(client.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()))
        ){
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(System.in));

            String message;
            System.out.println("Welcome! Please input a URL: ");

            while ((message = stdInput.readLine()) != null) {
                output.println(message);

                String response = input.readLine();

                if (response.split(",")[0].contains("1")) {
                    System.out.println("!!!No email address found on the page!!!");
                } else if (response.split(",")[0].contains("2")){
                    System.out.println("!!!Server couldn't find the web page!!!");
                } else {
                    String split = response.split("\\[")[1];
                    String emailFix = response.split("\\[")[1].substring(0, split.length() - 1);
                    String[] emails = emailFix.split(", ");
                    for (String email : emails) {
                        System.out.println(email);
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main (String[] args) {
        //find local ip address for eduroam: ifconfig | grep inet
        new Client("localhost", 59000);
    }
}
