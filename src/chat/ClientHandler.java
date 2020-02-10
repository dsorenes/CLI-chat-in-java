package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

public class ClientHandler implements Runnable {

    private BufferedReader input;
    private PrintWriter output;

    ClientHandler(BufferedReader input, PrintWriter output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
            String message;

            while (true) {
                try {
                    message = input.readLine();

                    Emails emails = EmailExtractor.extractEmails(new URL(message));
                    output.println(emails);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    }
}
