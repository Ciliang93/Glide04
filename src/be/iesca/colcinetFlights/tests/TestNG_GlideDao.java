package be.iesca.colcinetFlights.tests;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import be.iesca.colcinetFlights.dao.GlideDao;
import be.iesca.colcinetFlights.daoimpl.DaoFactory;
import be.iesca.colcinetFlights.domaine.Glide;

/**********
 * Test unitaire de la classe GlideDaoImpl
 * 
 **********/

public class TestNG_GlideDao {
	private List<Glide> listeDePlaneurs;
	private GlideDao glideDao = (GlideDao) DaoFactory.getInstance().getDaoImpl(GlideDao.class);
	
	/**********
	 * Initialisations
	 * 
	 **********/
	
	@BeforeClass
	public void initGlideList() {
		listeDePlaneurs = new ArrayList<Glide>(3);
		
		Glide planeur1 = new Glide("bois et toile", 17.0, 25.0);
		Glide planeur2 = new Glide("plastique", 27.0, 25.0);
		Glide planeur3 = new Glide("biplace", 30.0, 25.0);
		
		listeDePlaneurs.add(planeur1);
		listeDePlaneurs.add(planeur2);
		listeDePlaneurs.add(planeur3);
	}
	
	/**********
	 * Tests
	 * 
	 **********/
	
	@Test
	public void testList() {
		List<Glide> glideResult = glideDao.listAllGlides();
		for(int i=0; i < listeDePlaneurs.size(); i++) {
			assertEquals(glideResult.get(i).getTypeGlide(), listeDePlaneurs.get(i).getTypeGlide());
		}
	}
}
