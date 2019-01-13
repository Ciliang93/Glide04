package be.iesca.colcinetFlights.daoimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import be.iesca.colcinetFlights.dao.PilotDao;
import be.iesca.colcinetFlights.domaine.Pilot;

/**********
 * MockObject de la db
 * couche persistance
 *
 **********/

public class PilotDaoMockImpl implements PilotDao {
	private TreeMap<String, Pilot> mapPilots;

	public PilotDaoMockImpl() {
		Comparator<String> keyComp = new PilotNameOnlyComparator();
		this.mapPilots = new TreeMap<String, Pilot>(keyComp);
	}

	/**********
	 * Methodes
	 * 
	 **********/
	
	@Override
	public boolean addPilot(Pilot pilot) {
		try {
			if (this.mapPilots.containsKey(pilot.getNomPilot()))
				return false;
			this.mapPilots.put(pilot.getNomPilot() + " " + pilot.getPrenomPilot(), pilot);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}
	
	@Override
	public List<Pilot> listAllPilotsByName() {
		Collection<Pilot> collectionDePilotes = Collections.unmodifiableCollection(this.mapPilots.values()); //La map est trié grace au comparateur
		List<Pilot> listeTrieeSurLeNomEtPrenom = new ArrayList<Pilot>(collectionDePilotes);
		Comparator<Pilot> valueComp = new PilotNameAndFornameComparator();
		listeTrieeSurLeNomEtPrenom.sort(valueComp);
		return listeTrieeSurLeNomEtPrenom;
		
	}
	
	public List<Pilot> listAllPilotsByBalance(){
		List<Pilot> listSortedOnBalance = listAllPilotsByName();
		Iterator<Pilot> i = listSortedOnBalance.iterator(); //Lors d'un remove, le foreach plante. Il est donc préférable de passer par un iterateur.
		
		while (i.hasNext()) {
			Pilot p = i.next();
			if(p.getSoldePilot() >= 0) {
				i.remove(); //Le remove se fait via l'itérateur
			}
		}
		listSortedOnBalance.sort(new BalanceComparator()); //Tri sur le solde (du plus négatif au moins négatif)
		
		return listSortedOnBalance;
	}

	@Override
	public Pilot getPilot(String nameAndForname) {
		try{
			//TODO
			Pilot pilot = this.mapPilots.get(nameAndForname);
			if (pilot == null) return null;
			return pilot;
		} catch (Exception ex){
			return null;
		}
	}

	@Override
	public boolean modifyPilot(Pilot pilote) {
		try {
			if(this.mapPilots.remove(pilote.getNomPilot()) == null) return false;
			this.mapPilots.put(pilote.getNomPilot(), pilote);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean deletePilot(String nomDuPilote) {
		try {
			if(this.mapPilots.remove(nomDuPilote) == null) return false;
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	@Override
	public int getRowCountByName() {
		return listAllPilotsByName().size();
	}
	
	@Override
	public int getRowCountByBalance() {
		return listAllPilotsByBalance().size();
	}
	
	/**********
	 * Comparateurs 
	 *
	 **********/
	
	private class PilotNameOnlyComparator implements Comparator<String> {
		@Override
		public int compare(String nomDePilote1, String nomDePilote2) {
			return (nomDePilote1.compareTo(nomDePilote2));
		}
	}
	
	private class PilotNameAndFornameComparator implements Comparator<Pilot> {
		@Override
		public int compare(Pilot pilote1, Pilot pilote2) {
			if( pilote1.getNomPilot().equals( pilote2.getNomPilot() ) ){
				return ( pilote1.getPrenomPilot().compareTo(pilote2.getPrenomPilot()) );
			}
			return pilote1.getNomPilot().compareTo(pilote2.getNomPilot());
		}
	}
	
	private class BalanceComparator implements Comparator<Pilot> {
		@Override
		public int compare(Pilot pilote1, Pilot pilote2) {
			if (pilote1.getSoldePilot() > pilote2.getSoldePilot()) return 1;
			if (pilote1.getSoldePilot() < pilote2.getSoldePilot()) return -1;
			return 0;
		}
	}
}
 