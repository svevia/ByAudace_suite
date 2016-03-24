package fr.iutinfo.skeleton.api;

import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

import java.util.List;

public interface PhraseDao {

	@SqlUpdate("insert into phrase_metier (phrase, besoin, mail, terminee, consultee) values (:phrase, :besoin, :mail, :terminee, :consultee)")
	@GetGeneratedKeys
	Phrase insert(@BindBean() String name);

	@SqlQuery("select * from phrase_metier where phrase = :name")
    @RegisterMapperFactory(BeanMapperFactory.class)
	Phrase findByName(@Bind("name") String name);

	@SqlQuery("select * from phrase_metier")
	@RegisterMapperFactory(BeanMapperFactory.class)
	List<Phrase> all();

	void close();
}
