package fr.iutinfo.skeleton.api;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.spi.DirStateFactory.Result;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.sqlite.SQLiteDataSource;

/**
 * @author maugern
 * @date 23 mars 2016
 */

public class DBRequestTest {
	private static final Object[] NULL = null;
	private Connection c = null;
	private Statement stmt = null;
	private ResultSet rs;

	@Before
	public void init() {

		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.println("Erreur driver jdbc !");
			e.printStackTrace();
		}
		try {
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			stmt = c.createStatement();
		} catch (SQLException e) {
			System.err.println("Erreur ouverture DB !");
			e.printStackTrace();
		}

	}

	@Test
	public void read_fist_user_in_database() {
		String role = null;
		try {
			rs = stmt.executeQuery("SELECT * FROM utilisateur LIMIT 1");
			while ( rs.next() ) {
				role = rs.getString("role");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Erreur de requête !");
		}
		assertEquals(role, NULL);
	}
	
	@After
	public void close_all() throws SQLException {
		rs.close();
		stmt.close();
		c.close();
	}
}


