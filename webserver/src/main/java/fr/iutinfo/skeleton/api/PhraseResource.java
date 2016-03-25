package fr.iutinfo.skeleton.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/phrase")
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
	public List<Phrase> getAllPhrase() {
		return dao.all();
	}
}
