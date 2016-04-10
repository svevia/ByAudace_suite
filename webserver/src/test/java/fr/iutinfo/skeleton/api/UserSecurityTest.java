package fr.iutinfo.skeleton.api;

import static org.junit.Assert.*;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

public class UserSecurityTest extends JerseyTest{
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
    public void password_should_be_hached() {
    	h.createUser("tc@gmail.com", "Clavier", "Thomas", "AZERTY", "1234pouet", "user","000");
    	User user = target("/userdb/Clavier").request().get(User.class);
        assertNotEquals("1234pouet", user.getMot_de_passe());
    }
	
	@Test
    public void should_set_salt_at_build () {
        User user = new User();
        assertNotNull(user.getSalt());
        assertFalse(user.getSalt().isEmpty());
    }

}
