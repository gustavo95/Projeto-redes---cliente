package userInterface;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fileManagement.FileOperations;
import requisitionManagement.Requisition;

public class DownloadReply extends JDialog{
	
	private static final long serialVersionUID = 6714995860553945925L;
	private JLabel lbUser;
    private JLabel lbFile;
    private JTextField tfFolder;
    private JLabel lbFolder;
    private JButton btnBrowse;
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
        
        lbFolder = new JLabel("Save in: ");
        cs.gridx = 0;
        cs.gridy = 2;
        panel.add(lbFolder, cs);
 
        tfFolder = new JTextField(20);
        tfFolder.setEnabled(false);
        tfFolder.setText(" ");
        cs.gridx = 1;
        cs.gridy = 2;
        panel.add(tfFolder, cs);
        
        btnBrowse = new JButton("Browse...");
        btnBrowse.addActionListener(new ActionListener() {
        	 
            public void actionPerformed(ActionEvent e) {
            	JFileChooser chooser = new JFileChooser();
            	chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int retorno = chooser.showOpenDialog(null);
            
                if (retorno == JFileChooser.APPROVE_OPTION) {
                  tfFolder.setText(chooser.getSelectedFile().getPath());
                  panel.revalidate();
                  panel.repaint();
                  parent.pack();
                }
            }
        });
        cs.gridx = 2;
        cs.gridy = 2;
        panel.add(btnBrowse, cs);
 
        btnDownload = new JButton("Download");
        btnDownload.addActionListener(new ActionListener() {
 
        	public void actionPerformed(ActionEvent e) {
        		if(!tfFolder.equals(" ")){
        			fo = new FileOperations();
        			dispose();
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
    
    public String getFolder(){
    	return tfFolder.getText();
    }

}
