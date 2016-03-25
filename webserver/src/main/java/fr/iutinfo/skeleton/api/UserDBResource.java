package fr.iutinfo.skeleton.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import javax.ws.rs.core.Response;

@Path("/userdb")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserDBResource {

    private static UserDao dao = BDDFactory.getDbi().open(UserDao.class);
    final static Logger logger = LoggerFactory.getLogger(UserDBResource.class);

    @POST
    public User createUser(User user) {
        System.out.println(user);
        System.out.println(user.getMail());
        user.resetPasswordHash();
        dao.insert(user);
        return user;
    }

    @GET
    @Path("/{mail}")
    public User getUser(@PathParam("mail") String mail) {
        User user = dao.findByMail(mail);
        if (user == null) {
            throw new WebApplicationException(404);
        }
        return user;
    }

    @GET
    public List<User> getAllUsers() {
        return dao.all();
    }
}
