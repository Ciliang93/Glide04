package be.iesca.colcinetFlights.ihm;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import be.iesca.colcinetFlights.controleur.GestionnaireUseCases;
import be.iesca.colcinetFlights.domaine.Bundle;
import be.iesca.colcinetFlights.domaine.Flight;
import be.iesca.colcinetFlights.domaine.Glide;
import be.iesca.colcinetFlights.domaine.Pilot;

@SuppressWarnings("serial")
public class FlightsFormPanel extends JPanel {

	private Model model;

	private GestionnaireUseCases gestionnaireUseCases;

	private double prix = 0;
	private double duree;
	private Pilot selectedPilot;
	private Glide selectedGlide;

	/** GUI **/

	private JLabel dateLabel;
	private JSpinner dateSpinner;
	private SpinnerDateModel dateModel;
	private JSpinner.DateEditor editor;

	private JLabel dureeLabel;
	private JSpinner dureeSpinner;
	private SpinnerNumberModel dureeModel;

	private JLabel piloteLabel;
	private JComboBox<Pilot> piloteComboBox;
	private DefaultComboBoxModel<Pilot> piloteComboBoxModel;

	private JLabel planeurLabel;
	private JComboBox<Glide> planeurComboBox;
	private DefaultComboBoxModel<Glide> planeurComboBoxModel;

	private JLabel prixLabel;
	private JTextField jtfPrix;

	private JButton saveBtn;
	private JButton cancelBtn;

	/** Constructeur **/

