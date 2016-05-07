package userInterface;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import connection.Client;

public class ClientMain {
	
	private static Client clientOperations;

	public static void main(String[] args) {
		final JFrame frame = new JFrame("JDialog Demo");
		frame.setSize(500, 400);

		clientOperations = new Client();

		if(clientOperations.isConnected()){
			LoginDialog loginDlg = new LoginDialog(frame, clientOperations);
			loginDlg.setVisible(true);
			if(loginDlg.isSucceeded()){
				MainMenu mm = new MainMenu(frame, clientOperations);
				mm.setVisible(true);
			}
			frame.dispose();
			
			clientOperations.closeConection();
		}else {
			JOptionPane.showMessageDialog(frame, "No connection to the server","Login",
					JOptionPane.ERROR_MESSAGE);
			frame.dispose();
		}
		

	}

}
