package be.iesca.colcinetFlights.usecase;

import be.iesca.colcinetFlights.domaine.Bundle;

public interface GestionPilots {
	/** Aucune m�thode ne renvoie de r�sultat car elles garnissent toutes le bundle **/
	void getPilot(Bundle bundle);
	void addPilot(Bundle bundle);
	void getListSortedByName(Bundle bundle);
	void getListSortedByBalance(Bundle bundle);
	void modifyPilot(Bundle bundle);
	void getRowCountByName(Bundle bundle);
	void getRowCountByBalance(Bundle bundle);
	//void deletePilot(Bundle bundle);
}
