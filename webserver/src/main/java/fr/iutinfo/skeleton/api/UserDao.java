package fr.iutinfo.skeleton.api;

import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

import java.util.List;

public interface UserDao {

	/*@SqlUpdate("insert into utilisateur (mail, nom, prenom, digit, mot_de_passe, role) values (:mail, :nom, :prenom, :digit, :mot_de_passe, :role)")
	//@GetGeneratedKeys
	void insert(@BindBean() User user);*/

        @SqlUpdate("insert into utilisateur (mail, nom, prenom, digit, mot_de_passe, role) values (:mail, :nom, :prenom, :digit, :mot_de_passe, :role)")
	@GetGeneratedKeys
	int insert(@Bind("mail") String mail, @Bind("nom") String nom, @Bind("prenom") String prenom, 
            @Bind("digit") String digit, @Bind("mot_de_passe") String mot_de_passe, @Bind("role") String role);
    
	@SqlQuery("select * from utilisateur where mail = :mail")
        @RegisterMapperFactory(BeanMapperFactory.class)
	User findByMail(@Bind("mail") String mail);

	@SqlQuery("select * from utilisateur")
	@RegisterMapperFactory(BeanMapperFactory.class)
	List<User> all();

	void close();
}
