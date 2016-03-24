package fr.iutinfo.skeleton.api;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    User createUser(String nom, String prenom, String email, String password, String digit, String role) {
        User user = new User(nom, prenom, email, password, digit, role);
        return doPost(user);
    }

    User doPost(User user) {
        Entity<User> userEntity = Entity.entity(user, MediaType.APPLICATION_JSON);
        return target.request().post(userEntity).readEntity(User.class);
    }

}
