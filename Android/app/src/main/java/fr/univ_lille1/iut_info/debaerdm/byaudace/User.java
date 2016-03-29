package fr.univ_lille1.iut_info.debaerdm.byaudace;

/**
 * Created by debaerdm on 29/03/16.
 */
public class User {

    private String name;
    private String prenom;


    public User(String nom, String prenom) {
        this.name = nom;
        this.prenom = prenom;
    }

    public User() {}

    public String getNom() {
        return name;
    }

    public String getPrenom() {
        return prenom;
    }

    @Override
    public String toString() {
        return name + ", " + prenom;
    }

}
