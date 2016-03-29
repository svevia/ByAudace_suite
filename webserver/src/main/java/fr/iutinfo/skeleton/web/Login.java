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
       
        
        //logger.debug("User - current : " + currentUser.toString() + ", old : " + oldUser);
        if (oldUser == null && currentUser != null) {
        	setCookieAndRedirectToUserDetail(currentUser);
        } 
        if ( currentUser == null || currentUser.getMail().equals(oldUser.getMail())) {
            requestLoginForm();
        } else {
            setCookieAndRedirectToUserDetail(currentUser);
        }
        return null;
    }
    
    private void requestLoginForm() {
        throw new WebApplicationException(Response
                .status(Response.Status.UNAUTHORIZED)
                .header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Mon application\"")
                .entity("Ressouce requires login.").build());
    }

    private void setCookieAndRedirectToUserDetail(User currentUser) throws URISyntaxException {
        URI location = new URI("/html/userdb");
        throw new WebApplicationException(Response
                .temporaryRedirect(location)
                .cookie(new NewCookie("user", currentUser.getMail()))
                .build()
        );
    }
}
