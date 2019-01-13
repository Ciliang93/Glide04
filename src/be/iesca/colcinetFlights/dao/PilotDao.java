package be.iesca.colcinetFlights.dao;

import java.util.List;

import be.iesca.colcinetFlights.domaine.Pilot;

public interface PilotDao extends Dao {
	boolean addPilot(Pilot pilote);
	Pilot getPilot (String nameAndForname);
	List<Pilot> listAllPilotsByName();
	List<Pilot> listAllPilotsByBalance();
	boolean modifyPilot(Pilot pilote);
	boolean deletePilot(String nomDuPilote);
	int getRowCountByName();
	int getRowCountByBalance();
}
