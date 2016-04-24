package fr.iutinfo.skeleton.api;

import java.util.Calendar;
import java.util.List;

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
        return phrase;
    }

    @POST
    @Path("/help")
    @RolesAllowed({"admin","user"})
    public void aiderPhrase(Aide aide) {
        dao.help(aide.phrase.getPhrase(),aide.user.getMail(),new java.sql.Date(Calendar.getInstance().getTime().getTime()));
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
        return dao.search("%" + search + "%");
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
     * Recupere tout les elements dans la table ordonnés avec le champ terminee
     * 
     * @return phrases - Liste des phrases
     */
    @GET
    @Path("/orderterminee")
    public List<Phrase> getOrderTerminee() {
        return dao.orderTerminee();
    }

    /**
     * Recupere tout les elements dans la table
     * 
     * @return phrases - Liste des phrases
     */
    @GET
    @RolesAllowed({"admin","user"})
    public List<Phrase> getAllPhrase() {
        return dao.all();
    }
}
