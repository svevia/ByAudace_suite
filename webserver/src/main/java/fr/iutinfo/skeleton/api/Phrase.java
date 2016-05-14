package fr.iutinfo.skeleton.api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Phrase  {
    final static Logger logger = LoggerFactory.getLogger(Phrase.class);

    /* attributs de la table phrase contenue dans la base de données */
    private int id;
    private String phrase;
    private String besoin;
    private int date;
    private String mail;
    private String categorie;
    private boolean terminee;
    
    /**constucteur**/
    public Phrase(){
    }
    
    /**
     * construteur 
     * @param phrase, besoin, mail, numero
     * terminee initialisee a false et consultee a 0
     **/
    public Phrase(String phrase, String besoin, String mail, String categorie) {
        this.phrase = phrase;
        this.besoin = besoin;
        this.mail = mail;
        this.categorie = categorie;
        this.terminee = false;
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

	public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setBesoin(String besoin) {
        this.besoin = besoin;
    }

    public String getBesoin() {
        return this.besoin;
    }
    

    public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getCategorie() {
        return this.categorie;
    }

    public boolean getTerminee() {
        return this.terminee;
    }

    public void setTerminee(boolean terminee) {
        this.terminee = terminee;
    }

    
    
    public String toString() {
        return this.phrase+ " (" + this.besoin + ")";
    }
}
