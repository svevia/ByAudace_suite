package fr.univ_lille1.iut_info.debaerdm.byaudace;

/**
 * La classe User représente un utilisateur.
 * Utilisée pour l'authentification de celui-ci et le maintien de sa session à travers les activités, celle-ci
 * contient ses nom, prénom et numéro, ainsi que le sel nécessaire au cryptage de son mot de passe.
 * Sont également accessibles depuis ici getters et setters pour les attributs cités-ci dessus.
 */
public class User {

    private String name;
    private String prenom;
    private String salt;
    private String numero;


    public User(String nom, String prenom, String salt, String numero) {
        this.name = nom;
        this.prenom = prenom;
        this.salt = salt;
        this.numero = numero;
    }

    public String getNom() {
        return name;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getSalt() {
        return salt;
    }

    public String getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        return "Nom : "+name + ", Prenom : " + prenom+ ", Numero : "+ numero+", Salt : "+salt;
    }

}
