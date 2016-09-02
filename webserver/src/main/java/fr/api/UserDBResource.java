package fr.api;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.skife.jdbi.v2.sqlobject.BindBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.api.User;
/**
 * Requêtes REST liés à la table utilisateurs de la base de données
 *
 */
@Path("/userdb")

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserDBResource {
    
    private static UserDao dao = BDDFactory.getDbi().open(UserDao.class);
    final static Logger logger = LoggerFactory.getLogger(UserDBResource.class);

    /**
     * Créé un utilsateur et l'ajoute dans la base de données Exemple : curl
     *
     * @param user - Les parametres de l'utilisateur
     * @return user - Utilisateur créé
     */
    @POST
    @RolesAllowed({"admin", "animateur"})
    public User createUser(User user) {
    	if(dao.findByMail(user.getMail())== null){
    		String pass = user.generatePass();
    		user.setMot_de_passe(pass);
    		Mailer.sendMail(user.getMail(), Mailer.pass(pass), "cotre mot de passe Audace"); 
	        user.resetPasswordHash();
	        dao.insert(user);
	        logger.trace("creation user " + user.getMail() + " -- id = " + dao.findByMail(user.getMail()).getId());
	        return user;
    	}
    	else{
    		return null;
    	}
    }
    
    /**
     * Créé un utilsateur et l'ajoute dans la base de données Exemple : curl
     *
     * @param user - Les parametres de l'utilisateur
     * @return user - Utilisateur créé
     */
    @POST
    @RolesAllowed("root")
    @Path("/admin")
    public User createAdmin(User user) {
    	if(dao.findByMail(user.getMail())== null){
	        user.resetPasswordHash();
	        dao.insert(user);
	        logger.trace("creation admin " + user.getMail() + " -- id = " + user.getId());
	        return user;
    	}
    	else{
    		return null;
    	}
    }

    
    
    @POST
    @RolesAllowed("admin")
    @Path("/edit")
    public User editUser(User user) {
    	if((dao.findByMail(user.getMail()) == null) ||(dao.findByMail(user.getMail()).getId() == user.getId())){
    		if(!(user.getMot_de_passe().equals(dao.findById(user.getId()).getMot_de_passe()))){//mot de passe changé, donc on le hash
    			user.setSalt(dao.findById(user.getId()).getSalt());
    			user.resetPasswordHash();
    		}
	        dao.update(user);
	        return user;
    	}
    	else{
    		return null;
    	}
    }
    
    
    @POST
    @RolesAllowed({"admin","user"})
    @Path("/editme")
    public User editMe(User user,@Context SecurityContext context) {
    	User log = (User) context.getUserPrincipal();
    	logger.trace("edit personnel du user + " + user.getId());
    	logger.trace("context user : " + log.getId());
    	if(((User) context.getUserPrincipal()).getId() == user.getId()){
	    	if((dao.findByMail(user.getMail()) == null) ||(dao.findByMail(user.getMail()).getId() == user.getId())){
	    		if(!(user.getMot_de_passe().equals(dao.findById(user.getId()).getMot_de_passe()))){//mot de passe changé, donc on le hash
	    			user.setSalt(dao.findById(user.getId()).getSalt());
	    			user.resetPasswordHash();
	    		}
		        dao.update(user);
		        return user;
	    	}
	    	else{
	    		return null;
	    	}
    	}
    	else{
    		return null;
    	}
    }


    
    @POST
    @RolesAllowed("root")
    @Path("/edit/admin")
    public User editAdmin(User user) {
    	if((dao.findByMail(user.getMail())== null) ||(dao.findByMail(user.getMail()).getId() == user.getId()) ){
    		if(!(user.getMot_de_passe().equals(dao.findById(user.getId()).getMot_de_passe()))){//mot de passe changé, donc on le hash
    			user.setSalt(dao.findById(user.getId()).getSalt());
    			user.resetPasswordHash();
    		}
        dao.update(user);
        return user;
	}
	else{
		return null;
	}
}

   
    
    @POST
    @RolesAllowed("admin")
    @Path("/mail")
    public void sendMail(String m) {
    	List<String> mails = dao.getAllMail();
    	for(String mail : mails){
    		Mailer.sendMail(mail, m, "Audace a besoin de vous !");
    	}
    }
    
    
    @POST
    @RolesAllowed({"admin","user"})
    @Path("/send")
    public void sendHelp(Mail m) {
    	List<String> mails = dao.getAllMail();
    	for(String mail : mails){
    		Mailer.sendMail(m);
    	}
    }
    
    @POST
    @RolesAllowed({"admin", "animateur"})
    @Path("/newCat")
    public void newCat(String cat) {
    	dao.insertCat(cat);
    }
    

    /**
     * Recherche un utilisateur par son mail Exemple : curl
     * "localhost:8080/v1/userdb/toto@gmail.com" -X GET
     *
     * @param mail - Mail de l'utilisateur
     * @return user - Utilisateur trouvé
     */
    @GET
    @RolesAllowed({"admin", "user"})
    @Path("/{id}")
    public User getUser(@PathParam("id") int id) {
        User user = dao.findById(id);

        if (user == null) {
        	throw new WebApplicationException(403);
        }
        return user;
    }
    
    /**
     * Recherche un utilisateur par son mail Exemple : curl
     * "localhost:8080/v1/userdb/toto@gmail.com" -X GET
     *
     * @param mail - Mail de l'utilisateur
     * @return user - Utilisateur trouvé
     */
    @GET
    @RolesAllowed({"admin", "user"})
    @Path("/mail/{mail}")
    public User getUserByMail(@PathParam("mail") String mail) {
        User user = dao.findByMail(mail);
        if (user == null) {
        	throw new WebApplicationException(403);
        }
        return user;
    }
    
    
    /**
     * Recherche un user à l'aide de son mail
     * 
     * @param search
     * @return phrase
     */
    @GET
    @Path("/search")
    public List<User> search(@QueryParam("search") String search) {
    	if(search == ""){
    		return dao.all();
    	}
    	else{
    		return dao.search("%" + search + "%");
    	}
    }
    
    
    /**
     * Mot de passe oublié
     * 
     * @param id
     * @return user
     */
    @GET
    @Path("/lost/{mail}")
    public User lost(@PathParam("mail") String mail) {
    	User user = dao.findByMail(mail);
    	logger.trace("mot de passe perdu pour : " + mail);
		String pass = user.generatePass();
		user.setMot_de_passe(pass);
		Mailer.sendMail(user.getMail(), Mailer.pass(pass), "Votre mot de passe Audace"); 
        user.resetPasswordHash();
        
        dao.update(user);
    	return user;
    }

    /**
     * Supprime un utilisateur en passant son mail Exemple : curl
     * "localhost:8080/v1/userdb/toto@gmail.com" -X DELETE
     *
     * @param mail - Mail de l'utilisateur
     * @return user - Utilisateur supprimé
     */
    @DELETE
    @Path("/{id}")
    @RolesAllowed({"admin"})
    public Response deleteUser(@PathParam("id") int id) {
        User user = dao.findById(id);
        if (user == null) {
            return Response.accepted().status(404).build();
        }
        dao.delete(id);
        logger.trace("suppression user " + user.getMail() + " -- id = " + user.getId());
        return Response.accepted().status(202).entity(user).build();
    }
    
    /**
     * Recupere le salt lié à l'addresse mail Exemple : curl
     * "localhost:8080/v1/userdb/salt?toto@gmail.com" -X GET
     *
     * @param mail
     * @return salt
     */
    @GET
    @Path("/salt")
    public Response getSalt(@QueryParam("mail") String mail) {
        String salt = dao.getSalt(mail);
        if (salt == null) {
            return Response.accepted().status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.accepted().status(Response.Status.OK).entity(salt).build();
        }
    }
    
    
    @GET
    @Path("/nbr")
    @Produces(MediaType.TEXT_PLAIN)
    public Integer getNbrUser(){
    	return dao.getNbrUser();
    }

    /**
     * Retourne le nom et le prenom lié au mail
     * Exemple : curl "localhost:8080/v1/userdb/user?mail=toto@gmail.com" -X GET
     * 
     * @param mail
     * @return nom et prenom
     */
    @GET
    @Path("/user")
    public String getNomAndPrenom(@QueryParam("id") int id) {
        return "NOM:" + dao.getNom(id) + ",PRENOM:" + dao.getPrenom(id) + ",SALT:" + dao.getSalt(dao.getMail(id)) + ",NUMERO:" + dao.getNumero(id);
    }

    /**
     * Retourne la liste de tout les utilisateurs dans la base de données
     * Exemple : curl "localhost:8080/v1/userdb" -X GET
     *
     * @return users - Liste de tout les utilisateurs dans la base
     */
    @GET
    @RolesAllowed({"admin"})
    public List<User> getAllUsers() {
        return dao.all();
    }
    
    @GET
    @Path("/cat")
    @RolesAllowed({"admin"})
    public List<String> getAllCat() {
        return dao.allCat();
    }
}
