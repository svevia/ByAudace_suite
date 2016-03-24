package fr.iutinfo.skeleton.api;

import fr.iutinfo.skeleton.auth.AuthFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;


@Path("/secure")
public class SecureResource {
    final static Logger logger = LoggerFactory.getLogger(SecureResource.class);

    @GET
    @Path("/byannotation")
    @RolesAllowed({"admin"})
    public User secureByAnnotation(@Context SecurityContext context) {
        return (User) context.getUserPrincipal();
    }

}
