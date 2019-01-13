package be.iesca.colcinetFlights.ihm;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import be.iesca.colcinetFlights.domaine.Flight;

@SuppressWarnings("serial")
public class FlightsTableModel extends AbstractTableModel{
	//private GestionVols gestionnaireVols;
	public String[] colNames = {"Date", "Durée", "Type Planeur", "Pilote", "Prix"};
	private int rowCount = 0;
	private List<Flight> listeDeVols;

	@Override
	public String getColumnName(int column) {
		return colNames[column];
	}
	
	@Override
	public int getRowCount() {
		return this.rowCount;
	}

	@Override
	public int getColumnCount() {
		return colNames.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		Flight v = listeDeVols.get(row);
		Object value = null;
		switch(col) {
		case 0:
			value = v.getDateFlight();
			break;
		case 1:
			value = v.getDureeFlight();
			break;
		case 2:
			value = v.getPlaneur().toString();
			break;
		case 3:
			value = v.getPilote().toString();
			break;
		case 4:
			value = v.getPrixFlight();
			break;
		}
		return value;
	}
	
	public boolean isCellEditable(int rowIndex,int columnIndex){
		return false;
	}
	
	public void setRowCount(int newRowCount) {
		this.rowCount = newRowCount;
	}
	
	public void add(List<Flight> listeDeVols) {
		this.listeDeVols = listeDeVols;
		fireTableRowsInserted(0,listeDeVols.size() - 1);
	}

}
