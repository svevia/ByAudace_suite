package fr.api;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import fr.auth.AuthFilter;

@ApplicationPath("/v1/") //Ajouter /V1/ devant tout URL utilisant l'API
public class Api extends ResourceConfig {

    public Api() {
        packages("fr.api");
        //register(LoggingFilter.class);
        register(AuthFilter.class);
        register(RolesAllowedDynamicFeature.class);
    }

}
