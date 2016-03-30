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
    	public ReturnerPhrase(Phrase phrases, String connect){
    		this.phrases = phrases;
    		this.connect = connect;
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
    }
    
    
    /**
     * Affiche le detail d'une phrase
     * @param name
     * @param context
     * @return
     */
    @GET
    @Template(name = "detail")
    @Path("/{name}")
    public ReturnerPhrase getPhrase(@PathParam("name") String name,@Context SecurityContext context) {
        String connect = context.getUserPrincipal().getName();
        Phrase phrases = dao.findByPhrase(name);
        if (phrases == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return new ReturnerPhrase(phrases, connect);
    }
}
