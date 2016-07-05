package fr.iutinfo.skeleton.api;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.api.BDDFactory;
import fr.api.User;
import fr.api.UserDao;

public class Helper {
    final static Logger logger = LoggerFactory.getLogger(Helper.class);
    private static UserDao dao;
    public WebTarget target;

    
    public Helper(WebTarget target) {
        this.target = target;
        dao = BDDFactory.getDbi().open(UserDao.class);
    }

    void initDb() {
        dao.dropUserTable();
        dao.createUserTable();
    }
    


    User createUser(String mail, String nom, String prenom, String digit, String mot_de_passe, String role, String numero) {
        User user = new User(mail, nom, prenom, digit, mot_de_passe, role, numero);
        return doPost(user);
    }
    
    User createUserWithEmail(String email) {
        User user = new User(email, "", "", "", "", "","");
        return doPost(user);
    }

    User doPost(User user) {
        Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);
        return target.request().post(userEntity).readEntity(User.class);
    }

}
