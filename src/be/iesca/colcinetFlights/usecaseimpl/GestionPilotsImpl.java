package be.iesca.colcinetFlights.usecaseimpl;

import java.util.List;
import be.iesca.colcinetFlights.dao.PilotDao;
import be.iesca.colcinetFlights.domaine.Bundle;
import be.iesca.colcinetFlights.domaine.Pilot;
import be.iesca.colcinetFlights.usecase.GestionPilots;
import be.iesca.colcinetFlights.daoimpl.DaoFactory;

/**********
 * Gestionnaire de use case 
 * Couche logique
 * 
 **********/

public class GestionPilotsImpl implements GestionPilots {
	private PilotDao pilotDao;

	public GestionPilotsImpl() {
		this.pilotDao = (PilotDao) DaoFactory.getInstance().getDaoImpl(PilotDao.class);
	}

	@Override
	public void getPilot(Bundle bundle) {
		boolean success = false;
		String nomEtPrenomDuPilote = (String) bundle.get(Bundle.NAME_AND_FORNAME);
		String msg = "";
		Pilot p = this.pilotDao.getPilot(nomEtPrenomDuPilote);
		
		if(p == null) {
			msg = "Pilote introuvable";
		} else {
			success = true;
		}
		
		bundle.put(Bundle.NAME_AND_FORNAME, nomEtPrenomDuPilote);
		bundle.put(Bundle.MESSAGE, msg);
		bundle.put(Bundle.PILOT, p);
		bundle.put(Bundle.OPERATION_SUCCESS, success);
	}
	
	@Override
	public void addPilot(Bundle bundle) {
		boolean success = false;
		String msg = "";
		Pilot pilot = (Pilot) bundle.get(Bundle.PILOT);
		if (pilot.getNomPilot() == null || pilot.getNomPilot().isEmpty()) {
			msg = "Ajout impossible. Nom manquant";
		} else if (pilot.getPrenomPilot() == null || pilot.getPrenomPilot().isEmpty()) {
			msg = "Ajout impossible. Prénom manquant";
		} else if (pilot.getEmailPilot() == null || pilot.getEmailPilot().isEmpty()) {
			msg = "Ajout impossible. Email manquant";
		} else if (pilot.getAdressePilot() == null || pilot.getAdressePilot().isEmpty()) {
			msg = "Ajout impossible. Adresse manquante";
		} else if (pilot.getNoGsmPilot() == null || pilot.getNoGsmPilot().isEmpty()) {
			msg = "Ajout impossible. Numéro de téléphone manquant";
		} else {
			Pilot pilotDB = this.pilotDao.getPilot(pilot.getNomPilot() + ' ' + pilot.getPrenomPilot());
			if (pilotDB != null) {
				msg = "Ajout impossible. Pilote déjà existant";
			} else {
				success = this.pilotDao.addPilot(pilot);
				if (success) {
					msg = "Ajout réalisé avec succès";
				} else {
					msg = "L'ajout n'a pas pu etre réalisé";
				}
			}
		}
		bundle.put(Bundle.OPERATION_SUCCESS, success);
		bundle.put(Bundle.PILOT, pilot);
		bundle.put(Bundle.MESSAGE, msg);
	}

	@Override
	public void getListSortedByName(Bundle bundle) {
		/** Initialisations **/
		boolean check = true;
		String message = "";
		List<Pilot> listeDePilotes = null;
		
		/** Trt **/
		listeDePilotes = this.pilotDao.listAllPilotsByName(); //La m�thode ListAllPilots g�re d�j� le tri sur le nom
		if(listeDePilotes == null) {
			check = false;
			message = "La liste est vide.";
		} else if(listeDePilotes.size() == 1) {
			message = "Il y a un seul pilote dans la liste.";
		} else {
			message = "La liste contient plusieurs pilotes";
		}
		
		bundle.put(Bundle.OPERATION_SUCCESS, check);
		bundle.put(Bundle.MESSAGE, message);
		bundle.put(Bundle.LIST, listeDePilotes);
	}

