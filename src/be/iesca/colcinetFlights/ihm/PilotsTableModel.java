package be.iesca.colcinetFlights.ihm;

import be.iesca.colcinetFlights.domaine.Pilot;
import java.util.List;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class PilotsTableModel extends AbstractTableModel{
	
	private String[] colNames = {"Email", "Nom", "Prenom", "Adresse", "GSM", "Solde"};
	private int rowCount = 0;
	private List<Pilot> pilotList;
	
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
		Pilot p = pilotList.get(row);
		Object value = null;
		switch (col) {
		case 0:
			value = p.getEmailPilot();
			break;
		case 1:
			value = p.getNomPilot();
			break;
		case 2:
			value = p.getPrenomPilot();
			break;
		case 3:
			value = p.getAdressePilot();
			break;
		case 4:
			value = p.getNoGsmPilot();
			break;
		case 5:
			value = p.getSoldePilot();
			break;
		default:
			break;
		}
		return value;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex,int columnIndex){
		return false;
	}
	
	public void setRowCount(int newRowCount) {
		this.rowCount = newRowCount;
	}
	
	public void add(List<Pilot> listeDePilote) {
		this.pilotList = listeDePilote;
		this.fireTableDataChanged();
	}
}
