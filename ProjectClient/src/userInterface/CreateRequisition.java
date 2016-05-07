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
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateRequisition extends JDialog{
	
	private static final long serialVersionUID = -9015225698293366602L;
	private JTextField tfTitle;
    private JTextField tfDescription;
    private JLabel lbTitle;
    private JLabel lbDescription;
    private JButton btnCreate;
    private JButton btnCancel;
    private String title;
    private String description;
 
    public CreateRequisition(Frame parent) {
        super(parent, "Create Requisition", true);
        
        title = null;
        description = null;
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
 
        cs.fill = GridBagConstraints.HORIZONTAL;
 
        lbTitle = new JLabel("Title: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbTitle, cs);
 
        tfTitle = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(tfTitle, cs);
 
        lbDescription = new JLabel("Description: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(lbDescription, cs);
 
        tfDescription = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(tfDescription, cs);
 
        btnCreate = new JButton("Create");
        btnCreate.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                title = tfTitle.getText();
                description = tfDescription.getText();
                dispose();
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

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

}