	@Override
	/** Ne garder que les pilotes dont le solde est négatif **/
	public void getListSortedByBalance(Bundle bundle) {
		/** Initialisations **/
		boolean check = true;
		String message = "";
		List<Pilot> listeDePilotes = null;
		
		/** Trt **/
		listeDePilotes = this.pilotDao.listAllPilotsByBalance(); //R�cup�ration de la liste tri�e sur le solde
		if(listeDePilotes == null) {
			check = false;
			message = "La liste est vide.";
		} else {
			message = "La liste ne contient que les pilotes ayant un solde négatif, triée sur ce solde";
		}
		
		bundle.put(Bundle.OPERATION_SUCCESS, check);
		bundle.put(Bundle.MESSAGE, message);
		bundle.put(Bundle.LIST, listeDePilotes);
	}

	@Override
	public void modifyPilot(Bundle bundle) {
		boolean sucess = false;
		String message = "";
		Pilot p = (Pilot) bundle.get(Bundle.PILOT);
		if (p.getNomPilot() == null || p.getNomPilot().isEmpty()) {
			message = "La modification n'a pas pu être réalisée. Il manque le nom du pilote";
		} else if (p.getAdressePilot() == null || p.getAdressePilot().isEmpty()) {
			message = "La modification n'a pas pu étre réalisée. Il manque l'adresse du pilote";
		} else if (p.getEmailPilot() == null || p.getEmailPilot().isEmpty()) {
			message = "La modification n'a pas pu être réalisée. Il manque l'email du pilote";
		} else if (p.getNoGsmPilot() == null || p.getNoGsmPilot().isEmpty()) {
			message = "La modification n'a pas pu être réalisée. Il manque le numéro de t�l�phone du pilote";
		} else if (p.getPrenomPilot() == null || p.getPrenomPilot().isEmpty()) {
			message = "La modification pas pa pu être réalisée. Il manque le prénom du pilote";
		} else {
			Pilot pilotDB = this.pilotDao.getPilot(p.getNomPilot() + " " + p.getPrenomPilot());
			if (pilotDB == null) {
				message = "La modification n'a pas pu être réalisée. Ce pilote n'existe pas";
			} else {
				sucess = this.pilotDao.modifyPilot(p);
				if (sucess) {
					message = "Modification réalisée avec succès";
				} else {
					message = "La modification n'a pas pu être réalisée";
				}
			}
		}
		bundle.put(Bundle.OPERATION_SUCCESS, sucess);
		bundle.put(Bundle.MESSAGE, message);
	}

	@Override
	public void getRowCountByName(Bundle bundle) {
		boolean success = true;
		int rowCount = this.pilotDao.getRowCountByName();
		String message = "Il y a " + rowCount + " pilotes enregistrés";
		
		bundle.put(Bundle.OPERATION_SUCCESS, success);
		bundle.put(Bundle.ROWCOUNT, rowCount);
		bundle.put(Bundle.MESSAGE, message);
	}
	
	@Override
	public void getRowCountByBalance(Bundle bundle) {
		boolean success = true;
		int rowCount = this.pilotDao.getRowCountByBalance();
		String message = "Il y a " + rowCount + " pilotes au solde négatif";
		
		bundle.put(Bundle.OPERATION_SUCCESS, success);
		bundle.put(Bundle.ROWCOUNT, rowCount);
		bundle.put(Bundle.MESSAGE, message);
	}

//	@Override
//	public void deletePilot(Bundle bundle) {
//		String nomDuPilote = (String) bundle.get(Bundle.NAME);
//		String message = "";
//		boolean check = false;
//		check = this.pilotDao.deletePilot(nomDuPilote);
//		if(check) {
//			message = "Le pilote a �t� supprim�.";
//		} else {
//			message = "Le pilote n'a pas �t� supprim�.";
//		}
//		
//		bundle.put(Bundle.OPERATION_SUCCESS, check);
//		bundle.put(Bundle.MESSAGE, message);
//	}	
}
