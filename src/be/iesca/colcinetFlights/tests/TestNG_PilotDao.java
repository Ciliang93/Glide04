package be.iesca.colcinetFlights.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import be.iesca.colcinetFlights.dao.PilotDao;
import be.iesca.colcinetFlights.daoimpl.DaoFactory;
import be.iesca.colcinetFlights.domaine.Pilot;

/**********
 * Test unitaire de la classe PilotDaoImpl
 * 
 **********/

public class TestNG_PilotDao {
	private List<Pilot> listSortedByName;
	private List<Pilot> listSortedByBalance;
	private PilotDao pilotDao = (PilotDao) DaoFactory.getInstance().getDaoImpl(PilotDao.class);
	
	/**********
	 * Initialisations
	 * 
	 **********/
	
	@BeforeClass
	public void initPilotList() {
		emptyList();
		listSortedByName = new ArrayList<Pilot>(6);
		listSortedByBalance = new ArrayList<Pilot>(6);
		
		Pilot angelo = new Pilot("ciliberto.angelo@gmail.com", "Ciliberto", "Angelo", "Rue basse, 75, Binche", "0488456789", -100.00);
		Pilot junior = new Pilot("colson.junior@gmail.com", "Colson", "Junior", "Rue Willy Ernst, 7, 6000 Charleroi", "0477906985", -50.00);
		Pilot benjamin = new Pilot("delhoye.benjamin@gmail.com", "Delhoye", "Benjamin", "Rue moyenne, 44, Montgny-Le-Tilleul", "0477789123", -150.00);
		Pilot robin = new Pilot("minadeo.robin@gmail.com", "Minadeo", "Robin", "Rue petite, 32, Marchienne-au-Pont", "0490125874", 25.00);
		Pilot maxime = new Pilot("minet.maxime@gmail.com", "Minet", "Maxime", "Rue grande, 120, Bruxelles", "0499123456", 100.00);
		
	//Ajout des pilotes dans les listes (triée)
		listSortedByName.add(angelo);
		listSortedByName.add(junior); 
		listSortedByName.add(benjamin);
		listSortedByName.add(robin);
		listSortedByName.add(maxime);
		
		listSortedByBalance.add(benjamin);
		listSortedByBalance.add(angelo);
		listSortedByBalance.add(junior);
	}
	
	@AfterClass //Exécuté après tout le code (vider la map du mock)
	private void emptyList() {
		List<Pilot> pilotResult = this.pilotDao.listAllPilotsByName();
		for(Pilot p : pilotResult) {
			assertTrue(pilotDao.deletePilot(p.getNomPilot()));
		}
	}
	
	/**********
	 * Tests
	 * 
	 **********/
	
	@Test
	public void testAdd() {
		for (Pilot p : listSortedByBalance) {
			assertTrue(pilotDao.addPilot(p));
		}
	}
	
	@Test(dependsOnMethods = { "testAdd" })
	public void testListByName() {
		List<Pilot> pilotResult = pilotDao.listAllPilotsByName();
		for (int i=0; i < listSortedByName.size(); i++) {
			assertEquals(pilotResult.get(i), listSortedByName.get(i));
		}
	}
	
	@Test(dependsOnMethods = { "testListByName" })
	public void testListByBalance() {
		List<Pilot> pilotResult = pilotDao.listAllPilotsByBalance();
		for(int i=0; i < listSortedByBalance.size(); i++) {
			assertEquals(pilotResult.get(i), listSortedByBalance.get(i));
		}
	}
	
	@Test(dependsOnMethods = { "testListByBalance" })
	private void testSearch() {
		for(Pilot p : listSortedByName) {
			Pilot pilotResult = pilotDao.getPilot(p.getNomPilot() + " " + p.getPrenomPilot());
			assertEquals(pilotResult, p);
		}
	}
	
	@Test(dependsOnMethods = { "testSearch" })
	private void testModify() {
		Pilot p = pilotDao.getPilot(listSortedByName.get(0).getNomPilot() + " " + listSortedByName.get(0).getPrenomPilot());
		p.setEmailPilot(p.getEmailPilot() + "/");
		p.setPrenomPilot(p.getPrenomPilot() + "/");
		p.setAdressePilot(p.getAdressePilot() + "/");
		p.setNoGsmPilot(p.getNoGsmPilot() + "/");
		
		assertTrue(pilotDao.modifyPilot(p));
		
		Pilot pilotResult = pilotDao.getPilot(p.getNomPilot() + " " + p.getPrenomPilot());
		assertEquals(pilotResult, p);
	}
	
	@Test(dependsOnMethods = { "testModify" })
	private void testDelete() {
		for(Pilot p : listSortedByName) {
			assertTrue(pilotDao.deletePilot(p.getNomPilot()));
		}
		List<Pilot> pilotResult = pilotDao.listAllPilotsByName();
		assertNotNull(pilotResult);
		assertEquals(0, pilotResult.size());
	}
}
