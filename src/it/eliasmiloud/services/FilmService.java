package it.eliasmiloud.services;

import java.util.ArrayList;

import it.eliasmiloud.repository.FilmRepository;
import it.eliasmiloud.constants.MessageConstant;
import it.eliasmiloud.exceptions.DatabaseException;
import it.eliasmiloud.exceptions.EmptyResultException;
import it.eliasmiloud.exceptions.RecordNotFoundException;

import it.eliasmiloud.model.Film;
public class FilmService {
	
	private FilmRepository filmRepository = new FilmRepository();
	public ArrayList<Film> getFilm() throws EmptyResultException, DatabaseException{
		
		ArrayList<Film> film = this.filmRepository.findAll();
		
		if(film.isEmpty()) {
			throw new EmptyResultException(MessageConstant.EMPTY_RESULT);
		}
		return film;
	}
	
	public ArrayList<Film> getFilmOrdinatiPerDurata() throws DatabaseException, EmptyResultException {
	    ArrayList<Film> films = this.filmRepository.orderBydurata();
	    if (films.isEmpty()) {
	        throw new EmptyResultException("Nessun film trovato");
	    }
	    return films;
	}
	
	public void deleteById(int id) throws RecordNotFoundException, DatabaseException {
		int affectedRows = this.filmRepository.deleteById(id);
		this.checkIdexists(affectedRows, id);
	}
	
	public void insert(String name) throws RecordNotFoundException, DatabaseException  {
		this.filmRepository.insert(name);
	
	}
	public void update(String name, int id) throws RecordNotFoundException, DatabaseException {
		int affectedRows = this.filmRepository.update(name, id);
		this.checkIdexists(affectedRows, id);
	}
	
	private void checkIdexists(int affectedRows, int id) throws RecordNotFoundException {
		if (affectedRows == 0) {
			throw new RecordNotFoundException(String.format(MessageConstant.RECORD_NOT_FOUND, id));
		}
	}
	
}
