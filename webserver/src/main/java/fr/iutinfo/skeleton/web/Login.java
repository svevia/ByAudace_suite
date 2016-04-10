package fr.iutinfo.skeleton.web;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.iutinfo.skeleton.api.BDDFactory;
import fr.iutinfo.skeleton.api.User;
import fr.iutinfo.skeleton.api.UserDao;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Login{
    final static Logger logger = LoggerFactory.getLogger(Login.class);

	UserDao dao = BDDFactory.getDbi().open(UserDao.class);
    @GET
    public User login(@Context SecurityContext context, @QueryParam("user") String oldLogin) throws URISyntaxException {
    	User currentUser = (User) context.getUserPrincipal();
        User oldUser = dao.findByMail(oldLogin);
       
        
        if (oldUser == null && currentUser != null) {//si l'utilisateur est déjà loggé
        	setCookieAndRedirectToUserDetail(currentUser);//on redirige vers la page principale
        } 
        if ( currentUser == null || currentUser.getMail().equals(oldUser.getMail())) {//Si la personne n'est pas ou plus loggé
            requestLoginForm();//on demande le login
        } else {
            setCookieAndRedirectToUserDetail(currentUser);//stock les infos de la personne dans un cookie
        }
        return null;
    }
    
    private void requestLoginForm() { //affiche le formulaire de connexion du navigateur
        throw new WebApplicationException(Response
                .status(Response.Status.UNAUTHORIZED)
                .header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Mon application\"")
                .entity("Ressouce requires login.").build());
    }

    private void setCookieAndRedirectToUserDetail(User currentUser) throws URISyntaxException {
        URI location = new URI("/html/userdb");//redirige vers la liste des utilisateurs après login
        throw new WebApplicationException(Response
                .temporaryRedirect(location)
                .cookie(new NewCookie("user", currentUser.getMail()+":"+currentUser.getMot_de_passe()))
                .build()
        );
    }
}
