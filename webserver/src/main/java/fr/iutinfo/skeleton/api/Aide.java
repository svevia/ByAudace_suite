package fr.iutinfo.skeleton.api;

public class Aide {

	Phrase phrase;
	User user;
	
	
	public Aide(){
		
	}
	
	public Aide(User user, Phrase phrase){
		this.user = user;
		this.phrase = phrase;
	}


	public Phrase getPhrase() {
		return phrase;
	}


	public void setPhrase(Phrase phrase) {
		this.phrase = phrase;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
	
	
}
