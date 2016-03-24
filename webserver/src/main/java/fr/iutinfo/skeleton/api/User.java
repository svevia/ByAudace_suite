package fr.iutinfo.skeleton.api;

import com.google.common.base.Charsets;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;
import java.security.SecureRandom;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class User implements Principal {
    final static Logger logger = LoggerFactory.getLogger(User.class);

    private String name;
    private String prenom;
    private String mail;
    private String mot_de_passe;
    private String passwdHash;
    private String salt;
    private String digit;
    private String role;

    public User(String mail, String nom, String prenom, String digit, String mot_de_passe, String role) {
        this.name = nom;
        this.prenom = prenom;
        this.mail = mail;
        setMot_de_passe(mot_de_passe);
        this.digit = digit;
        this.role = role;
    }

    public User(){}

    /* Getters Setters */

    public String getMail() {
        return mail;
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

    public void setMot_de_passe(String mot_de_passe) {
        passwdHash = buildHash(mot_de_passe, getSalt());
        this.mot_de_passe = mot_de_passe;
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

    /* Other methods */

    private String buildHash(String mot_de_passe, String s) {
        Hasher hasher = Hashing.md5().newHasher();
        hasher.putString(mot_de_passe + s, Charsets.UTF_8);
        return hasher.hash().toString();
    }

    public boolean isGoodPassword(String mot_de_passe) {
        return true;
        //String hash = buildHash(mot_de_passe, getSalt());
        //return hash.equals(getPasswdHash());
    }

    public String getPasswdHash() {
        return passwdHash;
    }

    public void setPasswdHash(String passwdHash) {
        this.passwdHash = passwdHash;
    }

    @Override
    public boolean equals(Object arg) {
        if (getClass() != arg.getClass())
            return false;
        User user = (User) arg;
        return name.equals(user.name) && prenom.equals(user.prenom) && mail.equals(user.mail) && passwdHash.equals(user.getPasswdHash()) && salt.equals((user.getSalt()));
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

    public boolean isInAdminGroup(){
        return this.role.equals("admin");
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
        if (mot_de_passe != null && ! mot_de_passe.isEmpty()) {
            setMot_de_passe(getMot_de_passe());
        }
    }


}
