package be.iesca.colcinetFlights.tests;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import be.iesca.colcinetFlights.domaine.Pilot;
import be.iesca.colcinetFlights.domaine.Bundle;
import be.iesca.colcinetFlights.usecase.GestionPilots;
import be.iesca.colcinetFlights.usecaseimpl.GestionPilotsImpl;

/**
 * 
 * @author Junior Colson
 * Test d'intégration use case après use case
 * 
 */

public class TestNG_Integration {

	private List<Pilot> listeDePilotesTrieeParNom;
	private List<Pilot> listeDePilotesTrieeParSolde;
	private GestionPilots gestionPilotes;
	private Bundle bundle;
	
	/**
	 * Initialisations
	 */

	@BeforeClass //Exécuté avant le code principal
	public void initialisation() {
		gestionPilotes = new GestionPilotsImpl();
		bundle = new Bundle();
		listeDePilotesTrieeParNom = new ArrayList<Pilot>(6);
		listeDePilotesTrieeParSolde = new ArrayList<Pilot>(6);
		
		Pilot angelo = new Pilot("ciliberto.angelo@gmail.com", "Ciliberto", "Angelo", "Rue basse, 75, Binche", "0488456789", -100.00);
		Pilot junior = new Pilot("colson.junior@gmail.com", "Colson", "Junior", "Rue Willy Ernst, 7, 6000 Charleroi", "0477906985", -50.00);
		Pilot benjamin = new Pilot("delhoye.benjamin@gmail.com", "Delhoye", "Benjamin", "Rue moyenne, 44, Montgny-Le-Tilleul", "0477789123", -150.00);
		Pilot robin = new Pilot("minadeo.robin@gmail.com", "Minadeo", "Robin", "Rue petite, 32, Marchienne-au-Pont", "0490125874", 25.00);
		Pilot maxime = new Pilot("minet.maxime@gmail.com", "Minet", "Maxime", "Rue grande, 120, Bruxelles", "0499123456", 100.00);
		
		//Ajout des pilotes dans les listes (triée)
				listeDePilotesTrieeParNom.add(angelo);
				listeDePilotesTrieeParNom.add(junior); 
				listeDePilotesTrieeParNom.add(benjamin);
				listeDePilotesTrieeParNom.add(robin);
				listeDePilotesTrieeParNom.add(maxime);
				
				listeDePilotesTrieeParSolde.add(benjamin);
				listeDePilotesTrieeParSolde.add(angelo);
				listeDePilotesTrieeParSolde.add(junior);
	}

	/**
	 * Use case 1 : Ajout d'un nouveau pilote
	 */
	@Test
	public void testAjouter() {
		/* Ajout de toute la liste de pilotes dans le bundle */
		for (Pilot p : listeDePilotesTrieeParNom) {
			bundle.put(Bundle.PILOT, p);
			gestionPilotes.addPilot(bundle);
			assertTrue((boolean) bundle.get(Bundle.OPERATION_SUCCESS));
		}
		
		/* Tentative d'ajout d'un pilote existant */
		bundle.put(Bundle.PILOT, listeDePilotesTrieeParNom.get(0));
		gestionPilotes.addPilot(bundle);
		assertFalse((boolean) bundle.get(Bundle.OPERATION_SUCCESS));
		
		/* Tentative d'ajout d'un pilote aux données erronées */
		bundle.put(Bundle.PILOT, new Pilot(null, null, null, null, null, 0.00));
		gestionPilotes.addPilot(bundle);
		assertFalse((boolean) bundle.get(Bundle.OPERATION_SUCCESS));
		
		bundle.put(Bundle.PILOT, new Pilot("email", null, null, null, null, 0.00));
		gestionPilotes.addPilot(bundle);
		assertFalse((boolean) bundle.get(Bundle.OPERATION_SUCCESS));

		bundle.put(Bundle.PILOT, new Pilot("email","nom", null, null, null, 0.00));
		gestionPilotes.addPilot(bundle);
		assertFalse((boolean) bundle.get(Bundle.OPERATION_SUCCESS));

		bundle.put(Bundle.PILOT, new Pilot("email", "nom", "prenom", null, null, 0.00));
		gestionPilotes.addPilot(bundle);
		assertFalse((boolean) bundle.get(Bundle.OPERATION_SUCCESS));
		
		bundle.put(Bundle.PILOT, new Pilot("email", "nom", "prenom", "adresse", null, 0.00));
		gestionPilotes.addPilot(bundle);
		assertFalse((boolean) bundle.get(Bundle.OPERATION_SUCCESS));
	}
	
