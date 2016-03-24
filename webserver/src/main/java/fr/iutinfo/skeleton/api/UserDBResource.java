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
    public Response createUser(@FormParam("mail") String mail, @FormParam("nom") String nom, @FormParam("prenom") String prenom, 
            @FormParam("digit") String digit, @FormParam("mot_de_passe") String mot_de_passe, @FormParam("role") String role) {
        System.out.println("Create user : mail=" + mail + ", nom=" + nom);
        User user = new User(mail, nom, prenom, digit, mot_de_passe, role);
        user.resetPasswordHash();
        //dao.insert(user);
        dao.insert(mail, nom, prenom, digit, mot_de_passe, role);
        return Response.status(200).entity("Created user : " + user.getMail()).build();
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
    
    /*@GET
    public void getAll() {
        List<User> lu = dao.all();
        for (User u : lu)
            System.out.println("mail:" + u.getEmail() + " nom:" + u.getPrenom());
    }*/
}
