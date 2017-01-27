package fr.api;
/**
 * Un utilisateur propose son aide pour la phrase d'un autre utilisateur
 * L'objet stock donc l'utilisateur proposant son aide, la phrase aidé et la date de l'event
 *
 */
public class Aide {

	private int phrase;
	private int utilisateur;
	private String date;
	
	
	public Aide(){
		
	}
	
	public Aide(int user, int phrase){
		this.utilisateur = user;
		this.phrase = phrase;
	}


	public int getPhrase() {
		return phrase;
	}


	public void setPhrase(int phrase) {
		this.phrase = phrase;
	}

	public int getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(int utilisateur) {
		this.utilisateur = utilisateur;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
}
