package fr.iutinfo.skeleton.api;

import com.google.common.base.Charsets;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;
import java.security.SecureRandom;

public class User implements Principal {
    final static Logger logger = LoggerFactory.getLogger(User.class);

    private String nom;
    private String prenom;
    private String mail;
    private String mot_de_passe;
    private String passwdHash;
    private String salt;
    private String digit;
    private String role;

    public User(String mail, String nom, String prenom, String digit, String mot_de_passe, String role) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        setPassword(mot_de_passe);
        this.digit = digit;
        this.role = role;
    }

    public String getEmail() {
        return mail;
    }

    public void setEmail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return nom;
    }

    public void setName(String nom) {
        this.nom = nom;
    }


    public void setPassword(String mot_de_passe) {
        passwdHash = buildHash(mot_de_passe, getSalt());
        this.mot_de_passe = mot_de_passe;
    }

    public String getPassword () {
        return this.mot_de_passe;
    }

    private String buildHash(String mot_de_passe, String s) {
        Hasher hasher = Hashing.md5().newHasher();
        hasher.putString(mot_de_passe + s, Charsets.UTF_8);
        return hasher.hash().toString();
    }

    public boolean isGoodPassword(String mot_de_passe) {
        String hash = buildHash(mot_de_passe, getSalt());
        return hash.equals(getPasswdHash());
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
        return nom.equals(user.nom) && prenom.equals(user.prenom) && mail.equals(user.mail) && passwdHash.equals(user.getPasswdHash()) && salt.equals((user.getSalt()));
    }

    @Override
    public String toString() {
        return nom + ", " + prenom + " <" + mail + ">";
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
    
    public boolean isInUserGroup(){
        return this.role == "user";
    }

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        Hasher hasher = Hashing.md5().newHasher();
        hasher.putLong(random.nextLong());
        return hasher.hash().toString();
    }

    public void resetPasswordHash() {
        if (mot_de_passe != null && ! mot_de_passe.isEmpty()) {
            setPassword(getPassword());
        }
    }
}
