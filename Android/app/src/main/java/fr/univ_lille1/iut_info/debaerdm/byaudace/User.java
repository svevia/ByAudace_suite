package fr.univ_lille1.iut_info.debaerdm.byaudace;

/**
 * Created by debaerdm on 29/03/16.
 */
public class User {

    private String mail;
    private String name;
    private String prenom;
    private String digit;
    private String numero; // Telephone
    private String mot_de_passe;
    private String role;
    private String salt;


    public User(String mail, String nom, String prenom, String digit, String mot_de_passe, String role, String salt) {
        this.name = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.mot_de_passe = mot_de_passe;
        this.digit = digit;
        this.role = role;
        this.salt = salt;
        this.numero = "";
    }

    public User(String mail, String nom, String prenom, String digit, String mot_de_passe, String role, String salt, String numero) {
        this.name = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.mot_de_passe = mot_de_passe;
        this.digit = digit;
        this.role = role;
        this.salt = salt;
        this.numero = numero;
    }

    public User(){}

    /* Getters Setters */

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNom() {
        return name;
    }

    public void setNom(String nom) {
        this.name = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setMot_de_passe(String pass) {
        this.mot_de_passe = pass;
    }

    public String getMot_de_passe() {
        return this.mot_de_passe;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setDigit(String digit) {
        this.digit = digit;
    }

    public String getDigit() {
        return digit;
    }

    public String getName() {
        return getNom();
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getSalt() {
        return salt;
    }

    @Override
    public String toString() {
        return name + ", " + prenom + " <" + mail + ">";
    }

}
