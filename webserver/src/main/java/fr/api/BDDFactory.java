package fr.api;

import org.skife.jdbi.v2.DBI;
import org.sqlite.SQLiteDataSource;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

/**
 * gestion de la BDD : propose une fonction
 * et dans la cas ou la base n'existe pas encore elle sera crée
 * la base de donnée est en SQLite
 * @author asvevi
 *
 */

@Singleton
public class BDDFactory {
    private static DBI dbi = null;

    /**
     * getDBI() qui retourne la connection à la base de donnée
     * si la base n'existe pas elle sera crée
     * @return
     */
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
    		init();//creation des tables et des users par dÃ©faut
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
		User admin = new User("admin", "admin", "admin", "admin", "IUTAudace2016", "admin","0000");
		admin.resetPasswordHash();
		User root = new User("root","root","root","root","IUTAudace2016","root","0000");
		root.resetPasswordHash();
		dao.insert(admin);
		dao.insert(root);
		PhraseDao phrase = BDDFactory.getDbi().open(PhraseDao.class);
		phrase.createPhraseTable();
		phrase.createAideTable();
		Gestion g = new Gestion();
		g.setDureeVie(15);
		g.setNbrPhrases(200);
		g.save();
	}

}
