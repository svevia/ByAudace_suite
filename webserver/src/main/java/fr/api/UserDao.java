package fr.api;

import java.util.List;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

/**
 * Requêtes SQL sur la table utilisateur utilisés dans UserDBResource
 */
public interface UserDao {

	//ajoute un utilisateur à la BDD
	@SqlUpdate("INSERT INTO utilisateur (mail, numero,name , prenom, categorie, mot_de_passe, role, salt) values (:mail, :numero, :name, :prenom, :categorie, :mot_de_passe, :role, :salt)")
	@GetGeneratedKeys
	int insert(@BindBean() User user);
	
	//ajoute un utilisateur à la BDD
	@SqlUpdate("INSERT INTO userCategorie (name) values (:name)")
	@GetGeneratedKeys
	int insertCat(@Bind("name") String name);

	//cherche un utilisateur spécifique depuis son id
	@SqlQuery("SELECT * FROM utilisateur where id = :id")
	@RegisterMapperFactory(BeanMapperFactory.class)
	User findById(@Bind("id") int id);
	
	//cherche un utilisateur spécifique depuis son id
	@SqlQuery("SELECT * FROM utilisateur where mail = :mail")
	@RegisterMapperFactory(BeanMapperFactory.class)
	User findByMail(@Bind("mail") String mail);

		//renvoi le salt servant au cryptage du mot de passe pour un utilisateur en articulier
		//(principalement utilisé par Android)
        @SqlQuery("select salt from utilisateur where mail = :mail")
        String getSalt(@Bind("mail") String mail);
        
        @SqlQuery("select mail from utilisateur where id = :id")
        String getMail(@Bind("id") int id);
	
        
        @SqlQuery("select name from utilisateur where id = :id")
        String getNom(@Bind("id") int id);
	
        @SqlQuery("select prenom from utilisateur where id = :id")
        String getPrenom(@Bind("id") int id);
        
        @SqlQuery("select numero from utilisateur where id = :id")
        String getNumero(@Bind("id") int id);
        
        
        //renvoi la liste des utilisateurs
        @SqlQuery("SELECT * FROM utilisateur order by mail asc")
	@RegisterMapperFactory(BeanMapperFactory.class)
	List<User> all();
        
        //renvoi la liste des utilisateurs
        @SqlQuery("SELECT name FROM userCategorie")
	@RegisterMapperFactory(BeanMapperFactory.class)
	List<String> allCat();


        //supprime un utilisateur
	@SqlUpdate("DELETE from utilisateur WHERE id = :id")
	void delete(@Bind("id") int id);

	//!!!!!suprime la table user!!!!!!! (pas utilisé à ce jour)
	@SqlUpdate("DROP TABLE IF EXISTS utilisateur")
	void dropUserTable(); 
	
	//update un user
	@SqlUpdate("UPDATE utilisateur SET mail = :mail, numero = :numero, name = :name, prenom = :prenom, categorie = :categorie, mot_de_passe = :mot_de_passe WHERE id = :id")
	void update(@BindBean() User user);

	
	//creee la table user
	@SqlUpdate("CREATE TABLE utilisateur(id INTEGER PRIMARY KEY AUTOINCREMENT, mail CHAR(200) UNIQUE NOT NULL,numero CHAR(20),name CHAR(200),prenom CHAR(200),categorie CHAR(200),mot_de_passe CHAR(50) NOT NULL,role CHAR(50),salt TEXT)")
	void createUserTable();
	
	//creee la table categorie user
	@SqlUpdate("CREATE TABLE userCategorie(id INTEGER PRIMARY KEY AUTOINCREMENT, name CHAR(200) UNIQUE NOT NULL)")
	void createCategorieUserTable();

	@SqlQuery("Select count(*) FROM utilisateur")
	int getNbrUser();
	
	@SqlQuery("Select mail FROM utilisateur")
	List<String> getAllMail();
	
    //selectionne les users correspondant a la recherche (utilisee dans UserView/index.jsp methode get)
    @SqlQuery("select * from utilisateur where mail like :search ")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<User> search(@Bind("search") String search);
	
    @SqlUpdate("DELETE from userCategorie WHERE name = :cat")
	void removeCat(@Bind("cat") String cat);


	void close();


}