	/**
	 * Use case 2 : Obtenir une liste triée sur le nom et prénom
	 */
	
	@Test(dependsOnMethods = { "testAjouter" })
	public void testListerParNomEtPrenom() {
		gestionPilotes.getListSortedByName(bundle);
		@SuppressWarnings("unchecked")
		List<Pilot> pilotsResult = (List<Pilot>) bundle.get(Bundle.LIST);
		
		assertTrue((boolean) bundle.get(Bundle.OPERATION_SUCCESS));
		for (int i = 0; i < pilotsResult.size(); i++) {
			assertEquals(pilotsResult.get(i), listeDePilotesTrieeParNom.get(i));
		}
	}
	
	/**
	 * Use case 3 : Obtenir une liste triée sur les soldes (négatifs)
	 */
	
	@Test(dependsOnMethods = { "testListerParNomEtPrenom" })
	public void testListerParSolde() {
		gestionPilotes.getListSortedByBalance(bundle);
		@SuppressWarnings("unchecked")
		List<Pilot> pilotsResult = (List<Pilot>) bundle.get(Bundle.LIST);
		assertTrue((boolean) bundle.get(Bundle.OPERATION_SUCCESS));
		
		for(int i = 0; i < pilotsResult.size(); i++) {
			assertEquals(pilotsResult.get(i), listeDePilotesTrieeParSolde.get(i));
		}
	}
	
//	/**
//	 * Use case 4 : Rechercher un pilote
//	 */
//	
//	@Test(dependsOnMethods = { "testListerParSolde" })
//	public void testRechercher() {
//		for (Pilot p : listeDePilotesTrieeParNom) {
//			bundle.put(Bundle.NAME_AND_FORNAME, p.getNomPilot() + " " + p.getPrenomPilot());
//			gestionPilotes.getPilot(bundle);
//			Pilot piloteResult = (Pilot) bundle.get(Bundle.PILOT);
//			assertTrue((boolean) bundle.get(Bundle.OPERATION_SUCCESS));
//			
//			assertEquals(piloteResult, p); 
//		}
//	}
//	
//	/**
//	 * Use case 5 : Modifier un pilote
//	 */
//	
//	@Test(dependsOnMethods = { "testRechercher" })
//	public void testModifier() {
//		bundle.put(Bundle.NAME_AND_FORNAME, listeDePilotesTrieeParNom.get(0).getNomPilot() + " " + listeDePilotesTrieeParNom.get(0).getPrenomPilot());
//		gestionPilotes.getPilot(bundle);
//		assertTrue((boolean) bundle.get(Bundle.OPERATION_SUCCESS));
//		
//		Pilot p = (Pilot) bundle.get(Bundle.PILOT);
//		p.setAdressePilot(p.getAdressePilot() + "/");
//		p.setEmailPilot(p.getEmailPilot() + "/");
//		bundle.put(Bundle.PILOT, p);
//		gestionPilotes.modifyPilot(bundle);
//		
//		bundle.clear();
//		bundle.put(Bundle.NAME_AND_FORNAME, p.getNomPilot() + " " + p.getPrenomPilot());
//		gestionPilotes.getPilot(bundle);
//		assertTrue( (boolean) bundle.get( Bundle.OPERATION_SUCCESS));
//		
//		Pilot pilotResult = (Pilot) bundle.get(Bundle.PILOT);
//		assertEquals(pilotResult, p);
//		
//		bundle.put(Bundle.PILOT, new Pilot("?","?","?","?","?", 0.00));
//		gestionPilotes.modifyPilot(bundle);
//		assertFalse((boolean) bundle.get(Bundle.OPERATION_SUCCESS));
//		}
	}
