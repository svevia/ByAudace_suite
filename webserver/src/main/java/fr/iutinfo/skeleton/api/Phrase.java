package fr.iutinfo.skeleton.api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Phrase  {
    final static Logger logger = LoggerFactory.getLogger(Phrase.class);

    /* Obligatoire */
    private String phrase;
    private String besoin;
    private String mail_deposant;
    private boolean terminee;
    private String numero;
    private int consultee;

    public Phrase(){

    }

    public Phrase(String phrase, String besoin, String mail_deposant, String numero) {
        this.phrase = phrase;
        this.besoin = besoin;
        this.mail_deposant = mail_deposant;
        this.terminee = false;
        this.consultee = 0;
    }

    public String getPhrase() {
        return this.phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getMail_deposant() {
        return this.mail_deposant;
    }

    public void setMail_deposant(String mail_deposant) {
        this.mail_deposant = mail_deposant;
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
