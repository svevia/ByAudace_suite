package fr.univ_lille1.iut_info.debaerdm.byaudace;

/**
 * Created by debaerdm on 29/03/16.
 */
public class User {

    private String name;
    private String prenom;
    private String salt;


    public User(String nom, String prenom, String salt) {
        this.name = nom;
        this.prenom = prenom;
        this.salt = salt;
    }

    public User() {}

    public String getNom() {
        return name;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getSalt() {
        return salt;
    }

    @Override
    public String toString() {
        return "Nom : "+name + ", Prenom : " + prenom+ ", Salt : "+salt;
    }

}
