package fr.api;

import org.skife.jdbi.v2.DBI;
import org.sqlite.SQLiteDataSource;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

@Singleton
public class BDDFactory {
    private static DBI dbi = null;

    public static DBI getDbi() {
    	if(!new File("data.db").exists()){//si la BDD n'existe pas
    		try {
				new File("data.db").createNewFile();//creation du fichier vide
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		//chargement de la BDD
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:data.db");
            dbi = new DBI(ds);
    		init();//creation des tables et des users par défaut
    	}
    	
    	
        if(dbi == null) {//chargement de la BDD
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:data.db");
            dbi = new DBI(ds);
        }
        return dbi;
    }

    /**
     * Cree les tables de la BDD ainsi que les users de base
     */
	private static void init() {
		UserDao user = BDDFactory.getDbi().open(UserDao.class);
		user.createUserTable();
		user.createCategorieUserTable();
	    UserDao dao = BDDFactory.getDbi().open(UserDao.class);
		User admin = new User("admin", "admin", "admin", "admin", "admin", "admin","0000");
		admin.resetPasswordHash();
		User test = new User("test", "test", "test", "test", "test", "user","0000");
		test.resetPasswordHash();
		User root = new User("root","root","root","root","root","root","0000");
		root.resetPasswordHash();
		dao.insert(admin);
		dao.insert(test);
		dao.insert(root);
		PhraseDao phrase = BDDFactory.getDbi().open(PhraseDao.class);
		phrase.createPhraseTable();
		phrase.createAideTable();
		new PhraseResource().createPhrase(new Phrase("phrase 1","besoin 1", 2, 1));
		new PhraseResource().createPhrase(new Phrase("phrase 2","besoin 2", 1, 3));
		new PhraseResource().createPhrase(new Phrase("phrase 3","besoin 2", 1, 1));
		new PhraseResource().createPhrase(new Phrase("phrase 4","besoin 2", 1, 2));
		new PhraseResource().createPhrase(new Phrase("phrase 5","besoin 2", 3, 1));
		Gestion g = new Gestion();
		g.setDureeVie(15);
		g.setNbrPhrase(200);
		g.save();
	}

}
