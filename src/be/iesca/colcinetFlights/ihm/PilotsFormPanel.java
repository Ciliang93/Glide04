package be.iesca.colcinetFlights.ihm;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import be.iesca.colcinetFlights.controleur.GestionnaireUseCases;
import be.iesca.colcinetFlights.domaine.Bundle;
import be.iesca.colcinetFlights.domaine.Pilot;

@SuppressWarnings("serial")
public class PilotsFormPanel extends JPanel {

	private Model model;

	private GestionnaireUseCases gestUseCases;

	private JLabel nameLabel;
	private JTextField jtfName;

	private JLabel surnameLabel;
	private JTextField jtfSurname;

	private JLabel emailLabel;
	private JTextField jtfEmail;

	private JLabel gsmLabel;
	private JTextField jtfGsm;

	private JLabel adressLabel;
	private JTextField jtfAdress;

	private JLabel accountAmountLabel;
	private JTextField jtfAccountAmount;

	private JButton saveBtn;
	private JButton modifBtn;
	private JButton cancelBtn;

	public PilotsFormPanel(Model modelUnique, MainFrame mainFrame) {
		this.model = modelUnique;
		this.gestUseCases = GestionnaireUseCases.getInstance();

		/**
		 * TextFields
		 */

		nameLabel = new JLabel("Nom : ");
		jtfName = new JTextField(20);

		surnameLabel = new JLabel("Prénom : ");
		jtfSurname = new JTextField(20);

		emailLabel = new JLabel("Email : ");
		jtfEmail = new JTextField(20);

		gsmLabel = new JLabel("Gsm : ");
		jtfGsm = new JTextField(20);

		adressLabel = new JLabel("Adresse : ");
		jtfAdress = new JTextField(40);

		accountAmountLabel = new JLabel("Solde Compte : ");
		jtfAccountAmount = new JTextField(5);

		/**
		 * Buttons
		 */

		saveBtn = new JButton();
		saveBtn.setIcon(new ImageIcon("img/validate.png"));
		saveBtn.setToolTipText("Sauver");

		cancelBtn = new JButton();
		cancelBtn.setIcon(new ImageIcon("img/cancel.png"));
		cancelBtn.setToolTipText("Annuler");

		modifBtn = new JButton();
		modifBtn.setIcon(new ImageIcon("img/gross-pencil.png"));
		modifBtn.setToolTipText("Sauver");

		/**
		 * GUI
		 */

		Border innerBorder = BorderFactory.createTitledBorder("Informations Pilote");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

		setLayout(new GridBagLayout());
		setVisible(false);

		layoutComponents(); // Ajoute tous les composants au GridBagLayout

		/**
		 * ActionPerformed
		 */

		// SAUVEGARDER
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				double solde;
				String name = jtfName.getText();
				String surname = jtfSurname.getText();
				String email = jtfEmail.getText();
				String gsm = jtfGsm.getText();
				String adress = jtfAdress.getText();
				if (jtfAccountAmount.getText().isEmpty()) {
					jtfAccountAmount.setText("0");
					solde = 0;
				} else {
					solde = Double.parseDouble(jtfAccountAmount.getText());
					solde = Math.round(solde*100/100);
				}

				Pilot p = new Pilot(email, name, surname, adress, gsm, solde);
				model.getBundle().put(Bundle.PILOT, p);
				gestUseCases.addPilot(model.getBundle());

				if ((boolean) model.getBundle().get(Bundle.OPERATION_SUCCESS)) {
					emptyTextFields();
					setVisible(false);
				}

				mainFrame.getPilotsTablePanel().refreshStatusField();
			}
		});

		// ANNULER
		cancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				emptyTextFields();
				setVisible(false);
				mainFrame.getPilotsTablePanel().emptyStatusField();
			}
		});

		// MODIFIER
		modifBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double solde = 0;
				String name = jtfName.getText();
				String surname = jtfSurname.getText();
				String email = jtfEmail.getText();
				String gsm = jtfGsm.getText();
				String adress = jtfAdress.getText();
				
				if (jtfAccountAmount.getText().isEmpty()) {
					solde = (((Pilot)model.getBundle().get(Bundle.PILOT)).getSoldePilot());
					
				} else {
					  try  
					  {  
						 solde = Double.parseDouble(jtfAccountAmount.getText());
						 Pilot p = new Pilot(email, name, surname, adress, gsm, solde);
							model.getBundle().put(Bundle.PILOT, p);

							gestUseCases.modifyPilot(model.getBundle());

							if ((boolean) model.getBundle().get(Bundle.OPERATION_SUCCESS)) {
								emptyTextFields();
								setVisible(false);
							}

							mainFrame.getPilotsTablePanel().refreshStatusField();
					  }  
					  catch(NumberFormatException nfe)  
					  {  
						  mainFrame.getPilotsTablePanel().getStatusField().setForeground(new Color(255,0,51));
						  mainFrame.getPilotsTablePanel().getStatusField().setText("Erreur de saisie");
						  mainFrame.getPilotsTablePanel().getStatusField().setVisible(true);
					  } 	
				}
			}
		});
	}

	/**
	 * Méthodes
	 */

	public void setTextFields(Pilot p) {
		this.jtfEmail.setText(p.getEmailPilot());
		this.jtfName.setText(p.getNomPilot());
		this.jtfName.setEditable(false);
		this.jtfSurname.setText(p.getPrenomPilot());
		this.jtfAdress.setText(p.getAdressePilot());
		this.jtfGsm.setText(p.getNoGsmPilot());
		this.jtfAccountAmount.setText("" + p.getSoldePilot());
	}

	public void emptyTextFields() {
		jtfName.setText("");
		jtfSurname.setText("");
		jtfEmail.setText("");
		jtfGsm.setText("");
		jtfAdress.setText("");
		jtfAccountAmount.setText("");
	}

	public void layoutComponents() {
		GridBagConstraints gc = new GridBagConstraints();
		// 1e ligne
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = gc.gridheight = 1;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 10, 10, 10);
		add(emailLabel, gc);

		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 10, 10, 10);
		gc.gridx = 1;
		add(jtfEmail, gc);

		// 2e ligne
		gc.gridwidth = gc.gridheight = 1;
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 10, 10, 10);
		add(nameLabel, gc);

		gc.gridx = 1;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 10, 10, 10);
		add(jtfName, gc);

		// 3e ligne
		gc.gridwidth = gc.gridheight = 1;
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 10, 10, 10);
		add(surnameLabel, gc);

		gc.gridx = 1;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 10, 10, 10);
		add(jtfSurname, gc);

		// 4e ligne

		gc.gridwidth = gc.gridheight = 1;
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 10, 10, 10);
		add(gsmLabel, gc);

		gc.gridx = 1;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 10, 10, 10);
		add(jtfGsm, gc);

		// 5e ligne
		gc.gridwidth = gc.gridheight = 1;
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 10, 10, 10);
		add(adressLabel, gc);

		gc.gridx = 1;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 10, 10, 10);
		add(jtfAdress, gc);

		// 6e ligne
		gc.gridwidth = gc.gridheight = 1;
		gc.gridy++;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 10, 10, 10);
		add(accountAmountLabel, gc);

		gc.gridx = 1;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 10, 10, 10);
		add(jtfAccountAmount, gc);

		// ligne des boutons

		gc.gridwidth = gc.gridheight = 1;
		gc.gridy++;
		gc.gridx = 0;
		gc.insets = new Insets(0, 10, 10, 10);
		add(saveBtn, gc);
		add(modifBtn, gc);
		saveBtn.setVisible(false);
		modifBtn.setVisible(false);

		gc.gridx = 1;
		gc.insets = new Insets(0, 10, 10, 10);
		add(cancelBtn, gc);

	}

	public JButton getSaveBtn() {
		return this.saveBtn;
	}

	public JButton getModifBtn() {
		return this.modifBtn;
	}

	public JTextField getJtfName() {
		return this.jtfName;
	}
	
	public JTextField getJtfSurname() {
		return this.jtfSurname;
	}

	public void setFormValues(Pilot pilote) {
		jtfEmail.setText("" + pilote.getEmailPilot());
		jtfName.setText("" + pilote.getNomPilot());
		jtfSurname.setText("" + pilote.getPrenomPilot());
		jtfGsm.setText("" + pilote.getNoGsmPilot());
		jtfAdress.setText("" + pilote.getAdressePilot());
		jtfAccountAmount.setText("" + pilote.getSoldePilot());

	}
}
