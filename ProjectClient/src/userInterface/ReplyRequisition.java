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

public class ReplyRequisition extends JDialog{
	
	private static final long serialVersionUID = 2899774524109715352L;
	private JTextField tfFile;
    private JLabel lbFile;
    private JButton btnBrowse;
    private JButton btnSend;
    private JButton btnCancel;
    private FileOperations fo;
 
    public ReplyRequisition(Frame parent) {
        super(parent, "Reply Requisition", true);
        
        fo = null;
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
 
        cs.fill = GridBagConstraints.HORIZONTAL;
 
        lbFile = new JLabel("File: ");
        cs.gridx = 0;
        cs.gridy = 0;
        panel.add(lbFile, cs);
 
        tfFile = new JTextField(20);
        tfFile.setEnabled(false);
        tfFile.setText(" ");
        cs.gridx = 1;
        cs.gridy = 0;
        panel.add(tfFile, cs);
        
        btnBrowse = new JButton("Browse...");
        btnBrowse.addActionListener(new ActionListener() {
        	 
            public void actionPerformed(ActionEvent e) {
            	JFileChooser chooser = new JFileChooser();
                int retorno = chooser.showOpenDialog(null);
            
                if (retorno == JFileChooser.APPROVE_OPTION) {
                  tfFile.setText(chooser.getSelectedFile().getPath());
                  tfFile.revalidate();
                  tfFile.repaint();
                  panel.revalidate();
                  panel.repaint();
                  parent.pack();
                }
            }
        });
        cs.gridx = 2;
        cs.gridy = 0;
        panel.add(btnBrowse, cs);
 
        btnSend = new JButton("Send");
        btnSend.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
            	if(!tfFile.getText().equals(" ")){
            		fo = new FileOperations(tfFile.getText());
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
        bp.add(btnSend);
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
