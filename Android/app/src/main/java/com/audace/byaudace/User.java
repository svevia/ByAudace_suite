package com.audace.byaudace;

/**
 * La classe User représente un utilisateur.
 * Utilisée pour l'authentification de celui-ci et le maintien de sa session à travers les activités, celle-ci
 * contient ses nom, prénom et numéro, ainsi que le sel nécessaire au cryptage de son mot de passe.
 * Sont également accessibles depuis ici getters et setters pour les attributs cités-ci dessus.
 */
public class User {

    private String digit;
    private String nom;
    private String prenom;
    private String numero;
    private String mdp;
    private int id;
    private String mail;


    public User(String digit, int id, String mail, String mdp, String nom, String numero, String prenom) {
        this.digit = digit;
        this.id = id;
        this.mail = mail.substring(1,mail.length()-1);
        this.mdp = mdp.substring(1,mdp.length()-1);
        this.nom = nom.substring(1,nom.length()-1);
        this.prenom = prenom.substring(1,prenom.length()-1);
        this.numero = numero;

    }

    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getNumero() {
        return numero;
    }
    public String getMail(){ return this.mail; }
    public String getMdp(){ return this.mdp; }

    @Override
    public String toString() {
        return "Nom : "+nom + ", Prenom : " + prenom+ ", Numero : "+ numero;
    }

}
