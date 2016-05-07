package userInterface;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CreateUserDialog extends JDialog{

	private static final long serialVersionUID = -6656324918896304594L;
	private JTextField tfUsername;
	private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JPasswordField pfConfirmPassword;
    private JLabel lbUsername;
    private JLabel lbEmail;
    private JLabel lbPassword;
    private JLabel lbConfirmPassword;
    private JButton btnCreate;
    private JButton btnCancel;
    
    private String name;
    private String email;
    private String password;
    
    public CreateUserDialog(Frame parent){
    	super(parent, "Create User", true);
    	
    	name = null;
    	email = null;
    	password = null;
    	
    	JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
 
        cs.fill = GridBagConstraints.HORIZONTAL;
 
        lbUsername = new JLabel("Username: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbUsername, cs);
        
        tfUsername = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(tfUsername, cs);
 
        lbEmail = new JLabel("Email: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbEmail, cs);
        
        tfEmail = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(tfEmail, cs);
        
        lbPassword = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);
 
        pfPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 2;
        panel.add(pfPassword, cs);
        
        lbConfirmPassword = new JLabel("Confirm Password: ");
        cs.gridx = 0;
        cs.gridy = 3;
        cs.gridwidth = 1;
        panel.add(lbConfirmPassword, cs);
 
        pfConfirmPassword = new JPasswordField(50);
        cs.gridx = 1;
        cs.gridy = 3;
        cs.gridwidth = 2;
        panel.add(pfConfirmPassword, cs);
        
        btnCreate = new JButton("Create");
        btnCreate.addActionListener(new ActionListener() {
        	 
            public void actionPerformed(ActionEvent e) {
            	password = new String(pfPassword.getPassword());
            	if(password.equals(new String(pfConfirmPassword.getPassword()))){
            		name = tfUsername.getText();
            		email = tfEmail.getText();
            		dispose();
            	}else{
            		JOptionPane.showMessageDialog(CreateUserDialog.this, "Passwords don't match", "Create User",
                            JOptionPane.ERROR_MESSAGE);
            	}
            }
        });
        
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
        	 
            public void actionPerformed(ActionEvent e) {
            	dispose();
            }
        });
        
        JPanel bp = new JPanel();
        bp.add(btnCreate);
        bp.add(btnCancel);
        
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);
 
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}
