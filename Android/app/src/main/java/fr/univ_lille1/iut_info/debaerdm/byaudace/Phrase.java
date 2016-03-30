package fr.univ_lille1.iut_info.debaerdm.byaudace;

/**
 * Created by debaerdm on 25/03/16.
 */
public class Phrase {

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
        this.terminee = false;
        this.numero = numero;
        this.consultee = 0;
    }

    public String getPhrase() {
        return this.phrase;
    }

    public String getMail() {
        return this.mail;
    }

    public String getBesoin() {
        return this.besoin;
    }

    public boolean getTerminee() {
        return this.terminee;
    }


    public int getConsultee() {
        return this.consultee;
    }

    public String toString() {
        return this.phrase+ " (" + this.besoin + ")";
    }
}
