package fr.univ_lille1.iut_info.debaerdm.byaudace;

/**
 * La classe Phrase est utilisée pour représenter un couple phrae métier / besoin.
 * Elle contient également le mail de l'utilisateur l'ayant postée, ainsi que plusieurs informations
 * statistiques nécessaires à la gestion de ses instanciations par la pile de l'application Android
 * ainsi que par le panel admin.
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

    /**
     * La méthode toString renvoie l'état actuel de l'objet sous forme de String.
     * Elle est notamment utilisée pour obtenir une prévisualisation rapide de l'objet dans la pile
     * de l'application Android.
     * Si le besoin ou la phrase métier sont considérés comme trop longs, ils seront raccourcis à la
     * longueur maximale autorisée.
     */
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
