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

@SuppressWarnings("serial")
public class FlightsTablePanel extends JPanel{
	/**
	 * Panneau de gestion des vols
	 */
	private FlightsTableModel tableModel;
	
	private JPanel panelNorth;
	private JTextField status;
	
	private JTable table;
	
	private FlightsTableModsController controller;
	private MainFrame mainFrame;
	
	public FlightsTablePanel(FlightsTableModel tableModelUnique, FlightsTableModsController controllerUnique, MainFrame mainFrame) {
		super(new GridLayout(25,5));
		this.tableModel = tableModelUnique;
		this.mainFrame = mainFrame;
		
		this.status = new JTextField(50);
		status.setEditable(false);
		status.setBorder(null);
		Font myFont = new Font("Lucida Console", Font.BOLD, 20);
		status.setFont(myFont);
		status.setHorizontalAlignment(JTextField.RIGHT);
		
		this.table = new JTable();
		this.table.setModel(this.tableModel);
		this.controller = controllerUnique;
		
		table.setRowHeight(25);
		
		this.panelNorth = new JPanel(new BorderLayout());
		
		this.setLayout(new BorderLayout());
		this.setVisible(false);
		
		panelNorth.add(controller, BorderLayout.LINE_START);
		panelNorth.add(status, BorderLayout.LINE_END);
		
		this.add(new JScrollPane(table), BorderLayout.CENTER);
		this.add(panelNorth, BorderLayout.NORTH);
				
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
			getStatusField().setForeground(new Color(255,0,51));
		}
		status.setText(bundleMessage);
	}

	public void emptyStatusField() {
		status.setText("");
		status.setForeground(Color.BLACK);
	}
}
