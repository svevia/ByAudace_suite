package fr.iutinfo.skeleton.api;
import com.google.common.base.Charsets;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.Principal;
import java.security.SecureRandom;

public class Phrase  {
    final static Logger logger = LoggerFactory.getLogger(Phrase.class);

    /* Obligatoire */
    private String phrase;
    private String besoin;
    private String mail;
    private boolean terminee;
    private String numero;
    private int consultee;

    public Phrase(){

    }

    public Phrase(String phrase, String besoin, String mail, String numero) {
        this.phrase = phrase;
        this.besoin = besoin;
        this.mail = mail;
        this.numero = numero;
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

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNumero() {
        return this.numero;
    }

    public boolean getTerminee() {
        return this.terminee;
    }

    public void setTerminee(boolean terminee) {
        this.terminee = terminee;
    }

    public int getConsultee() {
        return this.consultee;
    }

    public void setConsultee(int consultee) {
        this.consultee = consultee;
    }

    public String toString() {
        return this.phrase+ " (" + this.besoin + ")";
    }
}
