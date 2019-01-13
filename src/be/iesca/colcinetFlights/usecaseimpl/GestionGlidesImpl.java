package be.iesca.colcinetFlights.usecaseimpl;

import java.util.List;

import be.iesca.colcinetFlights.dao.GlideDao;
import be.iesca.colcinetFlights.daoimpl.DaoFactory;
import be.iesca.colcinetFlights.domaine.Bundle;
import be.iesca.colcinetFlights.domaine.Glide;
import be.iesca.colcinetFlights.usecase.GestionGlides;

public class GestionGlidesImpl implements GestionGlides {
	private GlideDao glideDao;
	
	public GestionGlidesImpl() {
		this.glideDao = (GlideDao) DaoFactory.getInstance().getDaoImpl(GlideDao.class);
	}
	
	@Override
	public void listAllGlide(Bundle bundle) {
		boolean check = true;
		String message = "";
		List<Glide> listeDePlaneur = null;
		
		listeDePlaneur = this.glideDao.listAllGlides();
		if(listeDePlaneur == null) {
			check = false;
			message = "La liste est vide";
		} else {
			message = "La liste contient les planeurs";
		}
		
		bundle.put(Bundle.OPERATION_SUCCESS, check);
		bundle.put(Bundle.MESSAGE, message);
		bundle.put(Bundle.LIST, listeDePlaneur);
	}

	@Override
	public void getGlide(Bundle bundle) {
		boolean success = false;
		String typePlaneur = (String) bundle.get(Bundle.TYPE);
		String msg = "";
		Glide g = this.glideDao.getGlide(typePlaneur);
		
		if(g == null) {
			msg = "Planeur introuvable";
		} else {
			success = true;
		}
		
		bundle.put(Bundle.TYPE, typePlaneur);
		bundle.put(Bundle.MESSAGE, msg);
		bundle.put(Bundle.GLIDE, g);
		bundle.put(Bundle.OPERATION_SUCCESS, success);
	}

}
