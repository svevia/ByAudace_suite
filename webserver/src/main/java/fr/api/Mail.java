package fr.api;

public class Mail {
	
	private String message;
	private String sujet;
	private String adresse;
	
	
	
	public Mail() {
		
	}



	public Mail(String message, String sujet, String adresse) {
		this.message = message;
		this.sujet = sujet;
		this.adresse = adresse;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public String getSujet() {
		return sujet;
	}



	public void setSujet(String sujet) {
		this.sujet = sujet;
	}



	public String getAdresse() {
		return adresse;
	}



	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
	

}
