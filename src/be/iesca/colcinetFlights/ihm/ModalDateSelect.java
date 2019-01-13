package be.iesca.colcinetFlights.ihm;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import be.iesca.colcinetFlights.domaine.Bundle;
import be.iesca.colcinetFlights.domaine.Flight;

@SuppressWarnings("serial")
public class ModalDateSelect extends JDialog {
	
	public ModalDateSelect(MainFrame parent, String title, String message) {
		super(parent, title);
		setLocationRelativeTo(null);
		
		JPanel messagePanel = new JPanel();
		messagePanel.add(new JLabel(message));
		getContentPane().add(messagePanel, BorderLayout.WEST);
		
		JPanel datePanel = new JPanel();
		
		SpinnerDateModel dateModel = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
		JSpinner dateSpinner = new JSpinner(dateModel);
		JSpinner.DateEditor editor = new JSpinner.DateEditor( dateSpinner, "dd/MM/yyyy");
		dateSpinner.setEditor(editor);
		dateSpinner.setVisible(false);
		
		dateSpinner.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSpinner spin = (JSpinner) e.getSource();
				parent.getfTableModsController().setDateSelected((Date) spin.getValue());
			}
			
		});
		
		JCheckBox checkBoxDate = new JCheckBox();
		checkBoxDate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkBoxDate.isSelected()) {
					dateSpinner.setVisible(true);
					parent.getfTableModsController().setDateSelected((Date) dateSpinner.getValue());
				} else {
					dateSpinner.setVisible(false);
					parent.getfTableModsController().setDateSelected(null);
				}
			}
			
		});
		
		datePanel.add(checkBoxDate);
		datePanel.add(dateSpinner);
		
		getContentPane().add(datePanel, BorderLayout.EAST);
		
		JPanel buttonPanel = new JPanel();
		JButton btnYes = new JButton("Confirmer");
		btnYes.addActionListener(new ActionListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				if(parent.getfTableModsController().getDateSelected() == null) { //Lister tous les vols
					parent.getfTableModsController().getGestionnaireUseCases().getRowCountWithoutDate(parent.getfTableModsController().getModel().getBundle()); //Garnir le nombre de lignes du bundle
					parent.getfTableModsController().getTableModel().setRowCount( (int) parent.getfTableModsController().getModel().getBundle().get(Bundle.ROWCOUNT) ); //Definir le nombre de ligne
					
					parent.getfTableModsController().getGestionnaireUseCases().listFlightsWithoutDate(parent.getfTableModsController().getModel().getBundle()); //Garnir la liste du bundle
					parent.getfTableModsController().getTableModel().add( (List<Flight>) parent.getfTableModsController().getModel().getBundle().get(Bundle.LIST) ); //Mise à jour du tableModel

					parent.getfTableModsController().setDateSelected(null); //On réinitialise la date pour le prochain listing
                    parent.getfTableModsController().getModel().getBundle().put(Bundle.DATE, null); //On réinitialise la date pour le prochain listing

					
				} else { //Lister les vols sur la date demandée
					Date dateChoisie = parent.getfTableModsController().getDateSelected();
					parent.getfTableModsController().getModel().getBundle().put(Bundle.DATE, dateChoisie);
					parent.getfTableModsController().getGestionnaireUseCases().getRowCountWithDate(parent.getfTableModsController().getModel().getBundle());
					parent.getfTableModsController().getTableModel().setRowCount( (int) parent.getfTableModsController().getModel().getBundle().get(Bundle.ROWCOUNT) );
					
					parent.getfTableModsController().getGestionnaireUseCases().listFlightsWithDate(parent.getfTableModsController().getModel().getBundle());
					parent.getfTableModsController().getTableModel().add( (List<Flight>) parent.getfTableModsController().getModel().getBundle().get(Bundle.LIST) );
					parent.getfTableModsController().setDateSelected(null); //On réinitialise la date pour le prochain listing
                    parent.getfTableModsController().getModel().getBundle().put(Bundle.DATE, null); //On réinitialise la date pour le prochain listing
				}
				dispose();
			}
		});
		
		buttonPanel.add(btnYes);
		
		JButton btnNo = new JButton("Annuler");
		btnNo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		buttonPanel.add(btnNo);
		
		getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(300, 100);;
		setVisible(true);
		
	}
}
