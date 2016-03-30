package fr.iutinfo.skeleton.web;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.glassfish.jersey.server.mvc.Template;

@Path("/statistiques")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.TEXT_HTML)
@RolesAllowed("admin")
public class StatistiquesViews {
    @GET
    @Template
    public String getAll(@Context SecurityContext context) {
        String name = context.getUserPrincipal().getName();
        return name;
    }
    
}