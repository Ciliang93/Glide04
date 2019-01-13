package be.iesca.colcinetFlights.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import be.iesca.colcinetFlights.domaine.Bundle;
import be.iesca.colcinetFlights.domaine.Pilot;

/**
 * Modèle MVC
 *    Vue
 */

@SuppressWarnings("serial")
public class PilotsTablePanel extends JPanel {
	/**
	 * Panneau de gestion des pilotes
	 */
	private PilotsTableModel tableModel;
	
	private JPanel panelNorth;
	
	private JTable table;
	private JTextField status;
	
	private PilotsTableModsController controller;
	
	private MainFrame mainFrame;

	public PilotsTablePanel(PilotsTableModel tableModelUnique, PilotsTableModsController controllerUnique, MainFrame mainFrame) {
		super(new GridLayout(25, 6));
		this.tableModel = tableModelUnique;
		this.mainFrame = mainFrame;

		this.table = new JTable();
		this.table.setModel(this.tableModel);
		this.controller = controllerUnique;
		
		this.status = new JTextField(50);
		status.setEditable(false);
		status.setBorder(null);
		Font myFont = new Font("Lucida Console", Font.BOLD, 20);
		status.setFont(myFont);
		status.setHorizontalAlignment(JTextField.RIGHT);

		table.setRowHeight(25);
		
		this.panelNorth = new JPanel(new BorderLayout());

		this.setLayout(new BorderLayout());
		this.setVisible(false);
		this.add(new JScrollPane(table), BorderLayout.CENTER);
		
		panelNorth.add(controller, BorderLayout.LINE_START);
		panelNorth.add(status, BorderLayout.LINE_END);
		
		this.add(panelNorth, BorderLayout.NORTH);
	}

	public Pilot getValuesFromRow() {
		int ligne = table.getSelectedRow();

		String email = (String) table.getValueAt(ligne, 1);
		String name = (String) table.getValueAt(ligne, 2);
		String surname = (String) table.getValueAt(ligne, 3);
		String gsm = (String) table.getValueAt(ligne, 4);
		String adress = (String) table.getValueAt(ligne, 5);
		double solde = (double) table.getValueAt(ligne, 6);

		Pilot pilote = new Pilot(email, name, surname, adress, gsm, solde);

		return pilote;
	}
	
	public JTable getTable() {
		return this.table;
	}
	
	public JTextField getStatusField(){
		return this.status;
	}
	
	public void refreshStatusField() {
		String bundleMessage = (String) mainFrame.getModel().getBundle().get(Bundle.MESSAGE);
		if ((boolean) mainFrame.getModel().getBundle().get(Bundle.OPERATION_SUCCESS)) {
			status.setForeground(new Color(123,150,105));
		} else {
			status.setForeground(new Color(255,0,51));
		}
		status.setText(bundleMessage);
	}

	public void emptyStatusField() {
		status.setText("");
		status.setForeground(Color.BLACK);
	}
}
