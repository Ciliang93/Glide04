package be.iesca.colcinetFlights.dao;

import java.util.List;
import be.iesca.colcinetFlights.domaine.Glide;

public interface GlideDao extends Dao {
	List<Glide> listAllGlides();
	Glide getGlide(String type);
}
