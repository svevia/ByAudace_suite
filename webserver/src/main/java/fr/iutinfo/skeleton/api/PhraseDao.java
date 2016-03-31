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
    @SqlUpdate("insert into phrase_metier (phrase, besoin, mail, terminee, consultee) values (:phrase, :besoin, :mail, :terminee, :consultee)")
    @GetGeneratedKeys
    int insert(@BindBean() Phrase phrase);
    //selection d'une phrase en fonction de son intitule "phrase" (methode get)
    @SqlQuery("select * from phrase_metier where phrase = :phrase")
    @RegisterMapperFactory(BeanMapperFactory.class)
    Phrase findByPhrase(@Bind("phrase") String phrase);
    //suppression d'une phrase en fonction de son intitule "phrase" (utilisee dans PhraseViews/index.jsp methode delete)
    @SqlUpdate("delete from phrase_metier where phrase = :phrase")
    void delete(@Bind("phrase") String phrase);
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
    
    @SqlQuery("select< * from phrase_metier order by consultee")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Phrase> orderConsultee();
    
    @SqlQuery("select * from phrase_metier order by mail")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Phrase> orderMail();
    
    @SqlQuery("select * from phrase_metier order by terminee")
    @RegisterMapperFactory(BeanMapperFactory.class)
    List<Phrase> orderTerminee();
    
    void close();
}
