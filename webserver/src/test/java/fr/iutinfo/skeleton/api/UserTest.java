package fr.iutinfo.skeleton.api;

import static org.junit.Assert.*;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

public class UserTest extends JerseyTest{
	private Helper h;

    @Before
    public void init () {
        h = new Helper(target("/userdb"));
        h.initDb();
    }
    
    @Override
    protected Application configure() {
        return new Api();
    }
    
    @Test
    public void test_create_user () {
    	h.createUser("Clavier", "Thomas", "digit", "tc@toto.com");
        User utilisateur = target("/userdb").request().get(User.class);
        assertEquals("Clavier", utilisateur.getName());
    }
    
    

}
