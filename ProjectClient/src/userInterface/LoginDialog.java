package userInterface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import connection.Client;
 
public class LoginDialog extends JDialog {
 
	private static final long serialVersionUID = -5782469100515942339L;
	private JTextField tfEmail;
    private JPasswordField pfPassword;
    private JLabel lbEmail;
    private JLabel lbPassword;
    private JButton btnLogin;
    private JButton btnNewUser;
    private boolean succeeded;
    
    private Client ClientOperations;
 
    public LoginDialog(Frame parent, Client co) {
        super(parent, "Login", true);
        ClientOperations = co;
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
 
        cs.fill = GridBagConstraints.HORIZONTAL;
 
        lbEmail = new JLabel("Email: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbEmail, cs);
 
        tfEmail = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(tfEmail, cs);
 
        lbPassword = new JLabel("Password: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbPassword, cs);
 
        pfPassword = new JPasswordField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(pfPassword, cs);
 
        btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
            	ClientOperations.login(getEmail(), getPassword());
                if (ClientOperations.getStatus()) {
                    JOptionPane.showMessageDialog(LoginDialog.this, "Hi! You have successfully logged in.", "Login",
                            JOptionPane.INFORMATION_MESSAGE);
                    
                    succeeded = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginDialog.this, "Invalid email or password!", "Login",
                            JOptionPane.ERROR_MESSAGE);
                    // reset username and password
                    tfEmail.setText("");
                    pfPassword.setText("");
                    succeeded = false;
 
                }
            }
        });
        
        btnNewUser = new JButton("New User");
        btnNewUser.addActionListener(new ActionListener() {
        	 
            public void actionPerformed(ActionEvent e) {
            	CreateUserDialog cud = new CreateUserDialog(parent);
            	cud.setVisible(true);
            	if(cud.getName() != null && cud.getEmail() != null){
            		
            		ClientOperations.createUser(cud.getName(), cud.getEmail(), cud.getPassword());
            		
            		if(ClientOperations.getStatus()){
            			JOptionPane.showMessageDialog(LoginDialog.this, "User created!", "Login",
                                JOptionPane.INFORMATION_MESSAGE);
            		}else{
            			JOptionPane.showMessageDialog(LoginDialog.this, "User name or email already registered!", "Login",
                                JOptionPane.ERROR_MESSAGE);
            		}
            	}
            }
        });
        
        JPanel bp = new JPanel();
        bp.add(btnLogin);
        bp.add(btnNewUser);
 
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);
 
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }
    
    
 
    public String getEmail() {
        return tfEmail.getText().trim();
    }
 
    public String getPassword() {
        return new String(pfPassword.getPassword());
    }
 
    public boolean isSucceeded() {
        return succeeded;
    }
}
