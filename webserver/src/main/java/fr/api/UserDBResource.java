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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

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
     * Créé un utilsateur et l'ajoute dans la base de données
     *
     * @param user - Les parametres de l'utilisateur
     * @return user - Utilisateur créé
     */
    @POST
    @RolesAllowed({"admin", "animateur"})
    public User createUser(User user) {
    	if(dao.findByMail(user.getMail())== null && (user.getRole().equals("user")|| user.getRole().equals("animateur"))){
    		user.setSignalement(0);
    		String pass = user.generatePass();
    		user.setMot_de_passe(pass);
    		Mailer.sendMail(user.getMail(), Mailer.pass(pass), "Votre mot de passe Audace"); 
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
     * Créé un utilsateur et l'ajoute dans la base de données
     *
     * @param user - Les parametres de l'utilisateur
     * @return user - Utilisateur créé
     */
    @POST
    @Path("/beta")
    public User createUserBeta(User user) {
    	if(dao.findByMail(user.getMail())== null && (user.getRole().equals("user")|| user.getRole().equals("animateur"))){
    		user.setSignalement(0);
    		String pass = user.generatePass();
    		user.setMot_de_passe(pass);
    		Mailer.sendMail(user.getMail(), Mailer.pass(pass), "Votre mot de passe Audace"); 
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
     * Créé un admin et l'ajoute dans la base de données : reservé à l'utilisateur root
     *
     * @param user - Les parametres de l'utilisateur
     * @return user - Utilisateur créé
     */
    @POST
    @RolesAllowed("root")
    @Path("/admin")
    public User createAdmin(User user) {
    	if(dao.findByMail(user.getMail())== null && user.getRole().equals("admin")){
    		user.setSignalement(0);
    		String pass = user.generatePass();
    		user.setMot_de_passe(pass);
    		Mailer.sendMail(user.getMail(), Mailer.pass(pass), "Votre mot de passe Audace"); 
	        user.resetPasswordHash();
	        dao.insert(user);
	        logger.trace("creation admin " + user.getMail() + " -- id = " + user.getId());
	        return user;
    	}
    	else{
    		return null;
    	}
    }

    
    /**
     * Fonction permettant l'edition d'un utilisateur par un admin
     * @param user
     * @return
     */
    @POST
    @RolesAllowed("admin")
    @Path("/edit")
    public User editUser(User user) {
    	if(user.getRole().equals("user") || user.getRole().equals("animateur")){
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
    
    /**
     * Fonction permettant d'éditer son propre utilisateur
     * @param user
     * @param context
     * @return
     */
    @POST
    @RolesAllowed({"admin","user"})
    @Path("/editme")
    public User editMe(User user,@Context SecurityContext context) {
    	User log = (User) context.getUserPrincipal();
    	logger.trace("edit personnel du user + " + user.getId());
    	logger.trace("context user : " + log.getId());
    	if(((User) context.getUserPrincipal()).getId() == user.getId()){
	    	if((dao.findByMail(user.getMail()).getId() == user.getId())){
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


    /**
     * Cette fonction sert à éditer uniquement les admins, elle est reservé à l'utilisateur root
     * @param user
     * @return
     */
    
    @POST
    @RolesAllowed("root")
    @Path("/edit/admin")
    public User editAdmin(User user) {
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
   
    /**
     * Envoi un mail à n'importe quelle adresse depuis l'adresse no-reply@campus-audace.fr
     * @param m
     */
    @POST
    @RolesAllowed("admin")
    @Path("/mail")
    public void sendMail(Mail m) {
    	List<String> categories = new Gson().fromJson(m.getCategories(), List.class);//décompose le json en ArrayList
    	for(String cat :categories){
    		System.out.println(cat);
	    	List<String> mails = dao.getAllMail(cat);
	    	for(String mail : mails){
	    		m.setAdresse(mail);
	    		Mailer.sendMail(m);
	    	}
    	}
    }
    
    /**
     * Envoi un mail automatique quand une personne se propose d'aider un besoin
     * @param m
     */
    @POST
    @RolesAllowed({"admin","user"})
    @Path("/send")
    public void sendHelp(Mail m) {
    		Mailer.sendMail(m);
    }
    
    /**
     * Crée une nouvelle catégorie d'utilisateurs
     * @param cat
     * @return
     */
    @GET
    @RolesAllowed({"admin"})
    @Path("/newCat/{cat}")
    public Response newCat(@PathParam("cat")String cat) {
    	dao.insertCat(cat);
    	return Response.ok().build();
    }
    
    /**
     * Supprime une catégorie d'utilisateur
     * @param cat
     * @return
     */
    @GET
    @RolesAllowed({"admin"})
    @Path("/delCat/{cat}")
    public Response delCat(@PathParam("cat")String cat) {
    	dao.removeCat(cat);
    	return Response.ok().build();
    }
    

    /**
     * Recherche un utilisateur par son id
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
     * retourne le rôle d'un utilisateur
     * @param id
     * @return
     */
    @GET
    @RolesAllowed({"admin"})
    @Path("/role/{id}")
    public String getRole(@PathParam("id") int id) {
        User user = dao.findById(id);

        if (user == null) {
        	throw new WebApplicationException(403);
        }
        return user.getRole();
    }
    
    /**
     * Recherche un utilisateur par son mail
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
    	if(user != null){
	    	logger.trace("mot de passe perdu pour : " + mail);
			String pass = user.generatePass();
			user.setMot_de_passe(pass);
			Mailer.sendMail(user.getMail(), Mailer.pass(pass), "Votre mot de passe Audace"); 
	        user.resetPasswordHash();
	        dao.update(user);
    	}
    	return user;
    }

    /**
     * Supprime un utilisateur en passant son id
     * @param mail - Mail de l'utilisateur
     * @return user - Utilisateur supprimé
     */
    @DELETE
    @Path("/{id}")
    @RolesAllowed({"admin"})
    public Response deleteUser(@PathParam("id") int id) {
        User user = dao.findById(id);
        
        if (user == null || user.getRole().equals("admin") || user.getRole().equals("root")) {
            return Response.ok().status(404).build();
        }
        dao.delete(id);
        logger.trace("suppression user " + user.getMail() + " -- id = " + user.getId());
        return Response.ok().status(202).entity(user).build();
    }
    
    /**
     * Permet de supprimer un admin, fonction reservé à l'utilisateur root
     * @param id
     * @return
     */
    @DELETE
    @Path("/admin/{id}")
    @RolesAllowed({"root"})
    public Response deleteAdmin(@PathParam("id") int id) {
        User user = dao.findById(id);
        if (user == null) {
            return Response.ok().status(404).build();
        }
        dao.delete(id);
        logger.trace("suppression user " + user.getMail() + " -- id = " + user.getId());
        return Response.ok().status(202).entity(user).build();
    }
    
    /**
     * Recupere le salt lié à l'addresse mail
     *
     * @param mail
     * @return salt
     */
    @GET
    @Path("/salt")
    public Response getSalt(@QueryParam("mail") String mail) {
        String salt = dao.getSalt(mail);
        if (salt == null) {
        	throw new WebApplicationException(Status.UNAUTHORIZED);
        } else {
            return Response.ok().status(Response.Status.OK).entity(salt).build();
        }
    }
    
    
    /**
     * Retorune le nombre d'utilisateur
     * @return
     */
    @GET
    @Path("/nbr")
    @Produces(MediaType.TEXT_PLAIN)
    public Integer getNbrUser(){
    	return dao.getNbrUser();
    }

    /**
     * Retourne le nom et le prenom lié à l'id
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
     * @return users - Liste de tout les utilisateurs dans la base
     */
    @GET
    @RolesAllowed({"admin"})
    public List<User> getAllUsers() {
        return dao.all();
    }
    
    /**
     * Retourne la liste de toutes les categories d'utilisateurs
     * @return
     */
    @GET
    @Path("/cat")
    @RolesAllowed({"admin"})
    public Response getAllCat() {
    	GenericEntity<List<String>> genericEntity = new GenericEntity<List<String>>(dao.allCat()) {};
    	    return Response.ok(genericEntity).build();
    }
}
