package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class EmailExtractor {
    final static Pattern EMAIL_REGEX = Pattern.compile("([a-z0-9_.-]+)@([a-z0-9_.-]+[a-z])[\\.]\\w{2,4}");

    static Emails extractEmails(URL url) {

        Emails matches = new Emails(new HashSet<>());

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

}
