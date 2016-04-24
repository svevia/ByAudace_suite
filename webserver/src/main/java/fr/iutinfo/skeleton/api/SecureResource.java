package fr.iutinfo.skeleton.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("/secure")
public class SecureResource {
    final static Logger logger = LoggerFactory.getLogger(SecureResource.class);

    @GET
    @Path("/byannotation")
    public User secureByAnnotation(@Context SecurityContext context) {
        return (User) context.getUserPrincipal();
    }

}
