package fr.api;

import java.security.Principal;
import java.security.SecureRandom;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

public class User implements Principal {
    final static Logger logger = LoggerFactory.getLogger(User.class);

    private int id;
    private String mail;
    private String name;
    private String prenom;
    private String digit;
    private String numero; // Telephone
    private String mot_de_passe;//serait crypter juste après
    private String role;
    private String categorie;
    private int anneeFormation;
    private String typeFormation;
    private int numFormation;
    private String salt;
    private int signalement;


    public User(String mail, String name, String prenom, String digit, String mot_de_passe, String role, String numero) {
        this.name = name;
        this.prenom = prenom;
        this.mail = mail;
        this.mot_de_passe = mot_de_passe;
        this.digit = digit;
        this.role = role;
        this.numero = numero;
    }
    
    public User(){}

    /* Getters Setters */


    public String getMail() {
        return mail;
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setMot_de_passe(String pass) {
        this.mot_de_passe = pass;
    }

    public String getMot_de_passe() {
        return this.mot_de_passe;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setDigit(String digit) {
        this.digit = digit;
    }

    public String getDigit() {
        return digit;
    }

    @Override
    public String getName() {
        return name;
    }
    
    

    public void setName(String name) {
		this.name = name;
	}

	public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    /* Other methods */

    public String buildHash(String pass, String s) {
        Hasher hasher = Hashing.md5().newHasher();
        hasher.putString(pass + s, Charsets.UTF_8);
        return hasher.hash().toString();
    }

    public boolean isGoodPassword(String pass) {
        return pass.equals(getMot_de_passe());
    }

    @Override
    public boolean equals(Object arg) {
        if (getClass() != arg.getClass())
            return false;
        User user = (User) arg;
        return mail.equals(user.mail);
    }

    @Override
    public String toString() {
        return name + ", " + prenom + " <" + mail + ">";
    }

    public String getSalt() {
        if (salt == null) {
            salt = generateSalt();
        }
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public int getAnneeFormation() {
		return anneeFormation;
	}

	public void setAnneeFormation(int anneeFormation) {
		this.anneeFormation = anneeFormation;
	}

	public String getTypeFormation() {
		return typeFormation;
	}

	public void setTypeFormation(String typeFormation) {
		this.typeFormation = typeFormation;
	}

	public int getNumFormation() {
		return numFormation;
	}

	public void setNumFormation(int numFormation) {
		this.numFormation = numFormation;
	}
	
	public int getSignalement() {
		return signalement;
	}

	public void setSignalement(int signalement) {
		this.signalement = signalement;
	}

	public boolean isInRootGroup(){
        return this.role.equals("root");
    }
    
    public boolean isInAdminGroup(){
        return (this.role.equals("admin"))||(this.role.equals("root"));
    }

    public boolean isInUserGroup(){
        return (this.role.equals("user"))||(this.role.equals("animateur"));
    }

	public boolean isInAnimateurGroup() {
		return this.role.equals("animateur");
	}
    
    
    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        Hasher hasher = Hashing.md5().newHasher();
        hasher.putLong(random.nextLong());
        return hasher.hash().toString();
    }
    
    public String generatePass(){
    	String pass = "";
    	String letters = "abcdefghijklmnopqrstuvwxyz";
    	Random r = new Random();
    	for(int i = 0; i < 8; i++){
    		int type = r.nextInt(3);
    		if(type == 0){
    			pass += r.nextInt(10);
    		}
    		else if (type == 1){
    			pass += letters.charAt(r.nextInt(letters.length()));
    		}
    		else{
    			pass += Character.toUpperCase(letters.charAt(r.nextInt(letters.length())));
    		}
    	}
    	return pass;
    }

    public void resetPasswordHash() {
        if (mot_de_passe != null && ! mot_de_passe.isEmpty()) {
            setMot_de_passe(buildHash(mot_de_passe, getSalt()));
        }
    }




}
