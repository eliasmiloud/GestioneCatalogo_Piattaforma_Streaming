package it.eliasmiloud;

import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import it.eliasmiloud.exceptions.DatabaseException;
import it.eliasmiloud.exceptions.EmptyResultException;
import it.eliasmiloud.exceptions.RecordNotFoundException;
import it.eliasmiloud.model.Film;
import it.eliasmiloud.repository.FilmRepository;
import it.eliasmiloud.services.FilmService;

public class GestioneCatalogo_Piattaforma_StreamingApplication {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean isRunning = true;
		String query;
		ArrayList<Film> film = new ArrayList<Film>();
		FilmService filmService = new FilmService();
		while (isRunning) {
			System.out.println("--MENU--");
			System.out.println("1) Mostra tutti i film:");
			System.out.println("2) Mostra film disponibili:");
			System.out.println("3) Mostra film ordinati per durata");
			System.out.println("4) Mostra film ordinati per voto ");
			System.out.println("5) Mostra film usciti dopo un certo anno ");
			System.out.println("6) Aggiungere un nuovo film  ");
			System.out.println("7) Modificare un film esistente ");
			System.out.println("8) Cancellare un film esistente ");
			System.out.println("0) Esci");

			try {

				int scelta = scanner.nextInt();
				scanner.nextLine();
				

				switch (scelta) {
					case 1 -> {
						
						ArrayList<Film>
	
						for (int i = 0; i < film.size(); i++) {
							System.out.println(film.get(i));
						}
						
					}
					case 3 -> {
					    System.out.println("\n--- Film ordinati per durata ---");
			
					    ArrayList<Film> filmsOrdinati = filmService.getFilmOrdinatiPerDurata();
					    
					    for (int i = 0; i < filmsOrdinati.size(); i++) {
							System.out.println(filmsOrdinati.get(i));
						}   
					}
					case 0 -> {
						isRunning = false;
					}
					default -> System.out.println("Opzione non valida");
				}

			} catch (EmptyResultException | DatabaseException e) {
				System.out.println(e.getMessage());
			} catch (InputMismatchException e) {
				System.out.println("Input non valido");
				scanner.nextLine();
			} catch (Exception e) {
				System.out.println("Errore");
			}
		}
		scanner.close();
	}

}
