package fr.iutinfo.skeleton.api;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Requêtes REST liés à la table utilisateurs de la base de données
 *
 * @author seysn
 * @date 29/03/16
 */
@Path("/userdb")
@RolesAllowed({"admin"})
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserDBResource {

    private static UserDao dao = BDDFactory.getDbi().open(UserDao.class);
    final static Logger logger = LoggerFactory.getLogger(UserDBResource.class);

    /**
     * Créé un utilsateur et l'ajoute dans la base de données
     * Exemple : curl "localhost:8080/v1/userdb" -X POST -d '{"mail":"toto@gmail.com", ...}'
     * 
     * @param user - Les parametres de l'utilisateur
     * @return user - Utilisateur créé
     */
    @POST
    public User createUser(User user) {
        System.out.println("Create user : " + user);
        user.resetPasswordHash();
        dao.insert(user);
        return user;
    }

    /**
     * Recherche un utilisateur par son mail
     * Exemple : curl "localhost:8080/v1/userdb/toto@gmail.com" -X GET 
     * 
     * @param mail - Mail de l'utilisateur
     * @return user - Utilisateur trouvé
     */
    @GET
    @RolesAllowed({"admin,user"})
    @Path("/{mail}")
    public Response getUser(@PathParam("mail") String mail) {
        User user = dao.findByMail(mail);
        if (user == null) {
            return Response.accepted().status(404).build();
        }
        return Response.accepted().status(202).entity(user).build();
    }

    /**
     * Supprime un utilisateur en passant son mail
     * Exemple : curl "localhost:8080/v1/userdb/toto@gmail.com" -X DELETE
     * 
     * @param mail - Mail de l'utilisateur
     * @return user - Utilisateur supprimé
     */
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

    /**
     * Met à jour un utilisateur
     * Exemple : curl "localhost:8080/v1/userdb/toto@gmail.com" -X PUT -d '{"mail":"titi@gmail.com", ...}'
     * 
     * @param user - Utilisateur à modifier
     * @return user - Utilisateur modifié
     */
    @PUT
    @Path("/{mail}")
    public Response updateUser(User user) {
        User oldUser = dao.findByMail(user.getMail());
        System.out.println("Updating user : " + user);
        if (user.equals(oldUser)) {
            System.err.println("L'update n'a provoque aucune modification. La/les modifications sont surement identique a l'original");
            return Response.accepted().status(418).build();
        }
        if (oldUser == null) {
            return Response.accepted().status(404).build();
        }
        dao.update(user);
        return Response.accepted().status(202).entity(user).build();
    }

    /**
     * Recupere le salt lié à l'addresse mail
     * Exemple : curl "localhost:8080/v1/userdb/salt" -X GET
     * 
     * @param mail
     * @return salt
     */
    @GET
    @Path("/salt")
    @RolesAllowed({"admin,user"})
    public String getSalt(@QueryParam("mail") String mail) {
        return dao.getSalt(mail);
    }

    /**
     * Retourne la liste de tout les utilisateurs dans la base de données
     * Exemple : curl "localhost:8080/v1/userdb" -X GET
     * 
     * @return users - Liste de tout les utilisateurs dans la base
     */
    @GET
    public List<User> getAllUsers() {
        return dao.all();
    }
}
