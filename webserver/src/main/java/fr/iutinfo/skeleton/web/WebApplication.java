package fr.iutinfo.skeleton.web;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

import fr.iutinfo.skeleton.auth.AuthFilter;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("html")
public class WebApplication extends ResourceConfig {

    public WebApplication() {
        packages("fr.iutinfo.skeleton.web");
        register(JspMvcFeature.class);
        // Tracing support.
        //property(ServerProperties.TRACING, TracingConfig.ON_DEMAND.name());
        register(AuthFilter.class);
        register(RolesAllowedDynamicFeature.class);
    }


}
