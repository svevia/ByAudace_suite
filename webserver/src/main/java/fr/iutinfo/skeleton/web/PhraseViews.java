package fr.iutinfo.skeleton.web;

import fr.iutinfo.skeleton.api.BDDFactory;
import fr.iutinfo.skeleton.api.*;
import org.glassfish.jersey.server.mvc.Template;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/phrase")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
@RolesAllowed("admin")
public class PhraseViews {
    private static PhraseDao dao = BDDFactory.getDbi().open(PhraseDao.class);

    @GET
    @Template
    public List<Phrase> getAllPhrase() {
        return dao.all();
    }

    @GET
    @Template(name = "detail")
    @Path("/{name}")
    public Phrase getPhrase(@PathParam("name") String name) {
        Phrase ph = dao.findByPhrase(name);
        if (ph == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return ph;
    }
}
