package fr.iutinfo.skeleton.api;

import java.util.List;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

public interface UserDao {

	@SqlUpdate("insert into utilisateur (mail, numero, nom, prenom, digit, mot_de_passe, role) values (:mail, :numero, :nom, :prenom, :digit, :mot_de_passe, :role)")
	@GetGeneratedKeys
	int insert(@BindBean() User user);
    
	@SqlQuery("select * from utilisateur where mail = :mail")
        @RegisterMapperFactory(BeanMapperFactory.class)
	User findByMail(@Bind("mail") String mail);

	@SqlQuery("select * from utilisateur")
	@RegisterMapperFactory(BeanMapperFactory.class)
	List<User> all();
	
        @SqlUpdate("delete from utilisateur where mail = :mail")
        void delete(@Bind("mail") String mail);
        
	@SqlUpdate("drop table if exists utilisateur")
	void dropUserTable(); 

	@SqlUpdate("CREATE TABLE utilisateur (mail CHAR(200) PRIMARY KEY NOT NULL, nom CHAR(200), prenom CHAR(200), digit CHAR(20), numero CHAR(20), mot_de_passe CHAR(50) NOT NULL, role CHAR(50))")
	void createUserTable();
	
	void close();
}
