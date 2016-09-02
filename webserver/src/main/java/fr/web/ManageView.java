package fr.web;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

import org.glassfish.jersey.server.mvc.Template;

import fr.api.BDDFactory;
import fr.api.User;
import fr.api.UserDao;

@Path("/manage")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
@RolesAllowed("admin")
public class ManageView {
	
	private static UserDao dao = BDDFactory.getDbi().open(UserDao.class);
	
	public class Returner{
    	List<String> list;
    	User user;
    	public Returner(List<String> list, User user){
    		this.user = user;
    		this.list = list;
    	}
		public List<String> getList() {
			return list;
		}
		public void setList(List<String> list) {
			this.list = list;
		}
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
    }
   
    
    @GET
    @Template
    @RolesAllowed({"admin","animateur"})
    public Returner display(@Context SecurityContext context) {
        User user = (User) context.getUserPrincipal();
        return new Returner(dao.allCat(), user);
    }
    
}