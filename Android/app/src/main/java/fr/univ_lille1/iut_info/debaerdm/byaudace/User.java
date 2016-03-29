package fr.univ_lille1.iut_info.debaerdm.byaudace;

/**
 * Created by debaerdm on 25/03/16.
 */
public class User {

    private String phrase;
    private String besoin;
    private String mail;
    private boolean terminee;
    private String numero;
    private int consultee;

    public User(){

    }

    public User(String phrase, String besoin, String mail, String numero) {
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
