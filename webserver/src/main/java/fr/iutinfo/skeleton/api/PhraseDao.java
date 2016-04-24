package fr.iutinfo.skeleton.api;

import java.util.List;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

/**
 * Requêtes SQL sur la table utilisateur utilisés dans UserDBResource
 * @author seysn
 */
public interface PhraseDao {
    //insertion d'une phrase (methode post)
    @SqlUpdate("insert into phrase_metier (phrase, besoin, mail, categorie, terminee) values (:phrase, :besoin , :mail, :categorie,  :terminee)")
    @GetGeneratedKeys
    int insert(@BindBean() Phrase phrase);

    @SqlUpdate("insert into aide (mail_repondant,phrase,date) values (:utilisateur,:phrase,:date)")
    int help(@BindBean() Aide aide);
    
    
    //selection d'une phrase en fonction de son intitule "phrase" (methode get)
    @SqlQuery("select * from phrase_metier where id = :id")
    @RegisterMapperFactory(BeanMapperFactory.class)
    Phrase findById(@Bind("id") int id);
    
    //selection d'une phrase en fonction de son intitule "phrase" (methode get)
    @SqlQuery("select * from phrase_metier where phrase = :phrase")
    @RegisterMapperFactory(BeanMapperFactory.class)
    Phrase findByPhrase(@Bind("phrase") String phrase);
    
    //suppression d'une phrase en fonction de son intitule "phrase" (utilisee dans PhraseViews/index.jsp methode delete)
    @SqlUpdate("delete from phrase_metier where id = :id")
    void delete(@Bind("id") int id);
    
    //selection de toutes les phrases (utilisee dans PhraseViews/index.jsp methode get)
    @SqlQuery("select * from phrase_metier")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Phrase> all();
    //retourne le nombre total de phrases (utilisee dans StatistiqueViews/index.jsp methode get)
    @SqlQuery("select count(*) from phrase_metier")
    int getAllCount();
    //retourne le nombre total de phrases terminees (utilisee dans StatistiqueViews/index.jsp methode get)
    @SqlQuery("select count(*) from phrase_metier where terminee = :bool")
    int getTermCount(@Bind("bool") boolean bool);
    //selectionne les phrases correspondant a la recherche (utilisee dans PhraseViews/index.jsp methode get)
    @SqlQuery("select * from phrase_metier where mail like :search "
            + "or besoin like :search or phrase like :search")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Phrase> search(@Bind("search") String search);
    
    //selectionne les phrases triees par champs (utilisee dans PhraseViews/index.jsp methode get)
    
    @SqlQuery("select * from phrase_metier order by besoin")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Phrase> orderBesoin();

    @SqlQuery("select * from phrase_metier order by phrase")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Phrase> orderPhrase();
    
    @SqlQuery("select * from phrase_metier order by consultee")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Phrase> orderConsultee();
    
    @SqlQuery("select * from phrase_metier order by mail")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Phrase> orderMail();
    
    @SqlQuery("select * from phrase_metier order by terminee")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Phrase> orderTerminee();
    
    //cree la table contenant les phrases
    @SqlUpdate("create table phrase_metier(id INTEGER PRIMARY KEY AUTOINCREMENT, phrase CHAR(300) NOT NULL,besoin CHAR(300),date DATETIME,mail CHAR(200) NOT NULL,categorie CHAR(200),terminee BOOLEAN DEFAULT \"false\",FOREIGN KEY(mail) REFERENCES utilisateur(mail) ON UPDATE CASCADE ON DELETE CASCADE)")
    void createPhraseTable();
    
    //creee la table d'association entre User et phrase indiquant qui a aidé une phrase
    @SqlUpdate("CREATE TABLE aide(mail_repondant CHAR(200),phrase CHAR(300),date DATETIME,FOREIGN KEY(mail_repondant) REFERENCES utilisateur(mail) ON UPDATE CASCADE ON DELETE CASCADE,FOREIGN KEY(phrase) REFERENCES phrase_metier(phrase) ON UPDATE CASCADE ON DELETE CASCADE,PRIMARY KEY(mail_repondant,phrase,date))")
    void createAideTable();
    
    void close();
}
