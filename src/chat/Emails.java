package chat;

import java.util.Set;

public class Emails {

    private int errorCode;
    private Set<String> emails;

    Emails(Set<String> emails) {
        this.errorCode = 1;
        this.emails = emails;
    }

    int getErrorCode() {
        return errorCode;
    }

    void addEmail(String email) {
        this.emails.add(email);
    }

    void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    Set<String> getEmails() {
        return emails;
    }

    void setEmails(Set<String> emails) {
        this.emails = emails;
    }

    @Override
    public String toString() {
        return "errorCode=" + errorCode +
                "," + emails;
    }
}
