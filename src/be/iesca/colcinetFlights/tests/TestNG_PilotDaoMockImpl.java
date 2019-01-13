package be.iesca.colcinetFlights.tests;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import be.iesca.colcinetFlights.domaine.Pilot;
import be.iesca.colcinetFlights.dao.PilotDao;
import be.iesca.colcinetFlights.daoimpl.PilotDaoMockImpl;

/**********
 * 
 * @author Junior Colson
 * Test unitaire de la classe PilotDaoMockImpl
 *
 **********/
public class TestNG_PilotDaoMockImpl {
	private List<Pilot> listeDePilotesTrieeParNom;
	private List<Pilot> listeDePilotesTrieeParSolde;
	protected PilotDao piloteDao;
	
	/**********
	 * Initialisations
	 * 
	 **********/
	
	@BeforeClass //Exécuté avant le code principal
	public void initialiserLeDao() {
		piloteDao = new PilotDaoMockImpl();
	}
	
	@BeforeClass //Exécuté avant le code principal
	public void initialiserListePilotes() {
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
	
	@AfterClass //Exécuté après tout le code (vider la map du mock)
	private void EmptyList() {
		List<Pilot> resultatPilotes = this.piloteDao.listAllPilotsByName();
		for(Pilot p : resultatPilotes) {
			assertTrue(piloteDao.deletePilot(p.getNomPilot()));
		}
		
	}
	
	/**********
	 * Tests
	 * 
	 **********/
	
	@Test
	public void testAjouter() {
		for (Pilot p : listeDePilotesTrieeParNom) {
			assertTrue(piloteDao.addPilot(p));
		}
	}
	
	@Test(dependsOnMethods = { "testAjouter" })
	public void testListerParNom() {
		List<Pilot> resultatPilotes = piloteDao.listAllPilotsByName();
		for (int i = 0; i < listeDePilotesTrieeParNom.size(); i++) {
			assertEquals(resultatPilotes.get(i), listeDePilotesTrieeParNom.get(i));
		}
	}
	
	@Test(dependsOnMethods = { "testListerParNom" })
	public void testListerParSolde() {
		List<Pilot> resultatPilotes = piloteDao.listAllPilotsByBalance();
		for(int i = 0; i < listeDePilotesTrieeParSolde.size(); i++) {
			assertEquals(resultatPilotes.get(i), listeDePilotesTrieeParSolde.get(i));
		}
	}
	
	@Test(dependsOnMethods = { "testListerParSolde" })
	public void testRechercher() {
		
		for(Pilot p : listeDePilotesTrieeParNom) {
			Pilot resultatPilote = piloteDao.getPilot(p.getNomPilot());
			assertEquals(resultatPilote, p);
		}
	}
	
	@Test(dependsOnMethods = { "testRechercher" })
	public void testModifier() {
		Pilot pilote = piloteDao.getPilot(listeDePilotesTrieeParNom.get(0).getNomPilot());
		pilote.setAdressePilot(pilote.getAdressePilot() + "/");
		pilote.setEmailPilot(pilote.getEmailPilot() + "/");
		pilote.setNoGsmPilot(pilote.getNoGsmPilot() + "/");
		pilote.setPrenomPilot(pilote.getPrenomPilot() + "/");
		pilote.setSoldePilot(pilote.getSoldePilot() + 50.0);
		
		assertTrue(piloteDao.modifyPilot(pilote));
		
		Pilot piloteResult = piloteDao.getPilot(pilote.getNomPilot());
		assertEquals(piloteResult, pilote);
	}	
}
