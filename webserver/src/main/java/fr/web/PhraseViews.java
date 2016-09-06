package fr.web;

import org.glassfish.jersey.server.mvc.Template;

import fr.api.*;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import java.util.List;

@Path("/phrase")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
@RolesAllowed("admin")
public class PhraseViews {
    private static PhraseDao dao = BDDFactory.getDbi().open(PhraseDao.class);

    /**
     * class interne serveant à coupler une List de phrase et le nom de l'utilisateur loggé
     * afin de l'afficher dans la navbar
     */
    public class Returner{
    						
    	List<Phrase> phrases;
    	User user;
    	public Returner(List<Phrase> phrases, User name){
    		this.phrases = phrases;
    		this.user = name;
    	}
		public List<Phrase> getPhrases() {
			return phrases;
		}
		public void setPhrases(List<Phrase> phrases) {
			this.phrases = phrases;
		}
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}

    }
    
    /**
     * Affiche la liste de toutes les phrases
     * @param context
     * @return
     */
    @GET
    @Template
    public Returner getAllPhrase(@Context SecurityContext context) {
        User name = (User) context.getUserPrincipal();
        return new Returner(dao.orderDate(), name);
    }

    /**
     * class interne serveant à coupler une phrase et le nom de l'utilisateur loggé
     * afin de l'afficher dans la navbar
     */
    public class ReturnerPhrase{
    	Phrase phrases;
    	User user;
		User createur;
    	
    	public ReturnerPhrase(Phrase phrases, User user){
    		
    		UserDao userDao = BDDFactory.getDbi().open(UserDao.class);
    		this.phrases = phrases;
    		this.user = user;
    		this.createur = userDao.findById(phrases.getId_user());
    	}
		public Phrase getPhrases() {
			return phrases;
		}
		public void setPhrases(Phrase phrases) {
			this.phrases = phrases;
		}
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
		public User getCreateur() {
			return createur;
		}
		public void setCreateur(User createur) {
			this.createur = createur;
		}
		
    }
    
    
    /**
     * Affiche le detail d'une phrase
     * @param name
     * @param context
     * @return
     */
    @GET
    @Template(name = "detail")
    @Path("/{id}")
    public ReturnerPhrase getPhrase(@PathParam("id") int id,@Context SecurityContext context) {
        User user = (User) context.getUserPrincipal();
        Phrase phrases = dao.findById(id);
        if (phrases == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        else{
        	return new ReturnerPhrase(phrases, user);
        }
    }
}
