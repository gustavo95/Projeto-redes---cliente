package userInterface;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import connection.Client;

public class ClientMain {

	public static void main(String[] args) {
		final JFrame frame = new JFrame("JDialog Demo");
		frame.setSize(500, 400);
		
		LoginDialog loginDlg = new LoginDialog(frame);
		loginDlg.setVisible(true);
		if(loginDlg.isSucceeded()){
			MainMenu mm = new MainMenu(frame, new Client());
            mm.setVisible(true);
		}
        frame.dispose();
		

	}
	
	public static void showMessage(String message){
		JOptionPane.showMessageDialog(null, message);
	}

}
