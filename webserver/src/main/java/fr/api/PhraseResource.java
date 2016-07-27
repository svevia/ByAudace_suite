package fr.api;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/phrase")
@RolesAllowed({"admin"})
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

/**
 * Requêtes REST liés à la table phrase_metier de la base de données
 *
 */
public class PhraseResource {

    private static PhraseDao dao = BDDFactory.getDbi().open(PhraseDao.class);
    final static Logger logger = LoggerFactory.getLogger(PhraseResource.class);

    /**
     * Créé une phrase et l'ajoute dans la base de données 
     * Exemple : curl "localhost:8080/v1/phrase" -X POST -d '{"mail":"toto@gmail.com", ...}'
     *
     * @param phrase - Phrase à ajouter
     * @return phrase - Phrase créée
     */
    @POST
    @RolesAllowed({"admin","user"})
    public Phrase createPhrase(Phrase phrase) {
        dao.insert(phrase);
        logger.trace("nouvelle phrase -- id = " + phrase.getId());
        return phrase;
    }
    
    @POST
    @Path("/config")
    @RolesAllowed("admin")
    public void setConfig(Gestion g){
    	g.save();
    }
    
    @GET
    @Path("/config")
    @RolesAllowed("admin")
    public Gestion getConfig(){
    	Gestion g = new Gestion();
    	g.init();
    	return g;
    }

    @POST
    @Path("/help")
    @RolesAllowed({"admin","user"})
    public Aide aiderPhrase(Aide aide) {
        dao.help(aide);
        logger.trace("aide reçu sur la phrase -- id = " + aide.getPhrase());
        return aide;
    }

    /**
     * Recupère l'objet Phrase associé à l'élément id
     * 
     * @param id - Attribut phrase recherchée
     * @return phrase - Objet Phrase trouvée
     */
    @GET
    @Path("/{id}")
    @RolesAllowed({"admin","user"})
    public Phrase getPhrase(@PathParam("id") int id) {
        Phrase ph = dao.findById(id);
        if (ph == null) {
            throw new WebApplicationException(404);
        }
        return ph;
    }
    
    
    @GET
    @Path("/signalement/{id}")
    @RolesAllowed({"admin","user"})
    public Phrase signalPhrase(@PathParam("id") int id) {
        dao.signal(id);
        logger.trace("signalement reçu sur la phrase -- id = " + id);
        return dao.findById(id);
    }

    /**
     * Supprime l'objet Phrase associé à l'élément id et la retourne
     * 
     * @param id - Attribut phrase recherchée
     * @return phrase - Objet Phrase supprimée
     */
    @DELETE
    @Path("/{id}")
    public Phrase deletePhrase(@PathParam("id") int id) {
        Phrase ph = dao.findById(id);
        System.out.println("Deleting phrase : " + id);
        if (ph == null) {
            throw new WebApplicationException(404);
        }
        dao.delete(id);
        logger.trace("Suppression de la phrase -- id = " + id);
        return ph;
    }

    /**
     * Recupere le pourcentage de phrase terminées
     * 
     * @return pourcentage
     */
    @GET
    @Path("/pourcentage")
    public String getPourcentage() {
        int a = dao.getTermCount(false);
        int b = dao.getAllCount();
        return "{ \"percent\":" + ((double) a/b) * 100 + " }";
    }
    
    /**
     * Recherche une phrase à l'aide des champs phrase, besoin ou mail
     * 
     * @param search
     * @return phrase
     */
    @GET
    @Path("/search")
    public List<Phrase> search(@QueryParam("search") String search) {
    	if(search == ""){
    		return dao.orderDate();
    	}
    	else{
    		return dao.search("%" + search + "%");
    	}
    }

    /**
     * Recupere tout les elements dans la table ordonnés avec le champ besoin
     * 
     * @return phrases - Liste des phrases
     */
    @GET
    @Path("/orderbesoin")
    public List<Phrase> getOrderBesoin() {
        return dao.orderBesoin();
    }

    /**
     * Recupere tout les elements dans la table ordonnés avec le champ besoin
     * 
     * @return phrases - Liste des phrases
     */
    @GET
    @Path("/orderconsultee")
    public List<Phrase> getOrderConsultee() {
        return dao.orderConsultee();
    }

    /**
     * Recupere tout les elements dans la table ordonnés avec le champ phrase
     * 
     * @return phrases - Liste des phrases
     */
    @GET
    @Path("/orderphrase")
    public List<Phrase> getOrderPhrase() {
        return dao.orderPhrase();
    }

    /**
     * Recupere tout les elements dans la table ordonnés avec le champ mail
     * 
     * @return phrases - Liste des phrases
     */
    @GET
    @Path("/ordermail")
    public List<Phrase> getOrderMail() {
        return dao.orderMail();
    }

    /**
     * Recupere tout les elements dans la table ordonnés avec le champ mail
     * 
     * @return phrases - Liste des phrases
     */
    @GET
    @Path("/orderdate")
    public List<Phrase> getOrderDate() {
        return dao.orderDate();
    }
    
    /**
     * Recupere tout les elements dans la table ordonnés avec le champ mail
     * 
     * @return phrases - Liste des phrases
     */
    @GET
    @Path("/ordersignal")
    public List<Phrase> getOrderSignalement() {
        return dao.orderSignalement();
    }

    @GET
    @Path("/nbr")
    @Produces(MediaType.TEXT_PLAIN)
    public Integer getNbrUser(){
    	return dao.getAllCount();
    }
    
    @GET
    @Path("/finit")
    @Produces(MediaType.TEXT_PLAIN)
    public Integer getFinish(){
    	return dao.getTermCount(true);
    }
    
    @GET
    @Path("/activite")
    @Produces(MediaType.TEXT_PLAIN)
    public int getActivite(){
    	return dao.phrasePoste();
    }
    
    
    
    /**
     * Recupere tout les elements dans la table
     * 
     * @return phrases - Liste des phrases
     */
    @GET
    @RolesAllowed({"admin","user"})
    public List<Phrase> getAllPhrase() {
    	Gestion g = new Gestion();
    	g.init();
        return dao.allAppli(g);
    }
}
