package fr.api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Phrase  {
    final static Logger logger = LoggerFactory.getLogger(Phrase.class);

    /* attributs de la table phrase contenue dans la base de données */
    private int id;
    private int signalement;
    private String phrase;
    private String besoin;
    private String date;
    private int id_user;
    private int categorie;
    private boolean terminee;
    
    /**constucteur**/
    public Phrase(){
    }
    
    /**
     * construteur 
     * @param phrase, besoin, mail, numero
     * terminee initialisee a false et consultee a 0
     **/
    public Phrase(String phrase, String besoin, int id_user, int categorie) {
        this.phrase = phrase;
        this.besoin = besoin;
        this.id_user = id_user;
        this.categorie = categorie;
        this.terminee = false;
        this.signalement = 0;
    }
    //getters et setters de l'objet
    public String getPhrase() {
        return this.phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


    public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	public void setBesoin(String besoin) {
        this.besoin = besoin;
    }

    public String getBesoin() {
        return this.besoin;
    }
    

    public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setCategorie(int categorie) {
        this.categorie = categorie;
    }

    public int getCategorie() {
        return this.categorie;
    }

    public boolean getTerminee() {
        return this.terminee;
    }

    public void setTerminee(boolean terminee) {
        this.terminee = terminee;
    }
    
    public int getSignalement() {
		return signalement;
	}

	public void setSignalement(int signalement) {
		this.signalement = signalement;
	}

	public String toString() {
        return this.phrase+ " (" + this.besoin + ")";
    }
}
