package fr.iutinfo.skeleton.web;


import fr.iutinfo.skeleton.api.BDDFactory;
import fr.iutinfo.skeleton.api.*;
import org.glassfish.jersey.server.mvc.Template;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/userdb")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
public class UserViews {
    private static UserDao dao = BDDFactory.getDbi().open(UserDao.class);

    @GET
    @Template
    public List<User> getAll() {
        return dao.all();
    }

    @GET
    @Template(name = "detail")
    @Path("/{mail}")
    public User getDetail(@PathParam("mail") String mail) {
        User user = dao.findByMail(mail);
        if (user == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return user;
    }

}
