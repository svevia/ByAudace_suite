package fr.univ_lille1.iut_info.debaerdm.byaudace;

/**
 * La classe Phrase est utilisée pour représenter un couple phrae métier / besoin.
 * Elle contient également le mail de l'utilisateur l'ayant postée, ainsi que plusieurs informations
 * statistiques nécessaires à la gestion de ses instanciations par la pile de l'application Android
 * ainsi que par le panel admin.
 */
public class Phrase {

    private int id;
    private int id_user;
    private int categorie;
    private String phrase;
    private String besoin;
    private boolean terminee;
    private int consultee;
    private boolean deroulee;


    public Phrase(int id, int id_user, String phrase, String besoin, int categorie) {
        this.id = id;
        this.id_user = id_user;
        this.phrase = phrase;
        this.besoin = besoin;
        this.categorie = categorie;
        this.terminee = false;
        this.consultee = 0;
        this.deroulee = false;
    }


    public int getId() {
        return this.id;
    }

    public int getIdUser() {
        return this.id_user;
    }

    public String getPhrase() {
        return this.phrase;
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

    public int getCategorie() {
        return this.categorie;
    }

    public boolean isDeroulee(){
        return this.deroulee;
    }

    public void setDeroulee(boolean b){
        this.deroulee = b;
    }

    public void setIdUser(int id){
        this.id_user = id;
    }

    /**
     * La méthode toString renvoie l'état actuel de l'objet sous forme de String.
     * Elle est notamment utilisée pour obtenir une prévisualisation rapide de l'objet dans la pile
     * de l'application Android.
     * Si le besoin ou la phrase métier sont considérés comme trop longs, ils seront raccourcis à la
     * longueur maximale autorisée.
     */
    public String toString() {
/*
        String ret = "";

        if(this.getBesoin().length() >= 40){
            ret += this.getBesoin().substring(0,40) + "...";
        }else{
            ret += this.getBesoin();
        }
        //ret += ' (' + this.getCategorie() + ')';

        ret += "\n";

        if(this.getPhrase().length() >= 40){
            ret += this.getPhrase().substring(0,40) + "...";
        }else{
            ret += this.getPhrase();
        }

        return ret;
        */

        return this.getBesoin() + "\n\n" + this.getPhrase();

    }
}
