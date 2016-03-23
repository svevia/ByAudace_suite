package fr.iutinfo.skeleton.api;

import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

import java.util.List;

public interface UserDao {

	@SqlUpdate("insert into users (name,alias,email, passwdHash, salt) values (:name, :alias, :email, :passwdHash, :salt)")
	@GetGeneratedKeys
	int insert(@BindBean() User user);

	@SqlQuery("select * from utilisateur where mail = :mail")
    @RegisterMapperFactory(BeanMapperFactory.class)
	User findByMail(@Bind("mail") String mail);

	@SqlQuery("select * from utilisateur")
	@RegisterMapperFactory(BeanMapperFactory.class)
	List<User> all();

	void close();
}
