package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientHandler implements Runnable {

    private BufferedReader input;
    private PrintWriter output;
    final Pattern EMAIL_REGEX = Pattern.compile("([a-z0-9_.-]+)@([a-z0-9_.-]+[a-z])[\\.]\\w{2,4}");

    ClientHandler(BufferedReader input, PrintWriter output) {
        this.input = input;
        this.output = output;
    }

    private Emails extractEmails(URL url) {

        Emails matches = new Emails(new HashSet<>());

        String host = url.getHost();
        System.out.println(host);

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String content;

            while ((content = reader.readLine()) != null) {
                Matcher matcher = EMAIL_REGEX.matcher(content);
                while (matcher.find()) {
                    matches.addEmail(matcher.group());
                }
            }
        } catch (IOException e) {
            matches.setErrorCode(2);
            return matches;
        }

        if (!matches.getEmails().isEmpty()) {
            matches.setErrorCode(0);
        }

        return matches;
    }

    @Override
    public void run() {
            String message;

            while (true) {
                try {
                    message = input.readLine();

                    Emails emails = extractEmails(new URL(message));
                    output.println(emails);

                    System.out.println(message);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    }
}
