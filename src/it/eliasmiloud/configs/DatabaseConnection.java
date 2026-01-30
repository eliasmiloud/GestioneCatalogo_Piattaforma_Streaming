package it.eliasmiloud.configs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
	private static final String HOST = "localhost";
	private static final String PORT = "3010";
	private static final String USER = "root";
	private static final String PASS = "sechiaccheratedellaltrovifacciospostare";
	private static final String NAME = "streaming_platform_db";
		
	private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + NAME;
	

	public static Connection getConnection() throws SQLException {
		
		return DriverManager.getConnection(URL,USER,PASS);
		
	}
}
