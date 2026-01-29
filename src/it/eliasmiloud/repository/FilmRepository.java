package it.eliasmiloud.repository;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import it.eliasmiloud.configs.DatabaseConnection;
import it.eliasmiloud.exceptions.DatabaseException;
import it.eliasmiloud.model.Film;

public class FilmRepository {

	public ArrayList<Film> findAll(String query) throws DatabaseException {
		
		ArrayList<Film> film = new ArrayList<Film>();
		try {
			Connection connection = DatabaseConnection.getConnection();
			Statement stmt = connection.createStatement();

			ResultSet results = stmt.executeQuery(query);

			while (results.next()) {
				int id = results.getInt("id");
				String titolo = results.getString("titolo");
				String regista = results.getString("regista");
				String genere = results.getString("genere");
				int durata_minuti = results.getInt("durata_minuti");
				Date data_uscita = results.getDate("data_uscita");
				double voto_medio = results.getDouble("voto_medio");
				boolean disponibilità = results.getBoolean("disponibilità");

				Film films = new Film(id, titolo, regista, genere, durata_minuti, data_uscita, voto_medio,
						disponibilità);
				film.add(films);
			}
		} catch (SQLException e) {
			throw new DatabaseException("Errore db");
		}
		return film;
	}
	
	public int deleteById(int id) throws DatabaseException {
		String query = "DELETE FROM film WHERE id = ?";
		int affectedRows = 0;
		try {
			Connection connection = DatabaseConnection.getConnection();
			PreparedStatement stmt = connection.prepareStatement(query);
			
			stmt.setInt(1, id);
			affectedRows = stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new DatabaseException("Errore db");
		}
		return affectedRows;
	}
		
		public void insert(String titolo) throws DatabaseException {
			String query = "INSERT INTO film (titolo) VALUES (?) ";
			
			try {
				Connection connection = DatabaseConnection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(query);
				
				stmt.setString(1, titolo);
				stmt.executeUpdate();
				
			} catch (SQLException e) {
				throw new DatabaseException("Errore db");
			}
			
		}
		public int update(String titolo, int id) throws DatabaseException{
			String query = "UPDATE film SET titolo= ? WHERE id=?";
			int affectedRows = 0;
			
			try {
				Connection connection = DatabaseConnection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(query);
				
				stmt.setInt(1, id);
				stmt.setString(2, titolo);
				affectedRows = stmt.executeUpdate();
				
			} catch (SQLException e) {
				throw new DatabaseException("Errore db");
			}
			return affectedRows;
		}
	
	
	
	
}
