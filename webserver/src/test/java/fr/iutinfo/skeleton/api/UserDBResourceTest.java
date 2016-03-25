/*package fr.iutinfo.skeleton.api;

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
    	User utilisateur = target("/userdb/").request().get(User.class);
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

    @Test
    public void testReadUserWithNameFooAsJsonString() {
    	h.createUser("Clavier", "Thomas", "tc@gmail.com", "1234pouet", "AZERTY", "user");
        String json = target("/userdb").request().get(String.class);
        assertTrue(json.contains("\"mail\":\"tc@gmail.com\""));
    }

    @Test
    public void testReadUserWithNameAsObject() {
        User utilisateur = target("/userdb/Clavier").request().get(User.class);
        assertEquals("Clavier", utilisateur.getName());
    }

    @Test
    public void testCreateUserMustReturnUserWithMail() {
        User savedUser = h.createUser("Clavier", "Thomas", "tc@gmail.com", "1234pouet", "AZERTY", "user");
        assertFalse(savedUser.getMail().equals(""));
    }

    @Test
    public void testUpdateUserName() {
        User u = h.createUser("Clavier", "Thomas", "tc@gmail.com", "1234pouet", "AZERTY", "user");
        u.setPrenom("yann");
        Response rep = target("/userdb").path("" + u.getNom.request()
                .put(Entity.entity(u, MediaType.APPLICATION_JSON));
        User updatedUser = rep.readEntity(User.class);
        assertEquals("yann", updatedUser.getNom());
    }

    @Test
    public void testGetingSameUserTwice() {
        User user1 = target("/userdb/mail").request().get(User.class);
        User user2 = target("/userdb/mail").request().get(User.class);
        assertEquals(user1.toString(), user2.toString());
    }

    @Test
    public void testReadUnavailableUser() {
        int status = target("/userdb/bar").request().get().getStatus();
        assertEquals(404, status);
    }

    @Test
    public void tesListAllUsers() {
		h.createUser("Clavier", "Thomas", "tc@gmail.com", "1234pouet", "AZERTY", "user");
    	h.createUser("Seq", "Yann", "ys@gmail.com", "azerty123", "OUIOUI", "admin");
        List<User> users = target("/userdb/").request().get(new GenericType<List<User>>() {});
        assertTrue(users.size() >= 2);
    }

    @Test
    public void after_delete_read_user_sould_return_202() {
        User u = h.createUser("Clavier", "Thomas", "tc@gmail.com", "1234pouet", "AZERTY", "user");
        int status = target("/userdb/" + u.getMail()).request().delete().getStatus();
        assertEquals(202, status);

    }

}
*/