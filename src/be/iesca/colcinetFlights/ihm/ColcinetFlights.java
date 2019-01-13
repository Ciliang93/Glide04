package be.iesca.colcinetFlights.ihm;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class ColcinetFlights {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainFrame mainFrame = new MainFrame();
				JFrame.setDefaultLookAndFeelDecorated(true);
				mainFrame.setVisible(true);
			}
		});

	}

}
