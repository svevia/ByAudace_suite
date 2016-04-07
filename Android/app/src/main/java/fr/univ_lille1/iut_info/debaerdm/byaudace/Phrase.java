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

        String ret = "";

        if(this.getBesoin().length() >= 40){
            ret += this.getBesoin().substring(0,40) + "...";
        }else{
            ret += this.getBesoin();
        }

        ret += "\n";

        if(this.getPhrase().length() >= 40){
            ret += this.getPhrase().substring(0,40) + "...";
        }else{
            ret += this.getPhrase();
        }

        return ret;
    }
}
