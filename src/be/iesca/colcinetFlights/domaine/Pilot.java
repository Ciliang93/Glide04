package be.iesca.colcinetFlights.domaine;

public class Pilot {
	private int idPilot;
	private String emailPilot;
	private String nomPilot;
	private String prenomPilot;
	private String adressePilot;
	private String noGsmPilot;
	private double soldePilot;

	//Constructeur avec id (quand il est récupéré en bd)
	public Pilot(int id, String email, String nom, String prenom, String adresse, String noGsm, double solde) {
		this.idPilot = id;
		this.emailPilot = email;
		this.nomPilot = nom;
		this.prenomPilot = prenom;
		this.adressePilot = adresse;
		this.noGsmPilot = noGsm;
		this.soldePilot = solde;
	}
	
	//Constructeur sans id (quand il est créé par l'utilisateur)
	public Pilot(String emailPilot, String nomPilot, String prenomPilot, String adressePilot, String noGsmPilot, double soldePilot) {
		this.emailPilot = emailPilot;
		this.nomPilot = nomPilot;
		this.prenomPilot = prenomPilot;
		this.adressePilot = adressePilot;
		this.noGsmPilot = noGsmPilot;
		this.soldePilot = soldePilot;
	}
	
	public Pilot(Pilot pilot){
		this.idPilot = pilot.idPilot;
		this.emailPilot = pilot.emailPilot;
		this.nomPilot = pilot.nomPilot;
		this.prenomPilot = pilot.prenomPilot;
		this.adressePilot = pilot.adressePilot;
		this.noGsmPilot = pilot.noGsmPilot;
		this.soldePilot = pilot.soldePilot;
	}

	public String getEmailPilot() {
		return emailPilot;
	}

	public void setEmailPilot(String emailPilot) {
		this.emailPilot = emailPilot;
	}

	public String getNomPilot() {
		return nomPilot;
	}

	public void setNomPilot(String nomPilot) {
		this.nomPilot = nomPilot;
	}

	public String getPrenomPilot() {
		return prenomPilot;
	}

	public void setPrenomPilot(String prenomPilot) {
		this.prenomPilot = prenomPilot;
	}

	public String getAdressePilot() {
		return adressePilot;
	}

	public void setAdressePilot(String adressePilot) {
		this.adressePilot = adressePilot;
	}

	public String getNoGsmPilot() {
		return noGsmPilot;
	}

	public void setNoGsmPilot(String noGsmPilot) {
		this.noGsmPilot = noGsmPilot;
	}

	public double getSoldePilot() {
		return soldePilot;
	}

	public void setSoldePilot(double soldePilot) {
		this.soldePilot = soldePilot;
	}

	public int getIdPilot() {
		return idPilot;
	}

	public void setIdPilot(int idPilot) {
		this.idPilot = idPilot;
	}
	
	@Override
	public String toString() {
		return this.nomPilot + " " + this.prenomPilot;
	}

}
