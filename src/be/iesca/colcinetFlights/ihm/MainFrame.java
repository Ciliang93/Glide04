package be.iesca.colcinetFlights.ihm;

import java.awt.BorderLayout;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	/**
	 * MainFrame
	 */

	private Model model;
	private PilotsTableModel pTableModel;
	private FlightsTableModel fTableModel;
	
	private PilotsTablePanel pilotsTablePanel;
	private FlightsTablePanel flightsTablePanel;
	
	private PilotsFormPanel pilotsFormPanel;
	private FlightsFormPanel flightsFormPanel;
	
	private PilotsTableModsController pTableModsController;
	private FlightsTableModsController fTableModsController;
	
	private NorthToolBar toolbar;
	
	public MainFrame() {
		super("Colcinet Flights");
		
		/**
		 * Modèle
		 */
		
		this.model = new Model(); //Modèle unique instancié au lancement
		this.pTableModel = new PilotsTableModel();
		this.fTableModel = new FlightsTableModel();
		
		/**
		 * Contrôleurs
		 */
		
		this.pilotsFormPanel = new PilotsFormPanel(this.model, this);
		this.flightsFormPanel = new FlightsFormPanel(this.model, this);
		this.pTableModsController = new PilotsTableModsController(this.model, this.pTableModel, this);
		this.fTableModsController = new FlightsTableModsController(this.model, this.fTableModel, this);
		
		/**
		 * Vue
		 */
		
		this.pilotsTablePanel = new PilotsTablePanel(pTableModel, pTableModsController, this);
		this.flightsTablePanel = new FlightsTablePanel(fTableModel, fTableModsController, this);
		
		/**
		 * GUI
		 */
		
		this.toolbar = new NorthToolBar(this);
		setSize(600,450);
		setLayout(new BorderLayout());
		add(toolbar, BorderLayout.NORTH);
		setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH); //Permet d'adapter automatiquement la taille de la fenetre au support
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
	
	public PilotsTablePanel getPilotsTablePanel() {
		return this.pilotsTablePanel;
	}
	
	public PilotsFormPanel getPilotsFormPanel() {
		return this.pilotsFormPanel;
	}

	public FlightsTablePanel getFlightsTablePanel() {
		return this.flightsTablePanel;
	}
	
	public FlightsFormPanel getFlightsFormPanel() {
		return this.flightsFormPanel;
	}
	
	public PilotsTableModel getPTableModel() {
		return this.pTableModel;
	}

	public PilotsTableModsController getpTableModsController() {
		return pTableModsController;
	}

	public void setpTableModsController(PilotsTableModsController pTableModsController) {
		this.pTableModsController = pTableModsController;
	}

	public FlightsTableModsController getfTableModsController() {
		return fTableModsController;
	}

	public void setfTableModsController(FlightsTableModsController fTableModsController) {
		this.fTableModsController = fTableModsController;
	}
	
	public Model getModel() {
		return this.model;
	}
	
}
