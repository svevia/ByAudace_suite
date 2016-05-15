package fr.iutinfo.skeleton.api;

import java.util.List;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

/**
 * Requêtes SQL sur la table utilisateur utilisés dans UserDBResource
 * @author seysn
 */
public interface UserDao {

	//ajoute un utilisateur à la BDD
	@SqlUpdate("INSERT INTO utilisateur (mail, numero, nom, prenom, digit, mot_de_passe, role, salt) values (:mail, :numero, :nom, :prenom, :digit, :mot_de_passe, :role, :salt)")
	@GetGeneratedKeys
	int insert(@BindBean() User user);

	//cherche un utilisateur spécifique depuis son mail
	@SqlQuery("SELECT * FROM utilisateur where mail = :mail")
	@RegisterMapperFactory(BeanMapperFactory.class)
	User findByMail(@Bind("mail") String mail);

		//renvoi le salt servant au cryptage du mot de passe pour un utilisateur en articulier
		//(principalement utilisé par Android)
        @SqlQuery("select salt from utilisateur where mail = :mail")
        String getSalt(@Bind("mail") String mail);
        
        
        
        @SqlQuery("select nom from utilisateur where mail = :mail")
        String getNom(@Bind("mail") String mail);
	
        @SqlQuery("select prenom from utilisateur where mail = :mail")
        String getPrenom(@Bind("mail") String mail);
        
        @SqlQuery("select numero from utilisateur where mail = :mail")
        String getNumero(@Bind("mail") String mail);
        
        
        //renvoi la liste des utilisateurs
        @SqlQuery("SELECT * FROM utilisateur order by mail asc")
	@RegisterMapperFactory(BeanMapperFactory.class)
	List<User> all();

        //supprime un utilisateur
	@SqlUpdate("DELETE from utilisateur WHERE mail = :mail")
	void delete(@Bind("mail") String mail);

	//!!!!!suprime la table user!!!!!!! (pas utilisé à ce jour)
	@SqlUpdate("DROP TABLE IF EXISTS utilisateur")
	void dropUserTable(); 
	
	//update un user
	@SqlUpdate("UPDATE utilisateur SET (numero = :numero, digit = :digit, mot_de_passe = :mot_de_passe) WHERE mail = :mail")
	void update(@BindBean() User user);

	
	//creee la table user
	@SqlUpdate("CREATE TABLE utilisateur(mail CHAR(200) PRIMARY KEY NOT NULL,numero CHAR(20),nom CHAR(200),prenom CHAR(200),digit CHAR(20),mot_de_passe CHAR(50) NOT NULL,role CHAR(50),salt TEXT)")
	void createUserTable();

	@SqlQuery("Select count(*) FROM utilisateur")
	int getNbrUser();
	
	@SqlQuery("Select mail FROM utilisateur")
	List<String> getAllMail();
	
	
	void close();





}
