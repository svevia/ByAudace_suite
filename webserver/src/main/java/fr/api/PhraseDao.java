package fr.api;

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
    @SqlUpdate("insert into phrase (phrase, besoin, date, id_user, categorie, terminee, signalement) values (:phrase, :besoin, :date , :id_user, :categorie,  :terminee, :signalement)")
    @GetGeneratedKeys
    int insert(@BindBean() Phrase phrase);

    @SqlUpdate("insert into aide (id_repondant,phrase,date) values (:utilisateur,:phrase,:date)")
    int help(@BindBean() Aide aide);
    
    
    //selection d'une phrase en fonction de son id (methode get)
    @SqlQuery("select * from phrase where id = :id")
    @RegisterMapperFactory(BeanMapperFactory.class)
    Phrase findById(@Bind("id") int id);
    
    //selection d'une phrase en fonction de son id (methode get)
    @SqlQuery("select * from phrase where phrase = :phrase")
    @RegisterMapperFactory(BeanMapperFactory.class)
    Phrase findByPhrase(@Bind("phrase") String phrase);
    
    //suppression d'une phrase en fonction de son id (utilisee dans PhraseViews/index.jsp methode delete)
    @SqlUpdate("delete from phrase where id = :id")
    void delete(@Bind("id") int id);
    
    @SqlUpdate("UPDATE phrase SET signalement = signalement + 1 where id = :id")
	void signal(@Bind("id") int id);
    
    //selection de toutes les phrases (utilisee dans PhraseViews/index.jsp methode get)
    @SqlQuery("select * from phrase order by date desc")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Phrase> all();
    
    //selection de toutes les phrases (utilisee dans PhraseViews/index.jsp methode get)
    @SqlQuery("select * from phrase where date >= datetime('now', ':dureeVie days') order by date desc LIMIT :nbrPhrase")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Phrase> allAppli(@BindBean() Gestion g);
    
    //retourne le nombre total de phrases (utilisee dans StatistiqueViews/index.jsp methode get)
    @SqlQuery("select count(*) from phrase")
    int getAllCount();
    //retourne le nombre total de phrases terminees (utilisee dans StatistiqueViews/index.jsp methode get)
    @SqlQuery("select count(*) from phrase where terminee = 1")
    int getTermCount(@Bind("bool") boolean bool);
    
    //selectionne les phrases correspondant a la recherche (utilisee dans PhraseViews/index.jsp methode get)
    @SqlQuery("select phrase.* from phrase,utilisateur where phrase.id_user = utilisateur.id and (mail like :search "
            + "or besoin like :search or phrase like :search)")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Phrase> search(@Bind("search") String search);
    
    //selectionne les phrases triees par champs (utilisee dans PhraseViews/index.jsp methode get)
    
    @SqlQuery("select * from phrase order by besoin")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Phrase> orderBesoin();

    @SqlQuery("select * from phrase order by phrase")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Phrase> orderPhrase();
    
    @SqlQuery("select * from phrase order by consultee")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Phrase> orderConsultee();
    
    @SqlQuery("select * from phrase order by date DESC")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Phrase> orderDate();
    
    @SqlQuery("select * from phrase order by signalement DESC")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Phrase> orderSignalement();
    
    @SqlQuery("select phrase.* from phrase,utilisateur where phrase.id_user = utilisateur.id order by mail")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Phrase> orderMail();

    
    @SqlQuery("select count(*) from aide where date >= datetime('now', '-7 days')")
    @RegisterMapperFactory(BeanMapperFactory.class)
    int phrasePoste();
    
    //cree la table contenant les phrases
    @SqlUpdate("create table phrase(id INTEGER PRIMARY KEY AUTOINCREMENT, phrase CHAR(300) NOT NULL,besoin CHAR(300),date DATETIME,id_user INTEGER NOT NULL,categorie INTEGER,terminee BOOLEAN DEFAULT \"false\",signalement INTEGER, FOREIGN KEY(id) REFERENCES utilisateur(id) ON UPDATE CASCADE ON DELETE CASCADE)")
    void createPhraseTable();
    
    //creee la table d'association entre User et phrase indiquant qui a aidé une phrase
    @SqlUpdate("CREATE TABLE aide(id_repondant INTEGER,phrase CHAR(300),date DATETIME,FOREIGN KEY(id_repondant) REFERENCES utilisateur(id) ON UPDATE CASCADE ON DELETE CASCADE,FOREIGN KEY(phrase) REFERENCES phrase(phrase) ON UPDATE CASCADE ON DELETE CASCADE,PRIMARY KEY(id_repondant,phrase,date))")
    void createAideTable();
    
    void close();


}
