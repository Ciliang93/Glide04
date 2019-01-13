package be.iesca.colcinetFlights.domaine;

public class Glide {
	private int idGlide;
	private String typeGlide;
	private double prixHoraireGlide;
	private double prixFixeGlide;

	//Constructeur avec id, utilisé lors de la récupération en bd
	public Glide(int id, String type, double prixHoraire, double prixFixe) {
		this.idGlide = id;
		this.typeGlide = type;
		this.prixHoraireGlide = prixHoraire;
		this.prixFixeGlide = prixFixe;
	}
	
	public Glide(String typeGlide, double prixHoraireGlide, double prixFixeGlide) {
		this.typeGlide = typeGlide;
		this.prixHoraireGlide = prixHoraireGlide;
		this.prixFixeGlide = prixFixeGlide;
	}

	public String getTypeGlide() {
		return typeGlide;
	}

	public void setTypeGlide(String typeGlide) {
		this.typeGlide = typeGlide;
	}

	public double getPrixHoraireGlide() {
		return prixHoraireGlide;
	}

	public void setPrixHoraireGlide(double prixHoraireGlide) {
		this.prixHoraireGlide = prixHoraireGlide;
	}

	public double getPrixFixeGlide() {
		return prixFixeGlide;
	}

	public void setPrixFixeGlide(double prixFixeGlide) {
		this.prixFixeGlide = prixFixeGlide;
	}
	
	public int getIdGlide() {
		return idGlide;
	}

	public void setIdGlide(int idGlide) {
		this.idGlide = idGlide;
	}
	
	@Override
	public String toString() {
		return this.typeGlide;
	}
}
