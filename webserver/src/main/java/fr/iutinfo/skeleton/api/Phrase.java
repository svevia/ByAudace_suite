package fr.iutinfo.skeleton.api;
import com.google.common.base.Charsets;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.Principal;
import java.security.SecureRandom;

public class Phrase implements Principal {
   // final static Logger logger = LoggerFactory.getLogger(User.class);

    /* Obligatoire */
    private String phrase;
    private String besoin;
    private String mail;
    private boolean terminee;
    private int consultee;

    public Phrase(String phrase, String besoin, String mail) {
        this.phrase = phrase;
        this.besoin = besoin;
        this.mail = mail;
        this.terminee = false;
        this.consultee = 0;
    }

    public String getPhrase() {
        return this.phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setBesoin(String besoin) {
        this.besoin = besoin;
    }

    public String getBesoin() {
        return this.besoin;
    }

    public boolean getTerminee() {
        return this.terminee;
    }

    public void setTerminee(boolean terminee) {
        this.terminee = terminee;
    }

    @Override
    public boolean equals(Object arg) {
        if (getClass() != arg.getClass())
            return false;
        Phrase ph = (Phrase) arg;
        return phrase.equals(ph.phrase) && besoin.equals(ph.besoin);
    }

    @Override
    public String toString() {
        return phrase+ " (" + besoin + ")";
    }
}
