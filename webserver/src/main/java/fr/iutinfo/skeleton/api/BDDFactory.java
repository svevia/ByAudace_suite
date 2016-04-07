package fr.iutinfo.skeleton.api;

import org.skife.jdbi.v2.DBI;
import org.sqlite.SQLiteDataSource;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

@Singleton
public class BDDFactory {
    private static DBI dbi = null;

    public static DBI getDbi() {
    	if(!new File("data.db").exists()){
    		try {
				new File("data.db").createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:data.db");
            dbi = new DBI(ds);
    		init();
    	}
    	
    	
        if(dbi == null) {//chargement de la BDD
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl("jdbc:sqlite:data.db");
            dbi = new DBI(ds);
        }
        return dbi;
    }

	private static void init() {
		UserDao user = BDDFactory.getDbi().open(UserDao.class);
		user.createUserTable();
		user.insert(new User("admin", "admin", "admin", "admin", "admin", "admin"));
		user.insert(new User("test", "test", "test", "test", "test", "user"));
		PhraseDao phrase = BDDFactory.getDbi().open(PhraseDao.class);
		phrase.createPhraseTable();
		phrase.createAideTable();
	}

}
