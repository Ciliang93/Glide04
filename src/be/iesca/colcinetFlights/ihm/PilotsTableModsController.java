package be.iesca.colcinetFlights.ihm;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.util.List;
import be.iesca.colcinetFlights.domaine.Pilot;
import be.iesca.colcinetFlights.controleur.GestionnaireUseCases;
import be.iesca.colcinetFlights.domaine.Bundle;

/**
 * Modèle MVC
 * Controleur
 */

@SuppressWarnings("serial")
public class PilotsTableModsController extends JPanel implements ActionListener{

	private Model model;
	private PilotsTableModel tableModel;
	
	private GestionnaireUseCases gestionnaireUseCases;
	
	private MainFrame mainFrame;
	
	private JButton jbtListByName;
	private JButton jbtListByBalance;
	private JButton jbtModPilot;
	private JButton jbtAddPilot;
	
	public PilotsTableModsController(Model model, PilotsTableModel tableModelUnique, MainFrame mainFrame) {
		super();
		this.model = model;
		this.tableModel = tableModelUnique;
		this.mainFrame = mainFrame;	
		this.gestionnaireUseCases = GestionnaireUseCases.getInstance();
		
		this.jbtListByName = new JButton();
		this.jbtListByName.setIcon(new ImageIcon("img/lister.png"));
		this.jbtListByName.setToolTipText("Lister par nom");
		
		this.jbtListByBalance = new JButton();
		this.jbtListByBalance.setIcon(new ImageIcon("img/euro.png"));
		this.jbtListByBalance.setToolTipText("Lister les soldes négatifs");
		
		this.jbtModPilot = new JButton();
		this.jbtModPilot.setIcon(new ImageIcon("img/gross-pencil.png"));
		this.jbtModPilot.setToolTipText("Modifier pilote");
		
		this.jbtAddPilot = new JButton();
		this.jbtAddPilot.setIcon(new ImageIcon("img/user.png"));
		this.jbtAddPilot.setToolTipText("Ajouter un pilote");
			
		this.jbtListByName.addActionListener(this);
		this.jbtListByBalance.addActionListener(this);
		this.jbtModPilot.addActionListener(this);
		this.jbtAddPilot.addActionListener(this);
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(jbtAddPilot);
		this.add(jbtListByName);
		this.add(jbtListByBalance);
		this.add(jbtModPilot);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton pressed = (JButton) e.getSource();
		
		if(pressed == jbtListByName) { 
			this.gestionnaireUseCases.getRowCountByName(this.model.getBundle());
			this.tableModel.setRowCount( (int) this.model.getBundle().get(Bundle.ROWCOUNT) );
			
			this.gestionnaireUseCases.getListSortedByName(this.model.getBundle());
			this.tableModel.add( (List<Pilot>) this.model.getBundle().get(Bundle.LIST));
			this.mainFrame.getPilotsTablePanel().emptyStatusField();
			
		} else if(pressed == jbtListByBalance) {
			this.gestionnaireUseCases.getRowCountByBalance(this.model.getBundle());
			this.tableModel.setRowCount( (int) this.model.getBundle().get(Bundle.ROWCOUNT) );
			
			this.gestionnaireUseCases.getListSortedByBalance(this.model.getBundle());
			this.tableModel.add( (List<Pilot>) this.model.getBundle().get(Bundle.LIST) );
			this.mainFrame.getPilotsTablePanel().emptyStatusField();
			
		} else if(pressed == jbtModPilot) {
			int numeroDeLigne = this.mainFrame.getPilotsTablePanel().getTable().getSelectedRow();
			if(numeroDeLigne == -1) {
				this.mainFrame.getPilotsTablePanel().getStatusField().setForeground(new Color(255,0,51));
				this.mainFrame.getPilotsTablePanel().getStatusField().setText("Veuillez sélectionner un pilote pour le modifier");
			} else {
			String email = (String) this.tableModel.getValueAt(numeroDeLigne, 0);
			String nom = (String) this.tableModel.getValueAt(numeroDeLigne, 1);
			String prenom = (String) this.tableModel.getValueAt(numeroDeLigne, 2);
			String adresse = (String) this.tableModel.getValueAt(numeroDeLigne, 3); 
			String gsm = (String) this.tableModel.getValueAt(numeroDeLigne, 4);
			double solde = (double) this.tableModel.getValueAt(numeroDeLigne, 5);
			this.mainFrame.getPilotsTablePanel().emptyStatusField();
			
			Pilot p = new Pilot(email, nom, prenom, adresse, gsm, solde);
			this.mainFrame.getPilotsFormPanel().getModifBtn().setVisible(true);
			this.mainFrame.getPilotsFormPanel().getSaveBtn().setVisible(false);
			this.mainFrame.getPilotsFormPanel().setTextFields(p);
			this.mainFrame.getPilotsFormPanel().setVisible(false);
			this.mainFrame.getPilotsFormPanel().setVisible(true);
			this.mainFrame.getPilotsFormPanel().getJtfName().setEditable(false);
			this.mainFrame.getPilotsFormPanel().getJtfSurname().setEditable(false);
			this.mainFrame.getPilotsFormPanel().setVisible(true);	
			this.mainFrame.getPilotsTablePanel().emptyStatusField();
			}
			
		} else if(pressed == jbtAddPilot) {
			//Affichage du formulaire d'ajout
			this.mainFrame.getPilotsFormPanel().getModifBtn().setVisible(false);
			this.mainFrame.getPilotsFormPanel().getSaveBtn().setVisible(true);
			this.mainFrame.getPilotsFormPanel().getJtfName().setEditable(true);
			this.mainFrame.getPilotsFormPanel().getJtfSurname().setEditable(true);
			this.mainFrame.getPilotsFormPanel().emptyTextFields();
			this.mainFrame.getPilotsFormPanel().setVisible(true);
			this.mainFrame.getPilotsTablePanel().emptyStatusField();
		}
	}
	
}
