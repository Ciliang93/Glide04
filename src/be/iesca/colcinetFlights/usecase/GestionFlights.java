package be.iesca.colcinetFlights.usecase;

import be.iesca.colcinetFlights.domaine.Bundle;

public interface GestionFlights {
	void addFlight(Bundle bundle);
	void listFlightsWithDate(Bundle bundle);
	void listFlightsWithoutDate(Bundle bundle);
	void getRowCountWithDate(Bundle bundle);
	void getRowCountWithoutDate(Bundle bundle);
}
