package be.iesca.colcinetFlights.dao;

import java.util.Date;
import java.util.List;

import be.iesca.colcinetFlights.domaine.Flight;

public interface FlightDao extends Dao {
	boolean addFlight(Flight vol);
	List<Flight> listFlightsWithDate(Date date);
	List<Flight> listFlightsWithoutDate();
	int getRowCountWithDate(Date date);
	int getRowCountWithoutDate();
}
