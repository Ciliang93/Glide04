package be.iesca.colcinetFlights.controleur;

import be.iesca.colcinetFlights.domaine.Bundle;
import be.iesca.colcinetFlights.usecase.GestionFlights;
import be.iesca.colcinetFlights.usecase.GestionGlides;
import be.iesca.colcinetFlights.usecase.GestionPilots;
import be.iesca.colcinetFlights.usecaseimpl.GestionFlightsImpl;
import be.iesca.colcinetFlights.usecaseimpl.GestionGlidesImpl;
import be.iesca.colcinetFlights.usecaseimpl.GestionPilotsImpl;

public class GestionnaireUseCases implements GestionPilots, GestionFlights, GestionGlides {
	private static final GestionnaireUseCases INSTANCE = new GestionnaireUseCases();
	
	private GestionPilots gestionPilots;
	private GestionFlights gestionFlights;
	private GestionGlides gestionGlide;
	
	
	public static GestionnaireUseCases getInstance() {
		return INSTANCE;
	}
	
	private GestionnaireUseCases() {
		this.gestionPilots = new GestionPilotsImpl();
		this.gestionFlights = new GestionFlightsImpl();
		this.gestionGlide = new GestionGlidesImpl();
	}
	
	@Override
	public void getPilot(Bundle bundle) {
		this.gestionPilots.getPilot(bundle);
	}

	@Override
	public void addPilot(Bundle bundle) {
		this.gestionPilots.addPilot(bundle);
	}

	@Override
	public void getListSortedByName(Bundle bundle) {
		this.gestionPilots.getListSortedByName(bundle);
	}

	@Override
	public void getListSortedByBalance(Bundle bundle) {
		this.gestionPilots.getListSortedByBalance(bundle);
	}

	@Override
	public void modifyPilot(Bundle bundle) {
		this.gestionPilots.modifyPilot(bundle);
	}

	@Override
	public void getRowCountByName(Bundle bundle) {
		this.gestionPilots.getRowCountByName(bundle);		
	}
	
	public void getRowCountByBalance(Bundle bundle) {
		this.gestionPilots.getRowCountByBalance(bundle);
	}
	
	@Override
	public void addFlight(Bundle bundle) {
		this.gestionFlights.addFlight(bundle);
	}
	
	@Override
	public void listFlightsWithDate(Bundle bundle) {
		this.gestionFlights.listFlightsWithDate(bundle);
	}

	@Override
	public void listFlightsWithoutDate(Bundle bundle) {
		this.gestionFlights.listFlightsWithoutDate(bundle);
	}
	
	@Override
	public void listAllGlide(Bundle bundle) {
		this.gestionGlide.listAllGlide(bundle);
	}

	@Override
	public void getGlide(Bundle bundle) {
		this.gestionGlide.getGlide(bundle);
	}

	@Override
	public void getRowCountWithDate(Bundle bundle) {
		this.gestionFlights.getRowCountWithDate(bundle);
		
	}

	@Override
	public void getRowCountWithoutDate(Bundle bundle) {
		this.gestionFlights.getRowCountWithoutDate(bundle);
	}
}
