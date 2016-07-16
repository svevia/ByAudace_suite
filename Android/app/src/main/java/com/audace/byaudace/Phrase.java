package com.audace.byaudace;

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
    private Enum catego;
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

        if(categorie == 0) {
            this.catego = Categorie.JURIDIQUE;
        }else if(categorie == 1) {
            this.catego = Categorie.MARCHE;
        }else if(categorie == 2) {
            this.catego = Categorie.PARTENARIAT;
        }else if(categorie == 3) {
            this.catego = Categorie.RH;
        }else if(categorie == 4) {
            this.catego = Categorie.TECHNIQUE;
        } else {
            this.catego = Categorie.AUTRE;
        }
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

    public Enum getCatego(){ return this.catego; }

    /**
     * La méthode toString renvoie l'état actuel de l'objet sous forme de String.
     * Elle est notamment utilisée pour obtenir une prévisualisation rapide de l'objet dans la pile
     * de l'application Android.
     * Si le besoin ou la phrase métier sont considérés comme trop longs, ils seront raccourcis à la
     * longueur maximale autorisée.
     */
    public String toString() {

        return this.getPhrase() + "\n\n" + this.getBesoin();

    }
}
