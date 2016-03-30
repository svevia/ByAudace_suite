package fr.iutinfo.skeleton.web;


import fr.iutinfo.skeleton.api.BDDFactory;
import fr.iutinfo.skeleton.api.*;

import org.eclipse.persistence.annotations.RangePartition;
import org.glassfish.jersey.server.mvc.Template;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import java.util.List;

@Path("/userdb")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
public class UserViews {
    private static UserDao dao = BDDFactory.getDbi().open(UserDao.class);
    
    /**
     * Class interne contenant une liste de user et le nom de l'user actuellement connecté pour la navbar
     */
    public class Returner{
    	List<User> user;
    	String name;
    	public Returner(List<User> user, String name){
    		this.user = user;
    		this.name = name;
    	}
		public List<User> getUser() {
			return user;
		}
		public void setUser(List<User> user) {
			this.user = user;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
    }
    
    /**
     * retourne la liste des utilisateurs
     * @param context
     * @return
     */
    @GET
    @Template
    @RolesAllowed("admin")
    public Returner getAll(@Context SecurityContext context) {
        String name = context.getUserPrincipal().getName();
        return new Returner(dao.all(), name);
    }

    /**
     * Class interne regroupant un User et le user connecté
     * @author svevia
     */
    public class ReturnerUser{
    	User user;
    	String name;
    	public ReturnerUser(User user, String name){
    		this.user = user;
    		this.name = name;
    	}
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
    }
    
    /**
     * detail d'un user
     * @param mail
     * @param context
     * @return
     */
    @GET
    @Template(name = "detail")
    @Path("/{mail}")
    @RolesAllowed("admin")
    public ReturnerUser getDetail(@PathParam("mail") String mail,@Context SecurityContext context) {
    	String name = context.getUserPrincipal().getName();
        User user = dao.findByMail(mail);
        if (user == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return new ReturnerUser(user, name);
    }

}
