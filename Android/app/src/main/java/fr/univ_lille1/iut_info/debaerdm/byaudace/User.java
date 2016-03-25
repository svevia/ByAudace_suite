package fr.univ_lille1.iut_info.debaerdm.byaudace;

/**
 * Created by debaerdm on 25/03/16.
 */
public class User {
    private String mail;
    private String passWord;

    public User(String mail, String passWord){
        this.mail = mail;
        this.passWord = passWord;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "Mail : "+this.mail;
    }
}
