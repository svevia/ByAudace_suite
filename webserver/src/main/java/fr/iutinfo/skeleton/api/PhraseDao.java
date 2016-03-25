package fr.iutinfo.skeleton.api;

import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

import java.util.List;

public interface PhraseDao {

	@SqlUpdate("insert into phrase_metier (phrase, besoin, mail_deposant, terminee, consultee) values (:phrase, :besoin, :mail_deposant, :terminee, :consultee)")
	@GetGeneratedKeys
	Phrase insert(@BindBean() Phrase phrase);

	@SqlQuery("select * from phrase_metier where phrase = :phrase")
    @RegisterMapperFactory(BeanMapperFactory.class)
	Phrase findByPhrase(@Bind("phrase") String phrase);
        
        @SqlUpdate("delete from phrase_metier where phrase = :phrase")
        void delete(@Bind("phrase") String phrase);

	@SqlQuery("select * from phrase_metier")
	@RegisterMapperFactory(BeanMapperFactory.class)
	List<Phrase> all();

	void close();
}
