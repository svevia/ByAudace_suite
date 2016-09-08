package fr.api;



public class Mail {
	
	private String message;
	private String sujet;
	private String adresse;
	private String categories;//un json contenant la liste des categories selectionnés
	
	
	public Mail() {
		
	}



	public Mail(String message, String sujet, String adresse, String categories) {
		this.message = message;
		this.sujet = sujet;
		this.adresse = adresse;
		this.categories = categories;
	}


	

	public String getCategories() {
		return categories;
	}



	public void setCategories(String categories) {
		this.categories = categories;
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
