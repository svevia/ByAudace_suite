package fr.iutinfo.skeleton.auth;

import fr.iutinfo.skeleton.api.BDDFactory;
import fr.iutinfo.skeleton.api.User;
import fr.iutinfo.skeleton.api.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import javax.servlet.http.*;

@Provider
@PreMatching
public class AuthFilter implements ContainerRequestFilter {
    final static Logger logger = LoggerFactory.getLogger(AuthFilter.class);
    @Context private HttpServletRequest request;
    @Override
    /**
     * Verifie si un utilisateur peu se logger, il récupère les infos saisies, les decodes et verifie si elles sont corrects
     */
    public void filter(ContainerRequestContext containerRequest) throws WebApplicationException {

        String auth = containerRequest.getHeaderString(HttpHeaders.AUTHORIZATION);
        String scheme = containerRequest.getUriInfo().getRequestUri().getScheme();
        logger.info("auth : " + auth);

        if (auth != null) {
            String[] loginPassword = BasicAuth.decode(auth);//décode la base 64
            logger.debug("login : " + loginPassword[0]);
            logger.debug("password : " + loginPassword[1]);
            
            if (loginPassword == null || loginPassword.length != 2) {
                throw new WebApplicationException(Status.NOT_ACCEPTABLE);
            }
            UserDao dao = BDDFactory.getDbi().open(UserDao.class);
            User user = dao.findByMail(loginPassword[0]);
            if(user != null){
            	String passwd = user.buildHash(loginPassword[1], user.getSalt()); // crypte le mot de passe entré avec le salt associé au user
	            if((!user.isGoodPassword(passwd) && !user.isGoodPassword(loginPassword[1])) || user == null) {//verifie l'egalité des 2 mot de passes
	                throw new WebApplicationException(Status.UNAUTHORIZED);//renvoi une erreur 401
	            }
            }
            containerRequest.setSecurityContext(new AppSecurityContext(user, scheme));
        }
    }
}
