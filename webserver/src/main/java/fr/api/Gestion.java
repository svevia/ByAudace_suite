package fr.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Cette class offre la gestion de 2 variables :
 * -dureeVie qui est la duree de vie d'une phrase avant qu'elle disparaisse de l'appication
 * -nbrPhrases qui est le nombre de phrases au total accepté dans l'application
 * Ces variables sont ensuite stockés dans un fichier de config
 * @author asvevi
 *
 */
public class Gestion {
	
	private int dureeVie;
	private int nbrPhrases;
	
	public Gestion() {}
	
	public Gestion(int dureeVie, int nbrPhrases){
		this.dureeVie = dureeVie;
		this.nbrPhrases = nbrPhrases;
	}

	public int getDureeVie() {
		return dureeVie;
	}

	public void setDureeVie(int dureeVie) {
		this.dureeVie = dureeVie;
	}

	public int getNbrPhrases() {
		return nbrPhrases;
	}

	public void setNbrPhrases(int nbrPhrases) {
		this.nbrPhrases = nbrPhrases;
	}
	
	public void save(){
    	Properties prop = new Properties();
    	OutputStream output = null;
    	
    	try {
    		output = new FileOutputStream("src/main/resources/config.properties");
    		// set the properties value
    		prop.setProperty("nbrPhrases", getNbrPhrases()+"");
    		prop.setProperty("dureeVie", getDureeVie()+"");
    		// save properties to project root folder
    		prop.store(output, null);
    	} catch (IOException io) {
    		io.printStackTrace();
    	} finally {
    		if (output != null) {
    			try {
    				output.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}

    	}
	}
	
	public void init(){
		Properties prop = new Properties();
		InputStream input = null;
		File f = new File("src/main/resources/config.properties");
		if(!f.exists()){
			create();
		}
		try {

			input = new FileInputStream("src/main/resources/config.properties");

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			setDureeVie(Integer.parseInt(prop.getProperty("dureeVie")));
			setNbrPhrases(Integer.parseInt(prop.getProperty("nbrPhrases")));

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
		
		
	public void create(){
    	Properties prop = new Properties();
    	OutputStream output = null;
		File f = new File("src/main/resources/config.properties");
    	try {
			f.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	try {
    		output = new FileOutputStream("src/main/resources/config.properties");
    		
    		// set the properties value
    		prop.setProperty("nbrPhrases", 15+"");
    		prop.setProperty("dureeVie", 200+"");
    		// save properties to project root folder
    		prop.store(output, null);
    	} catch (IOException io) {
    		io.printStackTrace();
    	} finally {
    		if (output != null) {
    			try {
    				output.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}

    	}
	}
	
}
