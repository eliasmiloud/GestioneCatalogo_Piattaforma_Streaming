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

	public ArrayList<Film> findAll() throws DatabaseException {
		String query = "SELECT * FROM film";
		return this.ritonaLista(query);

	}

	public ArrayList<Film> findAvailable() throws DatabaseException {
		String query = "SELECT * FROM film WHERE disponibilità= true";
		return this.ritonaLista(query);

	}

	public ArrayList<Film> orderBydurata() throws DatabaseException {
		String query = "SELECT * FROM film ORDER BY durata_minuti DESC";
		return this.ritonaLista(query);

	}

	public ArrayList<Film> orderByVoto() throws DatabaseException {
		String query = "SELECT * FROM film ORDER BY voto_medio";
		return this.ritonaLista(query);
	}

	public ArrayList<Film> orderByAnno(Date data_uscita) throws DatabaseException {
		ArrayList<Film> listaFilm = new ArrayList<>();
		String query = "SELECT * FROM film WHERE YEAR (data_uscita) > ?";

		try {
			Connection connection = DatabaseConnection.getConnection();
			PreparedStatement stmt = connection.prepareStatement(query);

			stmt.setDate(1, data_uscita);
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new DatabaseException("Errore db");
		}
		return listaFilm;
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

	public void insert(Film nuovoFilm) throws DatabaseException {
		// 1. Prepariamo la query con tutti i campi (l'ID di solito è AUTO_INCREMENT,
		// quindi lo escludiamo)
		String query = "INSERT INTO film (titolo, regista, genere, durata_minuti, data_uscita, voto_medio, disponibilità) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement stmt = connection.prepareStatement(query)) {

			// 2. Mappiamo i valori dell'oggetto nuovoFilm sui parametri '?'
			stmt.setString(1, nuovoFilm.getTitolo());
			stmt.setString(2, nuovoFilm.getRegista());
			stmt.setString(3, nuovoFilm.getGenere());
			stmt.setInt(4, nuovoFilm.getDurata_minuti());

			// Attenzione: usiamo java.sql.Date
			stmt.setDate(5, nuovoFilm.getData_uscita());

			stmt.setDouble(6, nuovoFilm.getVoto_medio());
			stmt.setBoolean(7, nuovoFilm.isDisponibilità());

			// 3. Eseguiamo l'aggiornamento
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new DatabaseException("Errore db");
		}
	}

	// --- FilmRepository.java ---

	// MODIFICA
	public void update(Film film) throws DatabaseException {
	    String query = "UPDATE film SET titolo=?, regista=?, genere=?, durata_minuti=?, data_uscita=?, voto_medio=?, disponibilità=? WHERE id=?";
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	        
	        stmt.setString(1, film.getTitolo());
	        stmt.setString(2, film.getRegista());
	        stmt.setString(3, film.getGenere());
	        stmt.setInt(4, film.getDurata_minuti());
	        stmt.setDate(5, film.getData_uscita());
	        stmt.setDouble(6, film.getVoto_medio());
	        stmt.setBoolean(7, film.isDisponibilità());
	        stmt.setInt(8, film.getId()); // Fondamentale per il WHERE

	        int rows = stmt.executeUpdate();
	        if (rows == 0) throw new DatabaseException("Film con ID " + film.getId() + " non trovato.");
	        
	    } catch (SQLException e) {
	        throw new DatabaseException("Errore durante l'aggiornamento: " + e.getMessage());
	    }
	}

	// CANCELLAZIONE
	public void delete(int id) throws DatabaseException {
	    String query = "DELETE FROM film WHERE id = ?";
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {
	        
	        stmt.setInt(1, id);
	        int rows = stmt.executeUpdate();
	        if (rows == 0) throw new DatabaseException("Impossibile cancellare: film non trovato.");
	        
	    } catch (SQLException e) {
	        throw new DatabaseException("Errore durante la cancellazione: " + e.getMessage());
	    }
	}

	private ArrayList<Film> ritonaLista(String query) throws DatabaseException {
		ArrayList<Film> listaFilm = new ArrayList<>();
		try (Connection connection = DatabaseConnection.getConnection();
				Statement stmt = connection.createStatement();
				ResultSet results = stmt.executeQuery(query)) {

			while (results.next()) {
				// Recupera TUTTI i campi necessari per l'oggetto Film
				int id = results.getInt("id");
				String titolo = results.getString("titolo");
				String regista = results.getString("regista");
				String genere = results.getString("genere");
				int durata_minuti = results.getInt("durata_minuti");
				Date data_uscita = results.getDate("data_uscita");
				double voto_medio = results.getDouble("voto_medio");
				boolean disponibilità = results.getBoolean("disponibilità");

				// Crea l'oggetto Film completo
				Film f = new Film(id, titolo, regista, genere, durata_minuti, data_uscita, voto_medio, disponibilità);
				listaFilm.add(f);
			}
		} catch (SQLException e) {
			// Rilancia l'eccezione personalizzata
			throw new DatabaseException("Errore durante la lettura dal database");
		}
		return listaFilm;
	}

}
