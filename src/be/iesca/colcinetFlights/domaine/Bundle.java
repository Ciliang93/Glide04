package be.iesca.colcinetFlights.domaine;

import java.util.HashMap;
import java.util.Map;

public class Bundle {
	public static final String OPERATION_SUCCESS = "operationReussie";
	public static final String MESSAGE = "message";
	public static final String ROWCOUNT = "rowcount";
	public static final String LIST = "list";
	
	//CONSTANTES UTILISEES POUR LES DONNEES DES PILOTES
	public static final String PILOT = "pilot";
	public static final String NAME_AND_FORNAME = "name";
	
	//CONSTANTES UTILISEES POUR LES DONNES DES PLANEURS
	public static final String GLIDE = "glide";
	public static final String TYPE = "type";
	
	//CONSTANTES UTILISEES POUR LES DONNEES DES VOLS
	public static final String FLIGHT = "flight";
	public static final String DATE = "date";
	
	//A RAJOUTER POUR LES ITERATIONS FUTURES...
	
	private Map<String, Object> map = new HashMap<String, Object>();
	
	public Bundle() {
		this.map.put(Bundle.OPERATION_SUCCESS, false);
		this.map.put(Bundle.MESSAGE, "");
	}
	
	public void put(String key, Object value) {
		this.map.put(key, value);
	}
	
	public Object get(String key) {
		return this.map.get(key);
	}
	
	public void clear() {
		this.map.clear();
	}
}
