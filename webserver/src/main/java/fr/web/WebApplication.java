package fr.web;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

import fr.auth.AuthFilter;

import javax.ws.rs.ApplicationPath;


@ApplicationPath("html")//ajouter /html/ devant chaque URL utilisant les vues de la page web
public class WebApplication extends ResourceConfig {

    public WebApplication() {
        packages("fr.web");
        register(JspMvcFeature.class);
        // Tracing support.
        //property(ServerProperties.TRACING, TracingConfig.ON_DEMAND.name());
        register(AuthFilter.class);
        register(RolesAllowedDynamicFeature.class);
    }


}
