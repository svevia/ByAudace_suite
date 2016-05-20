package fr.iutinfo.skeleton.web;

import fr.iutinfo.skeleton.api.*;

import org.glassfish.jersey.server.mvc.Template;

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
    	String name;
    	public Returner(List<Phrase> phrases, String name){
    		this.phrases = phrases;
    		this.name = name;
    	}
		public List<Phrase> getPhrases() {
			return phrases;
		}
		public void setPhrases(List<Phrase> phrases) {
			this.phrases = phrases;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
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
        String name = context.getUserPrincipal().getName();
        return new Returner(dao.all(), name);
    }

    /**
     * class interne serveant à coupler une phrase et le nom de l'utilisateur loggé
     * afin de l'afficher dans la navbar
     */
    public class ReturnerPhrase{
    	Phrase phrases;
    	String connect;
    	String mail;
    	
    	public ReturnerPhrase(Phrase phrases, String connect){
    		
    		UserDao userDao = BDDFactory.getDbi().open(UserDao.class);
    		this.phrases = phrases;
    		this.connect = connect;
    		this.mail = userDao.findById(phrases.getId_user()).getMail();
    	}
		public Phrase getPhrases() {
			return phrases;
		}
		public void setPhrases(Phrase phrases) {
			this.phrases = phrases;
		}
		public String getConnect() {
			return connect;
		}
		public void setConnect(String connect) {
			this.connect = connect;
		}
		public String getMail() {
			return mail;
		}
		public void setMail(String mail) {
			this.mail = mail;
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
        String connect = context.getUserPrincipal().getName();
        Phrase phrases = dao.findById(id);
        if (phrases == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        else{
        	return new ReturnerPhrase(phrases, connect);
        }
    }
}
