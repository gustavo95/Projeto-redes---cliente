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

import fileManagement.FileOperations;
import requisitionManagement.Requisition;

public class DownloadReply extends JDialog{
	
	private static final long serialVersionUID = 6714995860553945925L;
	private JLabel lbUser;
    private JLabel lbFile;
    private JButton btnDownload;
    private JButton btnCancel;
    private FileOperations fo;
 
    public DownloadReply(Frame parent, Requisition requisition) {
        super(parent, "Reply Requisition", true);
        
        fo = null;
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
 
        cs.fill = GridBagConstraints.HORIZONTAL;
        
        lbUser = new JLabel("User: " + "userReply");
        cs.gridx = 0;
        cs.gridy = 0;
        panel.add(lbUser, cs);
        
        lbFile = new JLabel("File: " + "fileReply");
        cs.gridx = 0;
        cs.gridy = 1;
        panel.add(lbFile, cs);
 
        btnDownload = new JButton("Download");
        btnDownload.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                fo = new FileOperations();
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
        bp.add(btnDownload);
        bp.add(btnCancel);
 
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);
 
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }
    
    public FileOperations getFile(){
    	return fo;
    }

}
