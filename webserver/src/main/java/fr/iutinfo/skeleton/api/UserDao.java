package fr.iutinfo.skeleton.api;

import java.util.List;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

public interface UserDao {

	@SqlUpdate("INSERT INTO utilisateur (mail, numero, nom, prenom, digit, mot_de_passe, role) values (:mail, :numero, :nom, :prenom, :digit, :mot_de_passe, :role)")
	@GetGeneratedKeys
	int insert(@BindBean() User user);

	@SqlQuery("SELECT * FROM utilisateur where mail = :mail")
	@RegisterMapperFactory(BeanMapperFactory.class)
	User findByMail(@Bind("mail") String mail);

        @SqlQuery("select salt from utilisateur where mail = :mail")
        String getSalt(@Bind("mail") String mail);
        
	@SqlQuery("SELECT * FROM utilisateur")
	@RegisterMapperFactory(BeanMapperFactory.class)
	List<User> all();

	@SqlUpdate("DELETE utilisateur WHERE mail = :mail")
	void delete(@Bind("mail") String mail);

	@SqlUpdate("DROP TABLE IF EXISTS utilisateur")
	void dropUserTable(); 
	
	@SqlUpdate("UPDATE utilisateur SET (numero = :numero, digit = :digit, mot_de_passe = :mot_de_passe) WHERE mail = :mail")
	void update(@BindBean() User user);

	@SqlUpdate("CREATE TABLE utilisateur (mail CHAR(200) PRIMARY KEY NOT NULL, nom CHAR(200), prenom CHAR(200), digit CHAR(20), numero CHAR(20), mot_de_passe CHAR(50) NOT NULL, role CHAR(50))")
	void createUserTable();

	void close();
}
