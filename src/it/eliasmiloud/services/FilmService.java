package it.eliasmiloud.services;

import java.sql.Date;
import java.util.ArrayList;

import it.eliasmiloud.repository.FilmRepository;
import it.eliasmiloud.constants.MessageConstant;
import it.eliasmiloud.exceptions.DatabaseException;
import it.eliasmiloud.exceptions.EmptyResultException;
import it.eliasmiloud.exceptions.RecordNotFoundException;

import it.eliasmiloud.model.Film;

public class FilmService {

	private FilmRepository filmRepository = new FilmRepository();

	public ArrayList<Film> getFilm() throws EmptyResultException, DatabaseException {

		ArrayList<Film> film = this.filmRepository.findAll();

		if (film.isEmpty()) {
			throw new EmptyResultException(MessageConstant.EMPTY_RESULT);
		}
		return film;
	}

	public ArrayList<Film> getFilmAvailable() throws DatabaseException, EmptyResultException {
		ArrayList<Film> film = this.filmRepository.findAvailable();
		if (film.isEmpty()) {
			throw new EmptyResultException(MessageConstant.RECORD_NOT_FOUND);
		}
		return film;
	}

	public ArrayList<Film> getFilmOrdinatiPerDurata() throws DatabaseException, EmptyResultException {
		ArrayList<Film> films = this.filmRepository.orderBydurata();
		if (films.isEmpty()) {
			throw new EmptyResultException(MessageConstant.EMPTY_RESULT);
		}
		return films;
	}

	public ArrayList<Film> getFilmOrdinatiPerVoto() throws DatabaseException, EmptyResultException {
		ArrayList<Film> films = this.filmRepository.orderByVoto();
		if (films.isEmpty()) {
			throw new EmptyResultException(MessageConstant.EMPTY_RESULT);
		}
		return films;
	}

	public ArrayList<Film> getFilmOrdinatiAnno(Date data_uscita) throws DatabaseException, EmptyResultException {
		ArrayList<Film> films = this.filmRepository.orderByAnno(data_uscita);
		if (films.isEmpty()) {
			throw new EmptyResultException(MessageConstant.EMPTY_RESULT);
		}
		return films;
	}

	public void insert(Film nuovoFilm) throws RecordNotFoundException, DatabaseException {
		this.filmRepository.insert(nuovoFilm);

	}
	// --- FilmService.java ---

	public void updateFilm(Film film) throws DatabaseException {
		// Qui potresti aggiungere controlli, es: se il film esiste prima di modificarlo
		this.filmRepository.update(film);
	}

	public void deleteFilm(int id) throws DatabaseException {
		this.filmRepository.delete(id);
	}

	private void checkIdexists(int affectedRows, int id) throws RecordNotFoundException {
		if (affectedRows == 0) {
			throw new RecordNotFoundException(String.format(MessageConstant.RECORD_NOT_FOUND, id));
		}
	}

}
