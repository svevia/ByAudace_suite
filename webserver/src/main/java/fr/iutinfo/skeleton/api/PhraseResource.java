package fr.iutinfo.skeleton.api;

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

public class PhraseResource {

    private static PhraseDao dao = BDDFactory.getDbi().open(PhraseDao.class);
    final static Logger logger = LoggerFactory.getLogger(PhraseResource.class);

    @POST
    public Phrase createPhrase(Phrase phrase) {
        dao.insert(phrase);
        return phrase;
    }

    @GET
    @Path("/{phrase}")
    @RolesAllowed({"admin","user"})
    public Phrase getPhrase(@PathParam("phrase") String phrase) {
        Phrase ph = dao.findByPhrase(phrase);
        if (ph == null) {
            throw new WebApplicationException(404);
        }
        return ph;
    }

    @DELETE
    @Path("/{phrase}")
    public Phrase deleteUser(@PathParam("phrase") String phrase) {
        Phrase ph = dao.findByPhrase(phrase);
        System.out.println("Deleting phrase : " + phrase);
        if (ph == null) {
            throw new WebApplicationException(404);
        }
        dao.delete(phrase);
        return ph;
    }

    @GET
    @Path("/pourcentage")
    public String getPourcentage() {
        int a = dao.getTermCount(false);
        int b = dao.getAllCount();
        return "{ \"percent\":" + ((double) a/b) * 100 + " }";
    }
    
    @GET
    @Path("/search")
    public Phrase search(@QueryParam("search") String search) {
        return dao.search("%" + search + "%");
    }

    /*@GET
    @Path("/order")
    public List<Phrase> getAllOrderBy(@QueryParam("champ") String champ) {
        List<Phrase> res = dao.allOrderBy(champ);
        System.out.println("Recherche triée par " + champ);
        for (Phrase p : res)
            System.out.println(p);
        return res;
    }*/

    @GET
    @Path("/orderbesoin")
    @RolesAllowed({"admin","user"})
    public List<Phrase> getOrderBesoin() {
        return dao.orderBesoin();
    }

    @GET
    @Path("/orderconsultee")
    @RolesAllowed({"admin","user"})
    public List<Phrase> getOrderConsultee() {
        return dao.orderConsultee();
    }

    @GET
    @Path("/orderphrase")
    @RolesAllowed({"admin","user"})
    public List<Phrase> getOrderPhrase() {
        return dao.orderPhrase();
    }

    @GET
    @Path("/ordermail")
    @RolesAllowed({"admin","user"})
    public List<Phrase> getOrderMail() {
        return dao.orderMail();
    }

    @GET
    @Path("/orderterminee")
    @RolesAllowed({"admin","user"})
    public List<Phrase> getOrderTerminee() {
        return dao.orderTerminee();
    }

    @GET
    @RolesAllowed({"admin","user"})
    public List<Phrase> getAllPhrase() {
        return dao.all();
    }
}
