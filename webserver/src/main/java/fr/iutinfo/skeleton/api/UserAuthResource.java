package fr.iutinfo.skeleton.api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/auth")
public class UserAuthResource {
    private static UserDao dao = BDDFactory.getDbi().open(UserDao.class);
    final static Logger logger = LoggerFactory.getLogger(UserAuthResource.class);
    
    @POST
    @Path("/{mail}")
    public Response connect(@PathParam("mail") String mail, String mot_de_passe) {
        User user = dao.findByMail(mail);
        //System.out.println("mail=" + mail + ", mdp=" + mot_de_passe + ", user=" + user);
        if (user == null) {
            return Response.accepted().status(404).build();
        } else if (user.getMot_de_passe().equals(mot_de_passe)) {
            return Response.accepted().status(202).entity(mail).build();
        } else {
            return Response.accepted().status(403).build();
        }
    }
}
