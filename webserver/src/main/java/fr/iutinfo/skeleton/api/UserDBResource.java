package fr.iutinfo.skeleton.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/userdb")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserDBResource {

    private static UserDao dao = BDDFactory.getDbi().open(UserDao.class);
    final static Logger logger = LoggerFactory.getLogger(UserDBResource.class);

    @POST
    public User createUser(User user) {
        System.out.println("Create user : " + user);
        user.resetPasswordHash();
        dao.insert(user);
        return user;
    }

    @GET
    @Path("/{mail}")
    public Response getUser(@PathParam("mail") String mail) {
        User user = dao.findByMail(mail);
        if (user == null) {
            return Response.accepted().status(404).build();
        }
        return Response.accepted().status(202).entity(user).build();
    }

    @DELETE
    @Path("/{mail}")
    public Response deleteUser(@PathParam("mail") String mail) {
        User user = dao.findByMail(mail);
        System.out.println("Deleting user : " + user);
        if (user == null) {
            return Response.accepted().status(404).build();
        }
        dao.delete(mail);
        return Response.accepted().status(202).entity(user).build();
    }
    
    @PUT
    @Path("/{mail}")
    public Response updateUser(@PathParam("mail") String mail, User newparam) {
    	User user = dao.findByMail(mail);
    	newparam.setMail(user.getMail());
    	System.out.println("Updating user : " + user);
    	if (user.equals(newparam)) {
    		System.err.println("L'update n'a provoque aucune modification. La/les modifications sont surement identique a l'original");
    		return Response.accepted().status(418).build();
    	}
    	if (user == null) {
    		return Response.accepted().status(404).build();
    	}
    	dao.update(mail, newparam);
    	return Response.accepted().status(202).entity(user).build();
    }

    @GET
    public List<User> getAllUsers() {
        return dao.all();
    }
}
