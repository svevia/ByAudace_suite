package fr.univ_lille1.iut_info.debaerdm.byaudace;

/**
 * Created by debaerdm on 29/03/16.
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
