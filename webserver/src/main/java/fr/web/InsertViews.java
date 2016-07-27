package fr.web;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.glassfish.jersey.server.mvc.Template;

import fr.api.User;

/**
 * Vue pour la page d'insertion d'un nouvel utilisateur
 *
 */

@Path("/insert")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
@RolesAllowed({"admin","animateur"})
public class InsertViews {
    @GET
    @Template
    public User getAll(@Context SecurityContext context) {
        User user = (User) context.getUserPrincipal();
        return user;
    }
    
}