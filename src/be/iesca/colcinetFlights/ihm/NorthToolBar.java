package be.iesca.colcinetFlights.ihm;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;

@SuppressWarnings("serial")
public class NorthToolBar extends JPanel implements ActionListener {
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menuFichier = new JMenu("Fichier");
	private JMenu menuGestion = new JMenu("Gestion");
	private JMenuItem fichierQuitter = new JMenuItem("Quitter");
	private JMenuItem gestionPilotes = new JMenuItem("Gestion des pilotes");
	private JMenuItem gestionVols = new JMenuItem("Gestion des vols");
	private JToolBar toolBar = new JToolBar();
	private MainFrame mainFrame;
	
	public NorthToolBar(MainFrame mainFrame) {
		
		this.mainFrame = mainFrame;
		
		fichierQuitter.addActionListener(this);
		gestionPilotes.addActionListener(this);
		gestionVols.addActionListener(this);
		
		menuFichier.add(fichierQuitter);
		menuGestion.add(gestionPilotes);
		menuGestion.add(gestionVols);
		
		menuBar.add(menuFichier);
		menuBar.add(menuGestion);
		

		this.setLayout(new BorderLayout());
		toolBar.add(menuBar);
		toolBar.setFloatable(false);
		
		this.add(toolBar, BorderLayout.CENTER);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		JMenuItem clicked = (JMenuItem) arg0.getSource();
		
		if(clicked == gestionPilotes) { //Afficher une table pour les pilotes
			this.mainFrame.getFlightsTablePanel().setVisible(false);
			this.mainFrame.getFlightsFormPanel().setVisible(false);
			this.mainFrame.add(this.mainFrame.getPilotsTablePanel(), BorderLayout.CENTER);
			this.mainFrame.add(this.mainFrame.getPilotsFormPanel(), BorderLayout.EAST);
			this.mainFrame.getPilotsTablePanel().setVisible(true);
		} else if(clicked == gestionVols) { //Afficher une table pour les vols
			this.mainFrame.getPilotsTablePanel().setVisible(false);
			this.mainFrame.getPilotsFormPanel().setVisible(false);
			this.mainFrame.add(this.mainFrame.getFlightsTablePanel(), BorderLayout.CENTER);
			this.mainFrame.add(this.mainFrame.getFlightsFormPanel(), BorderLayout.EAST);
			this.mainFrame.getFlightsTablePanel().setVisible(true);
		} else if(clicked == fichierQuitter) {
			this.mainFrame.dispose();
		}
	}
}
