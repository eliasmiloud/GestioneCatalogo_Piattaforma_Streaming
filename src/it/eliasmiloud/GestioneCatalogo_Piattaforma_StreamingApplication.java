package it.eliasmiloud;

import java.util.ArrayList;
import java.util.Scanner;

import it.eliasmiloud.model.Film;
import it.eliasmiloud.repository.FilmRepository;

public class GestioneCatalogo_Piattaforma_StreamingApplication {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean isRunning = true;
		String query;
		ArrayList<Film> film = new ArrayList<Film>();
		FilmRepository filmrepository = new FilmRepository();
		
		switch (scelta) {
		case 1-> {
			query = "SELECT * FROM film";
			film = filmrepository.findAll(query);
		}
		}
	

	}

}
