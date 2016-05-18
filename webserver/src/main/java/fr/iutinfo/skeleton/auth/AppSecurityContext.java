package fr.iutinfo.skeleton.auth;

import fr.iutinfo.skeleton.api.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class AppSecurityContext implements SecurityContext {

    final static Logger logger = LoggerFactory.getLogger(AppSecurityContext.class);
    private User user;
    private String scheme;

    public AppSecurityContext(User user, String scheme) {
        this.user = user;
        this.scheme = scheme;
    }

    @Override
    public Principal getUserPrincipal() {//retourne l'user loggé
        return this.user;
    }

    /**
     * Verifie le rôle d'un tulisateur
     * @param s
     */
    @Override
    public boolean isUserInRole(String s) { 
        logger.debug("isUserInRole called for : " + s);
        if ("root".equals(s)) {
            return user.isInRootGroup();
        }
        if ("admin".equals(s) || "root".equals(s)) {//donne les droits d'admin au root aussi
            return user.isInAdminGroup();
        }
        else if("user".equals(s)) {
            return user.isInUserGroup();
        }
        return false;
    }

    @Override
    public boolean isSecure() {
        return "https".equals(this.scheme);
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}
