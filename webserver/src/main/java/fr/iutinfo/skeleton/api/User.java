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

    /* Obligatoire */
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String passwdHash;
    private String salt;
    
    /* Non obligatoire */
    private String phraseMetier;
    private String role;

    public User(String nom, String prenom, String email, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        setPassword(password);
        this.role = "user";
    }
    
    public User(String nom, String prenom, String email, String password, String phraseMetier) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        setPassword(password);
        this.phraseMetier = phraseMetier;
        this.role = "user";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return nom;
    }

    public void setName(String nom) {
        this.nom = nom;
    }


    public void setPassword(String password) {
        passwdHash = buildHash(password, getSalt());
        this.password = password;
    }

    public String getPassword () {
        return this.password;
    }

    private String buildHash(String password, String s) {
        Hasher hasher = Hashing.md5().newHasher();
        hasher.putString(password + s, Charsets.UTF_8);
        return hasher.hash().toString();
    }

    public boolean isGoodPassword(String password) {
        String hash = buildHash(password, getSalt());
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
        return nom.equals(user.nom) && prenom.equals(user.prenom) && email.equals(user.email) && passwdHash.equals(user.getPasswdHash()) && salt.equals((user.getSalt()));
    }

    @Override
    public String toString() {
        return nom + ", " + prenom + " <" + email + ">";
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
        if (password != null && ! password.isEmpty()) {
            setPassword(getPassword());
        }
    }
}
