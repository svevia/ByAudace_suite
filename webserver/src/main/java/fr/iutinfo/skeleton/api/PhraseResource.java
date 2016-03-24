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


  /*  public PhraseResource() {
		try {
			dao.createPhraseTable();
		} catch (Exception e) {
			System.out.println("Table déjà là !");
		}
	}*/
	
	@POST
	public Phrase createPhrase(String name) {
        Phrase ph = dao.insert(name);
        ph.setPhrase(name);
		return ph;
	}

	@GET
	@Path("/{name}")
	public Phrase getPhrase(@PathParam("name") String name) {
		Phrase ph = dao.findByName(name);
		if (ph == null) {
			throw new WebApplicationException(404);
		}
		return ph;
	}

	@GET
	public List<Phrase> getAllPhrase() {
		return dao.all();
	}

}
