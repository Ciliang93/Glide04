package be.iesca.colcinetFlights.ihm;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import be.iesca.colcinetFlights.controleur.GestionnaireUseCases;
import be.iesca.colcinetFlights.ihm.ModalDateSelect;

@SuppressWarnings("serial")
public class FlightsTableModsController extends JPanel implements ActionListener{
	
	private Model model;
	private FlightsTableModel tableModel;
	
	private GestionnaireUseCases gestionnaireUseCases;
	
	private MainFrame mainFrame;
	private Date dateSelected;
	
	private JButton jbtList;
	private JButton jbtAdd;
	
	public FlightsTableModsController(Model model, FlightsTableModel tableModel, MainFrame mainFrame) {
		super();
		this.model = model;
		this.tableModel = tableModel;
		this.mainFrame = mainFrame;
		this.gestionnaireUseCases = GestionnaireUseCases.getInstance();
		
		/**
		 * Modifier ici pour le look des boutons
		 */
		this.jbtList = new JButton();
		this.jbtList.setIcon(new ImageIcon("img/lister.png"));
		this.jbtList.setToolTipText("Lister les vols");
		
		this.jbtAdd = new JButton();
		this.jbtAdd.setIcon(new ImageIcon("img/add.png"));
		this.jbtAdd.setToolTipText("Ajouter un vol");
		
		
		this.jbtAdd.addActionListener(this);
		this.jbtList.addActionListener(this);
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.add(jbtAdd);
		this.add(jbtList);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton pressed = (JButton) e.getSource();
		
		if(pressed == jbtAdd) {
			this.mainFrame.getFlightsFormPanel().setVisible(true);
			this.mainFrame.getFlightsTablePanel().emptyStatusField();
		} else if(pressed == jbtList) {
			this.mainFrame.getFlightsFormPanel().setVisible(false);
			new ModalDateSelect(this.mainFrame, "Date", "Lister sur une date ?");
			this.mainFrame.getFlightsTablePanel().emptyStatusField();
		}
	}

	public Date getDateSelected() {
		return dateSelected;
	}

	public void setDateSelected(Date dateSelected) {
		this.dateSelected = dateSelected;
	}

	public GestionnaireUseCases getGestionnaireUseCases() {
		return gestionnaireUseCases;
	}

	public void setGestionnaireUseCases(GestionnaireUseCases gestionnaireUseCases) {
		this.gestionnaireUseCases = gestionnaireUseCases;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public FlightsTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(FlightsTableModel tableModel) {
		this.tableModel = tableModel;
	}
}
