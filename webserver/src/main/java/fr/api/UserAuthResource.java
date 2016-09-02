package fr.api;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Requêtes REST liés à la connection d'un utilisateur
 *
 */
@Path("/auth")
public class UserAuthResource {
    private static UserDao dao = BDDFactory.getDbi().open(UserDao.class);
    final static Logger logger = LoggerFactory.getLogger(UserAuthResource.class);


    /**
     * Vérifie si le mot de passe donné correspond avec le mot de passe dans la BDD
     * avec une methode POST.
     * Exemple : curl "localhost:8080/v1/auth/toto@gmail.com" -X POST -d "toto"
     *
     * @param mail
     * @param mot_de_passe
     * @return email, not found ou forbidden
     */
    @POST
    @RolesAllowed({"admin"})
    @Path("/{mail}")
    public Response connect(@PathParam("mail") String mail, String mot_de_passe) {
        User user = dao.findByMail(mail);
        if (user == null) {
            return Response.ok().status(404).build();
        } else if (user.getMot_de_passe().equals(mot_de_passe)) {
            return Response.ok().status(202).entity(mail).build();
        } else {
            return Response.ok().status(403).build();
        }
    }

    /**
     * Vérifie si le mot de passe donné correspond avec le mot de passe dans la BDD
     * avec une methode GET.
     * Exemple : curl "localhost:8080/v1/auth/toto@gmail.com?mot_de_passe=toto" -X GET
     *
     * @param mail
     * @param mot_de_passe
     * @return email, not found ou forbidden
     */
    @GET
    @Path("/{mail}")
    public Response connectGet(@PathParam("mail") String mail, @QueryParam("mot_de_passe") String mot_de_passe) {
        User user = dao.findByMail(mail);
        if (user == null) {
            return Response.ok().status(404).build();
        } else if (user.getMot_de_passe().equals(mot_de_passe)) {
            return Response.ok().status(202).entity(mail).build();
        } else {
            return Response.ok().status(403).build();
        }
    }
}
