package be.iesca.colcinetFlights.usecaseimpl;

import java.util.List;
import java.util.Date;
import be.iesca.colcinetFlights.dao.FlightDao;
import be.iesca.colcinetFlights.daoimpl.DaoFactory;
import be.iesca.colcinetFlights.domaine.Bundle;
import be.iesca.colcinetFlights.domaine.Flight;
import be.iesca.colcinetFlights.usecase.GestionFlights;

public class GestionFlightsImpl implements GestionFlights {

	private FlightDao flightDao;
	
	public GestionFlightsImpl() {
		this.flightDao = (FlightDao) DaoFactory.getInstance().getDaoImpl(FlightDao.class);
	}
	
	@Override
	public void addFlight(Bundle bundle) {
		boolean success = false;
		String msg = "";
		Flight f = (Flight) bundle.get(Bundle.FLIGHT);
		if (f.getDateFlight() == null) {
			msg = "Ajout impossible. Date manquante";
		} else if (f.getDureeFlight() <= 0) {
			msg = "Ajout impossible. Duree nulle ou negative";
		} else if (f.getPrixFlight() <= 0) {
			msg = "Ajout impossible. Prix éroné";
		} else if (f.getPilote() == null) {
			msg = "Ajout impossible. Pilote manquant";
		} else if (f.getPlaneur() == null) {
			msg = "Ajout impossible. Planeur manquant";
		} else {
			success = this.flightDao.addFlight(f);
			if (success) {
				msg = "Ajout realise avec succes";
			} else {
				msg = "L'ajout n'a pas pu etre realise";
			}
		}
		bundle.put(Bundle.OPERATION_SUCCESS, success);
		bundle.put(Bundle.FLIGHT, f);
		bundle.put(Bundle.MESSAGE, msg);
	}

	@Override
	public void listFlightsWithDate(Bundle bundle) {
		boolean success = false;
		String message = "";
		List<Flight> listOfFlights = null;
		
		listOfFlights = this.flightDao.listFlightsWithDate((Date) bundle.get(Bundle.DATE));
		if(listOfFlights == null) {
			message = "La liste est vide";
		} else {
			success = true;
			message = "La liste contient les vols a la date demandée";
		}
		
		bundle.put(Bundle.OPERATION_SUCCESS, success);
		bundle.put(Bundle.MESSAGE, message);
		bundle.put(Bundle.LIST, listOfFlights);
	}

	@Override
	public void listFlightsWithoutDate(Bundle bundle) {
		boolean success = false;
		String message = "";
		List<Flight> listOfFlights = null;
		
		listOfFlights = this.flightDao.listFlightsWithoutDate();
		if(listOfFlights == null) {
			message = "La liste est vide";
		} else {
			success = true;
			message = "La liste contient tous les vols";
		}
		
		bundle.put(Bundle.OPERATION_SUCCESS, success);
		bundle.put(Bundle.MESSAGE, message);
		bundle.put(Bundle.LIST, listOfFlights);
	}	
	
	@Override
	public void getRowCountWithDate(Bundle bundle) {
		boolean success = true;
		int rowCount = this.flightDao.getRowCountWithDate((Date)bundle.get(Bundle.DATE));
		String message = "Il y a " + rowCount + " pilotes enregistrés";
		
		bundle.put(Bundle.OPERATION_SUCCESS, success);
		bundle.put(Bundle.ROWCOUNT, rowCount);
		bundle.put(Bundle.MESSAGE, message);
	}
	
	@Override
	public void getRowCountWithoutDate(Bundle bundle) {
		boolean success = true;
		int rowCount = this.flightDao.getRowCountWithoutDate();
		String message = "Il y a " + rowCount + " pilotes enregistrés";
		
		bundle.put(Bundle.OPERATION_SUCCESS, success);
		bundle.put(Bundle.ROWCOUNT, rowCount);
		bundle.put(Bundle.MESSAGE, message);
	}
	
}
