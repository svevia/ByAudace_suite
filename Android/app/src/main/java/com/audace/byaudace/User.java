package com.audace.byaudace;

/**
 * La classe User représente un utilisateur.
 * Utilisée pour l'authentification de celui-ci et le maintien de sa session à travers les activités, celle-ci
 * contient ses nom, prénom et numéro, ainsi que le sel nécessaire au cryptage de son mot de passe.
 * Sont également accessibles depuis ici getters et setters pour les attributs cités-ci dessus.
 */
public class User {

    private String digit;
    private String name;
    private String prenom;
    private String numero;
    private String mot_de_passe;
    private int id;
    private String mail;


    public User(String digit, int id, String mail, String mot_de_passe, String name, String numero, String prenom) {
        this.digit = digit;
        this.id = id;
        this.mail = mail.substring(1,mail.length()-1);
        this.mot_de_passe = mot_de_passe.substring(1,mot_de_passe.length()-1);
        this.name = name.substring(1,name.length()-1);
        this.prenom = prenom.substring(1,prenom.length()-1);
        this.numero = numero.substring(1,numero.length()-1);;

    }

    public User(int id, String digit, String mail, String mdp, String nom, String prenom, String numero){
        this.digit = digit;
        this.id = id;
        this.mail = mail;
        this.mot_de_passe = mdp;
        this.name = nom;
        this.prenom = prenom;
        this.numero = numero;
    }

    public int getId() {
        return id;
    }
    public String getDigit() {
        return digit;
    }
    public String getNom() {
        return name;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getNumero() {
        return numero;
    }
    public String getMail(){ return this.mail; }
    public String getMdp(){ return this.mot_de_passe; }

    @Override
    public String toString() {
        return "Id : " + id + ", digit : " + digit + ", mail : " + mail.toString() + ", nom : "+name.toString() + ", prénom : " + prenom.toString()+ ", numéro : "+ numero.toString() + ", mot de passe : " + mot_de_passe.toString();
    }

}
