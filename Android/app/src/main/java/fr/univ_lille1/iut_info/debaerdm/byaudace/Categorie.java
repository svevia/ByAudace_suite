package fr.univ_lille1.iut_info.debaerdm.byaudace;

/**
 * Created by Rémy on 06/07/2016.
 */
public enum Categorie {

    JURIDIQUE("Juridique",0),
    MARCHE("Marché",1),
    PARTENARIAT("Partenariat",2),
    RH("Ressources humaines",3),
    TECHNIQUE("Technique",4),
    AUTRE("Autre",5);

    private int id = 0;
    private String name = "";

    Categorie(String name, int id){
        this.name = name;
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public int getId(){
        return id;
    }

}
