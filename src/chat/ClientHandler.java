package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;

public class ClientHandler implements Runnable {

    private Socket client;
    private BufferedReader input;
    private PrintWriter output;

    ClientHandler(Socket client, BufferedReader input, PrintWriter output) {
        this.client = client;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {

            while (true) {
                try {
                    String message = this.input.readLine();

                    if (message.equals("quit")) {
                        this.client.close();
                        break;
                    }

                    Emails emails = EmailExtractor.extractEmails(new URL(message));
                    output.println(emails);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    }
}
