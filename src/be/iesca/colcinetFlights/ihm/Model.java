package be.iesca.colcinetFlights.ihm;

import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import be.iesca.colcinetFlights.domaine.Bundle;

public class Model {
	private Bundle bundle;
	private ArrayList<ChangeListener> listViews;
	
	public Model() {
		this.bundle = new Bundle();
		this.bundle.put(Bundle.OPERATION_SUCCESS, true);
		this.listViews = new ArrayList<ChangeListener>(1);
	}
	
	public Model(String message) {
		this();
		this.bundle.put(Bundle.MESSAGE, message);
	}
	
	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
		traiterEvent(new ChangeEvent(this));
	}
	
	
	public synchronized void addChangeListener(ChangeListener chl) {
		if(!listViews.contains(chl)) {
			listViews.add(chl);
		}
	}
	
	public synchronized void removeChangeListener(ChangeListener chl) {
		if(listViews.contains(chl)) {
			listViews.remove(chl);
		}
	}
	
	protected synchronized void traiterEvent(ChangeEvent e) {
		for(ChangeListener listener : listViews) {
			listener.stateChanged(e);
		}
	}
	
	public Bundle getBundle() {
		return this.bundle;
	}
}
