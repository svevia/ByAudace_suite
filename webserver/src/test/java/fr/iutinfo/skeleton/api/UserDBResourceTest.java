package fr.iutinfo.skeleton.api;

import static org.junit.Assert.*;

import java.util.List;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

public class UserDBResourceTest extends JerseyTest{
	private Helper h;
	
	@Override
    protected Application configure() {
        return new Api();
    }

    @Before
    public void init(){
        h = new Helper(target("/userdb"));
        h.initDb();
    }
    
    @Test
    public void should_return_user_as_object() {
    	h.createUser("Clavier", "Thomas", "tc@gmail.com", "1234pouet", "AZERTY", "user");
    	User utilisateur = target("/userdb").request().get(User.class);
    	assertEquals("tc@gmail.com", utilisateur.getMail());
    }
    
    @Test
    public void should_return_all_users() {
    	h.createUser("Clavier", "Thomas", "tc@gmail.com", "1234pouet", "AZERTY", "user");
    	h.createUser("Seq", "Yann", "ys@gmail.com", "azerty123", "OUIOUI", "admin");
        List<User> users = target("/userdb").request().get(new GenericType<List<User>>() {});
        assertEquals(2, users.size());
    }
    
    @Test
    public void list_all_must_be_ordered() {
    	h.createUser("Clavier", "Thomas", "tc@gmail.com", "1234pouet", "AZERTY", "user");
    	h.createUser("Seq", "Yann", "ys@gmail.com", "azerty123", "OUIOUI", "admin");
        List<User> users = target("/userdb").request().get(new GenericType<List<User>>() {});
        assertEquals("tc@gmail.com", users.get(0).getMail());
    }

}
