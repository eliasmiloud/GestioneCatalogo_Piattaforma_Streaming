package it.eliasmiloud;

import java.security.Provider.Service;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.Date;
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
					System.out.println("\n--- tutti i film: ---");
					ArrayList<Film> filmAll = filmService.getFilm();

					for (int i = 0; i < filmAll.size(); i++) {
						System.out.println(filmAll.get(i));
					}
				}
				case 2 -> {
					System.out.println("\n--- film disponibili: ---");

					ArrayList<Film> filAvailable = filmService.getFilmAvailable();
					for (int i = 0; i < filAvailable.size(); i++) {
						System.out.println(filAvailable.get(i));
					}
				}
				case 3 -> {
					System.out.println("\n--- Film ordinati per durata ---");

					ArrayList<Film> filmsOrdinati = filmService.getFilmOrdinatiPerDurata();

					for (int i = 0; i < filmsOrdinati.size(); i++) {
						System.out.println(filmsOrdinati.get(i));
					}
				}
				case 4 -> {
					System.out.println("\n--- film ordinati per voto  ---");
					ArrayList<Film> filmsOrdinatiVoto = filmService.getFilmOrdinatiPerVoto();

					for (int i = 0; i < filmsOrdinatiVoto.size(); i++) {
						System.out.println(filmsOrdinatiVoto.get(i));
					}

				}
				case 5 -> {

					System.out.print("Data uscita (yyyy-MM-dd): ");
					String input = scanner.nextLine();

					Date data_uscita = java.sql.Date.valueOf(input);
					ArrayList<Film> filmsOrdinatiAnno = filmService.getFilmOrdinatiAnno(data_uscita);

					for (int i = 0; i < filmsOrdinatiAnno.size(); i++) {
						System.out.println(filmsOrdinatiAnno.get(i));
					}

				}
				case 6 -> {
					// Assumendo sia il caso di inserimento
					scanner.nextLine(); // Pulizia buffer iniziale

					System.out.println("--- Inserimento Nuovo Film ---");

					System.out.print("id: ");
					int id = scanner.nextInt();
					scanner.nextLine();
					
					System.out.print("Titolo: ");
					String titolo = scanner.nextLine();

					System.out.print("Regista: ");
					String regista = scanner.nextLine();

					System.out.print("Genere: ");
					String genere = scanner.nextLine();

					System.out.print("Durata (minuti): ");
					int durata = scanner.nextInt();
					scanner.nextLine(); // Pulisci dopo int

					System.out.print("Data uscita (yyyy-MM-dd): ");
					String dataStr = scanner.nextLine();
					java.sql.Date dataUscita = java.sql.Date.valueOf(dataStr);

					System.out.print("Voto medio: ");
					double voto = scanner.nextDouble();

					System.out.print("Disponibile (true/false): ");
					boolean disponibile = scanner.nextBoolean();

					// Creazione dell'oggetto Film (o passaggio dei parametri)
					Film nuovoFilm = new Film(id, titolo, regista, genere, durata, dataUscita, voto, disponibile);
					nuovoFilm.setId(id);
					nuovoFilm.setTitolo(titolo);
					nuovoFilm.setRegista(regista);
					nuovoFilm.setGenere(genere);
					nuovoFilm.setDurata_minuti(durata);
					nuovoFilm.setData_uscita(dataUscita);
					nuovoFilm.setVoto_medio(voto);
					nuovoFilm.setDisponibilità(disponibile);

					filmService.insert(nuovoFilm);

					System.out.println("Film '" + titolo + "' inserito con successo!");
				}
				// --- Main.java ---

				case 7 -> {
				    System.out.println("--- Modifica Film ---");
				    System.out.print("Inserisci l'ID del film da modificare: ");
				    int idMod = scanner.nextInt();
				    scanner.nextLine(); // Pulisci buffer

				    // Chiediamo i nuovi dati
				    System.out.print("Nuovo Titolo: ");
				    String titolo = scanner.nextLine();
				    System.out.print("Nuovo Regista: ");
				    String regista = scanner.nextLine();
				    System.out.print("Nuovo Genere: ");
				    String genere = scanner.nextLine();
				    System.out.print("Nuova Durata: ");
				    int durata = scanner.nextInt();
				    scanner.nextLine(); 
				    System.out.print("Nuova Data (yyyy-MM-dd): ");
				    Date data = java.sql.Date.valueOf(scanner.nextLine());
				    System.out.print("Nuovo Voto: ");
				    double voto = scanner.nextDouble();
				    System.out.print("Disponibile (true/false): ");
				    boolean disp = scanner.nextBoolean();

				    Film fMod = new Film(idMod, titolo, regista, genere, durata, data, voto, disp);
				    fMod.setId(idMod); // Impostiamo l'ID per la clausola WHERE
				    fMod.setTitolo(titolo);
				    fMod.setRegista(regista);
				    fMod.setGenere(genere);
				    fMod.setDurata_minuti(durata);
				    fMod.setData_uscita(data);
				    fMod.setVoto_medio(voto);
				    fMod.setDisponibilità(disp);

				    try {
				        filmService.updateFilm(fMod);
				        System.out.println("Film aggiornato con successo!");
				    } catch (DatabaseException e) {
				        System.out.println("Errore: " + e.getMessage());
				    }
				}

				case 8 -> {
				    System.out.println("--- Cancellazione Film ---");
				    System.out.print("Inserisci l'ID del film da eliminare: ");
				    int idDel = scanner.nextInt();
				    
				    try {
				        filmService.deleteFilm(idDel);
				        System.out.println("Film eliminato definitivamente.");
				    } catch (DatabaseException e) {
				        System.out.println("Errore: " + e.getMessage());
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
