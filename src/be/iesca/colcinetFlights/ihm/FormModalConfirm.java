package be.iesca.colcinetFlights.ihm;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class FormModalConfirm extends JDialog{
	
	public FormModalConfirm(JFrame parent, String title, String message) {
		super(parent, title);
		setLocationRelativeTo(null);
		
		JPanel messagePanel = new JPanel();
		messagePanel.add(new JLabel(message));
		getContentPane().add(messagePanel);
		
		JPanel buttonPanel = new JPanel();
		JButton btnYes = new JButton("Confirmer");
		btnYes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
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