	@SuppressWarnings("unchecked")
	public FlightsFormPanel(Model modelUnique, MainFrame mainFrame) {
		this.model = modelUnique;

		gestionnaireUseCases = GestionnaireUseCases.getInstance();

		/**
		 * Fields
		 */

		// DATE
		dateLabel = new JLabel("Date : ");
		dateModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
		dateSpinner = new JSpinner(dateModel);
		editor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
		dateSpinner.setEditor(editor);

		// DUREE
		duree = 0;
		dureeLabel = new JLabel("Durée : ");
		dureeModel = new SpinnerNumberModel(0, 0, 300, 1);
		dureeSpinner = new JSpinner(dureeModel);

		// LISTE DES PILOTES
		piloteLabel = new JLabel("Pilote : ");
		piloteComboBoxModel = new DefaultComboBoxModel<Pilot>(); // Modèle de la
																	// liste
																	// déroulante
		gestionnaireUseCases.getListSortedByName(this.model.getBundle()); // On
																			// récupère
																			// dans
																			// le
																			// bundle
																			// la
																			// liste
																			// des
																			// pilotes
																			// triée
																			// sur
																			// le
																			// nom
		List<Pilot> listeTriee = (List<Pilot>) this.model.getBundle().get(Bundle.LIST); // On
																						// crée
																						// une
																						// liste
																						// de
																						// pilotes
																						// avec
																						// le
																						// contenu
																						// du
																						// bundle
		Iterator<Pilot> i = listeTriee.iterator(); // On crée l'itérator pour
													// parcourir cette liste

		while (i.hasNext()) {
			Pilot p = i.next(); // On crée pour chaque élémet de la liste un
								// pilote p
			piloteComboBoxModel.addElement(p); // On ajoute ce pilote au modele
												// de la liste déroulante
		}

		piloteComboBox = new JComboBox<Pilot>(piloteComboBoxModel); // On crée
																	// le
																	// canevas
																	// de la
																	// liste
																	// déroulante
																	// et on
																	// ajoute le
																	// modele
																	// dedans
		selectedPilot = listeTriee.get(0);

		// LISTE DES PLANEURS
		planeurLabel = new JLabel("Planeur : ");
		planeurComboBoxModel = new DefaultComboBoxModel<Glide>(); // Modéle de
																	// la liste
																	// déroulante
		gestionnaireUseCases.listAllGlide(this.model.getBundle()); // Récupération
																	// dans le
																	// bundle
																	// des
																	// planeurs
		List<Glide> listeDePlaneur = (List<Glide>) this.model.getBundle().get(Bundle.LIST); // On
																							// crée
																							// une
																							// liste
																							// de
																							// planeur
																							// avec
																							// le
																							// contenu
																							// du
																							// bundle
		Iterator<Glide> j = listeDePlaneur.iterator(); // Itérateur pour
														// parcourir la liste

		while (j.hasNext()) {
			Glide g = j.next(); // On crée un planeur g avec chaque élément de
								// la liste
			planeurComboBoxModel.addElement(g); // On ajoute chaque élément au
												// modèle (juste le type de
												// chaque planeur)
		}

		planeurComboBox = new JComboBox<Glide>(planeurComboBoxModel); // On crée
																		// la
																		// liste
																		// déroulante
																		// en y
																		// ajouatnt
																		// le
																		// modele
		selectedGlide = listeDePlaneur.get(0); // Par défaut, le premier planeur
												// en base de donnée sera
												// séléctionné

		// PRIX
		prix = 0;
		prixLabel = new JLabel("Prix : ");
		jtfPrix = new JTextField(10);
		jtfPrix.setEditable(false);

		/**
		 * Buttons
		 */

		saveBtn = new JButton();
		saveBtn.setIcon(new ImageIcon("img/validate.png"));
		saveBtn.setToolTipText("Sauver");

		cancelBtn = new JButton();
		cancelBtn.setIcon(new ImageIcon("img/cancel.png"));
		cancelBtn.setToolTipText("Annuler");

		/**
		 * GUI
		 */

		Border innerBorder = BorderFactory.createTitledBorder("Informations Vol");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		setLayout(new GridBagLayout());
		setVisible(false);

		layoutComponents(); // Ajoute tous les composants au GridBagLayout

		/**
		 * Listeners
		 */

		// SAUVEGARDER
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String prixForm = jtfPrix.getText();
				prixForm = prixForm.replaceAll("[^\\d.]", "");
				
				if(!prixForm.isEmpty()) {
					prix = Math.round(Double.valueOf(prixForm)*100/100);
				}
				
				Flight f = new Flight((Date) dateSpinner.getValue(), (int) dureeSpinner.getValue(),
						Double.valueOf(prix), selectedGlide, selectedPilot);

				model.getBundle().put(Bundle.FLIGHT, f);
				gestionnaireUseCases.addFlight(model.getBundle());
				
				if ((boolean) model.getBundle().get(Bundle.OPERATION_SUCCESS)) {
					emptyFields();
					mainFrame.getFlightsTablePanel().refreshStatusField();
					setVisible(false);
				}else{
					mainFrame.getFlightsTablePanel().refreshStatusField();
				}
			}

		});

		// ANNULER
		cancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				emptyFields();
				setVisible(false);
				mainFrame.getFlightsTablePanel().emptyStatusField();

			}
		});

		// COMBOBOX
		piloteComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<Pilot> combo = (JComboBox<Pilot>) e.getSource();
				selectedPilot = (Pilot) combo.getSelectedItem();
				model.getBundle().put(Bundle.PILOT, selectedPilot);
			}

		});

		planeurComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<Glide> combo = (JComboBox<Glide>) e.getSource();
				selectedGlide = (Glide) combo.getSelectedItem();
				model.getBundle().put(Bundle.GLIDE, selectedGlide);

				if (prix < 25) {
					prix = selectedGlide.getPrixFixeGlide();
				} else {
					calculerPrix(selectedGlide.getPrixFixeGlide(), selectedGlide.getPrixHoraireGlide(), duree);
				}

				afficherPrix();
			}

		});

		// SPINNER
		dureeSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner spin = (JSpinner) e.getSource();
				int value = (int) spin.getValue();
	
				double dureeSelect = Double.parseDouble(""+value);
				duree = dureeSelect;

				calculerPrix(selectedGlide.getPrixFixeGlide(), selectedGlide.getPrixHoraireGlide(), duree);
				afficherPrix();
			}

		});
	}

	/**
	 * Méthodes
	 */

	public void layoutComponents() {
		GridBagConstraints gc = new GridBagConstraints();
		// 1e ligne
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = gc.gridheight = 1;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 10, 10, 10);
		add(dateLabel, gc);

		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 10, 10, 10);
		gc.gridx = 1;
		add(dateSpinner, gc);

		// 2e ligne
		gc.gridwidth = gc.gridheight = 1;
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 10, 10, 10);
		add(dureeLabel, gc);

		gc.gridx = 1;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 10, 10, 10);
		add(dureeSpinner, gc);

		// 3e ligne
		gc.gridwidth = gc.gridheight = 1;
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 10, 10, 10);
		add(piloteLabel, gc);

		gc.gridx = 1;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 10, 10, 10);
		add(piloteComboBox, gc);

		// 4e ligne
		gc.gridwidth = gc.gridheight = 1;
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 10, 10, 10);
		add(planeurLabel);

		gc.gridx = 1;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 10, 10, 10);
		add(planeurComboBox, gc);

		// 5e ligne
		gc.gridwidth = gc.gridheight = 1;
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 10, 10, 10);
		add(prixLabel);

		gc.gridx = 1;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 10, 10, 10);
		add(jtfPrix, gc);

		// ligne des boutons

		gc.gridwidth = gc.gridheight = 1;
		gc.gridy++;
		gc.gridx = 0;
		gc.insets = new Insets(0, 10, 10, 10);
		add(saveBtn, gc);
		saveBtn.setVisible(true);

		gc.gridx = 1;
		gc.insets = new Insets(0, 10, 10, 10);
		add(cancelBtn, gc);

	}

	public void afficherPrix() {
		int prixEntier = (int) prix;
		jtfPrix.setText(prixEntier + " €");
	}

	public void calculerPrix(double prixFixe, double prixHoraire, double duree) {
		double prixParMinute = prixHoraire / 60.0;
		prix = prixFixe + (prixParMinute * duree);
	}

	public void emptyFields() {
		this.jtfPrix.setText("");
		this.dureeSpinner.setValue(0);
		this.dateSpinner.setValue(new Date());
	}

}
