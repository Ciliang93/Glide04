package be.iesca.colcinetFlights.domaine;

import java.util.Date;

public class Flight {
	private int idFlight;
	private Date dateFlight;
	private int dureeFlight;
	private double prixFlight;
	private Glide planeur;
	private Pilot pilote;

	public Flight(Date dateFlight, int dureeFlight, double prixFlight, Glide planeur, Pilot pilote) {
		super();
		this.dateFlight = dateFlight;
		this.dureeFlight = dureeFlight;
		this.prixFlight = prixFlight;
		this.planeur = planeur;
		this.pilote = pilote;
	}

	public Date getDateFlight() {
		return dateFlight;
	}

	public void setDateFlight(Date dateFlight) {
		this.dateFlight = dateFlight;
	}

	public int getDureeFlight() {
		return dureeFlight;
	}

	public void setDureeFlight(int dureeFlight) {
		this.dureeFlight = dureeFlight;
	}

	public double getPrixFlight() {
		return prixFlight;
	}

	public void setPrixFlight(double prixFlight) {
		this.prixFlight = prixFlight;
	}
	
	public int getIdFlight() {
		return idFlight;
	}

	public void setIdFlight(int idFlight) {
		this.idFlight = idFlight;
	}

	public Glide getPlaneur() {
		return planeur;
	}

	public void setPlaneur(Glide planeur) {
		this.planeur = planeur;
	}

	public Pilot getPilote() {
		return pilote;
	}

	public void setPilote(Pilot pilote) {
		this.pilote = pilote;
	}
	
	@Override
	public String toString() {
		return "[Date : " + this.dateFlight + ", Durée : " + this.dureeFlight + ", Prix : " + this.prixFlight + ", Planeur : " + this.planeur.toString() + ", Pilote : " + this.pilote.toString() + "]";
	}

}