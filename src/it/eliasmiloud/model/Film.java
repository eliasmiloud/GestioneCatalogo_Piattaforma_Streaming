package it.eliasmiloud.model;

import java.sql.Date;

public class Film {

	private int id;
	private String titolo;
	private String regista;
	private String genere;
	private int durata_minuti;
	private Date data_uscita;
	private double voto_medio;
	private boolean disponibilità;

	public Film(int id, String titolo, String regista, String genere, int durata_minuti, Date data_uscita,
			double voto_medio, boolean disponibilità) {
		super();
		this.id = id;
		this.titolo = titolo;
		this.regista = regista;
		this.genere = genere;
		this.durata_minuti = durata_minuti;
		this.data_uscita = data_uscita;
		this.voto_medio = voto_medio;
		this.disponibilità = disponibilità;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getRegista() {
		return regista;
	}

	public void setRegista(String regista) {
		this.regista = regista;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public int getDurata_minuti() {
		return durata_minuti;
	}

	public void setDurata_minuti(int durata_minuti) {
		this.durata_minuti = durata_minuti;
	}

	public Date getData_uscita() {
		return data_uscita;
	}

	public void setData_uscita(Date data_uscita) {
		this.data_uscita = data_uscita;
	}

	public double getVoto_medio() {
		return voto_medio;
	}

	public void setVoto_medio(double voto_medio) {
		this.voto_medio = voto_medio;
	}

	public boolean isDisponibilità() {
		return disponibilità;
	}

	public void setDisponibilità(boolean disponibilità) {
		this.disponibilità = disponibilità;
	}

	@Override
	public String toString() {
		return "Film [id=" + id + ", titolo=" + titolo + ", regista=" + regista + ", genere=" + genere
				+ ", durata_minuti=" + durata_minuti + ", data_uscita=" + data_uscita + ", voto_medio=" + voto_medio
				+ ", disponibilità=" + disponibilità + "]";
	}

	
}
