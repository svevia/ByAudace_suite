package fr.web;


import org.eclipse.persistence.annotations.RangePartition;
import org.glassfish.jersey.server.mvc.Template;

import fr.api.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import java.util.List;

/**
 * page listant les utilisateurs
 * @author asvevi
 *
 */
@Path("/userdb")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
public class UserViews {
    private static UserDao dao = BDDFactory.getDbi().open(UserDao.class);
    
    /**
     * Class interne contenant une liste de user et le nom de l'user actuellement connecté pour la navbar
     */
    public class Returner{
    	List<User> list;
    	User user;
    	public Returner(List<User> list, User user){
    		this.user = user;
    		this.list = list;
    	}
		public List<User> getList() {
			return list;
		}
		public void setList(List<User> list) {
			this.list = list;
		}
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
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
        User name = (User) context.getUserPrincipal();
        return new Returner(dao.all(), name);
    }

    /**
     * Class interne regroupant un User et le user connecté
     * @author svevia
     */
    public class ReturnerUser{
    	User userDetail;
    	User user;
    	List<String> list;
    	public ReturnerUser(User userDetail, User user, List<String> list){
    		this.user = user;
    		this.userDetail = userDetail;
    		this.list = list;
    	}
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
		public User getUserDetail() {
			return userDetail;
		}
		public void setUserDetail(User userDetail) {
			this.userDetail = userDetail;
		}
		public List<String> getList() {
			return list;
		}
		public void setList(List<String> list) {
			this.list = list;
		}

    }
    
    /**
     * detail d'un user
     * @param mail
     * @param context
     * @return
     */
    @GET
    @Template(name = "edit")
    @Path("/{id}")
    @RolesAllowed("admin")
    public ReturnerUser getDetail(@PathParam("id") int id,@Context SecurityContext context) {
    	User name = (User) context.getUserPrincipal();
        User user = dao.findById(id);
        if (user == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return new ReturnerUser(user, name,dao.allCat());
    }

}
