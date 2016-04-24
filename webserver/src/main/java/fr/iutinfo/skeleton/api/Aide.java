package fr.iutinfo.skeleton.api;

public class Aide {

	int phrase;
	String utilisateur;
	String date;
	
	
	public Aide(){
		
	}
	
	public Aide(String user, int phrase){
		this.utilisateur = user;
		this.phrase = phrase;
	}


	public int getPhrase() {
		return phrase;
	}


	public void setPhrase(int phrase) {
		this.phrase = phrase;
	}

	public String getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(String utilisateur) {
		this.utilisateur = utilisateur;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
}
