package fr.iutinfo.skeleton.api;

import java.security.Principal;
import java.security.SecureRandom;

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
    private String salt;


    public User(String mail, String nom, String prenom, String digit, String mot_de_passe, String role, String numero) {
        this.name = nom;
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

    public String getNom() {
        return name;
    }

    public void setNom(String nom) {
        this.name = nom;
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
        return getNom();
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
    	logger.trace("pass : " + pass);
    	logger.trace("pass User : " + getMot_de_passe());
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

    public boolean isInRootGroup(){
        return this.role.equals("root");
    }
    
    public boolean isInAdminGroup(){
        return (this.role.equals("admin"))||(this.role.equals("root"));
    }

    public boolean isInUserGroup(){
        return this.role.equals("user");
    }


    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        Hasher hasher = Hashing.md5().newHasher();
        hasher.putLong(random.nextLong());
        return hasher.hash().toString();
    }

    public void resetPasswordHash() {
    	System.out.println(mot_de_passe);
        if (mot_de_passe != null && ! mot_de_passe.isEmpty()) {
            setMot_de_passe(buildHash(mot_de_passe, getSalt()));
        }
    }


}
