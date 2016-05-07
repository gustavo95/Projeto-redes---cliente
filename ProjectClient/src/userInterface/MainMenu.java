package userInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import connection.Client;
import requisitionManagement.Requisition;

public class MainMenu extends JDialog{

	private static final long serialVersionUID = 1483918045883562024L;

	private Client clientOperations;

	private Frame parent;
	private JPanel panel;
	private GridBagConstraints gbc;
	private JPanel requestsPanel;
	private JPanel userRequestsPanel;
	private JScrollPane spr;
	private JScrollPane spur;
	private JLabel lbRequests;
	private JLabel lbUserRequests;
	private JLabel lbSpace;
	private JButton btnRefresh;
	private JButton btnCreate;

	public MainMenu(Frame parent, Client clientOperations){
		super(parent, "Main Menu", true);
		this.parent = parent;
		this.clientOperations = clientOperations;
		
		panel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();
		requestsPanel = null;
		userRequestsPanel = null;
		spr = null;
		spur = null;

		gbc.fill = GridBagConstraints.HORIZONTAL;

		lbRequests = new JLabel("Requests to reply:");
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(lbRequests, gbc);

		lbUserRequests = new JLabel("Your requests:");
		gbc.gridx = 2;
		gbc.gridy = 0;
		panel.add(lbUserRequests, gbc);

		setRequisitionsList();

		btnRefresh = new JButton("Refresh");
		btnRefresh.setIcon(new ImageIcon(".\\Icon\\cloud-sync.png"));
		btnRefresh.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				setRequisitionsList();
				
				requestsPanel.revalidate();
				requestsPanel.repaint();
				userRequestsPanel.revalidate();
				userRequestsPanel.repaint();

			}
		});
		
		btnCreate = new JButton("New Requsition");
		btnCreate.setIcon(new ImageIcon(".\\Icon\\add.png"));
		btnCreate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				CreateRequisition cr = new CreateRequisition(parent);
				cr.setVisible(true);
				if(cr.getTitle() != null){
					clientOperations.sendRequisition(cr.getTitle(), cr.getDescription());
					if(clientOperations.getStatus()){
						JOptionPane.showMessageDialog(MainMenu.this, "Requisition created!", "Create Requisition",
	                            JOptionPane.INFORMATION_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(MainMenu.this, "Could not create requisition!", "Create Requisition",
	                            JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(btnRefresh, gbc);

		gbc.gridx = 2;
		gbc.gridy = 2;
		panel.add(btnCreate, gbc);

		getContentPane().add(panel, BorderLayout.CENTER);

		pack();
		setResizable(false);
		setLocationRelativeTo(parent);
	}

	private void setRequisitionsList(){
		
		if(requestsPanel == null && userRequestsPanel == null){
			requestsPanel = new JPanel(new GridBagLayout());
			userRequestsPanel = new JPanel(new GridBagLayout());
		}else{
			requestsPanel.removeAll();
			userRequestsPanel.removeAll();
		}

		requisitionButtons();
		
		if(spr == null && spur == null){
			spr = new JScrollPane(requestsPanel);
			spur = new JScrollPane(userRequestsPanel);
		}

		spr.setMinimumSize(new Dimension(300, 250));
		spr.setPreferredSize(new Dimension(300, 250));
		spr.setMaximumSize(new Dimension(300, 250));

		spur.setMinimumSize(new Dimension(300, 250));
		spur.setPreferredSize(new Dimension(300, 250));
		spur.setMaximumSize(new Dimension(300, 250));
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(spr, gbc);

		lbSpace = new JLabel("   ");
		gbc.gridx = 1;
		gbc.gridy = 1;
		panel.add(lbSpace, gbc);

		gbc.gridx = 2;
		gbc.gridy = 1;
		panel.add(spur, gbc);
	}

	private void requisitionButtons(){
		ArrayList<Requisition> requisitions = clientOperations.askForList();

		JLabel requisitionLabel;
		ImageIcon image;
		JLabel imageLabel;

		GridBagConstraints cs = new GridBagConstraints();

		if(requisitions == null){
			requisitionLabel = new JLabel("No requisition");
			cs.gridx = 0;
			cs.gridy = 0;
			cs.gridwidth = 1;
			requestsPanel.add(requisitionLabel, cs);
		}else{
			int indexRequests = 0;
			int indexUserRequests = 0;

			for(Requisition r : requisitions){
				requisitionLabel = new JLabel(r.getUser().getName() + " request: " + r.getName_document());
				imageLabel = new JLabel();
				cs.gridx = 1;
				cs.anchor = GridBagConstraints.FIRST_LINE_START;

				if(r.getUser().getName().equals(clientOperations.getActiveUser().getName())){
					cs.gridy = indexUserRequests;
					userRequestsPanel.add(requisitionLabel, cs);

					if(r.isWasAttend()){
						image = new ImageIcon(".\\Icon\\cloud-down.png");
						DownloadReply dr = new DownloadReply(parent, r);
						
						requisitionLabel.addMouseListener(new MouseAdapter()  
						{  
							public void mouseClicked(MouseEvent e)  
							{  
								dr.setVisible(true);
								if(dr.getFile() != null){
									clientOperations.receiveFile(dr.getFile().getFileName(),
											dr.getFile().getFilePath(), dr.getFolder());
								}
							}
						});
						imageLabel.addMouseListener(new MouseAdapter()  
						{  
							public void mouseClicked(MouseEvent e)  
							{  
								dr.setVisible(true);
								if(dr.getFile() != null){
									clientOperations.receiveFile(dr.getFile().getFileName(),
											dr.getFile().getFilePath(), dr.getFolder());
								}
							}  
						});
						
					}else{
						image = new ImageIcon(".\\Icon\\cloud.png");
					}
					imageLabel.setIcon(image);
					cs.gridx = 0;
					cs.gridy = indexUserRequests;
					userRequestsPanel.add(imageLabel, cs);

					indexUserRequests++;
				}else{
					if(!r.isWasAttend()){
						cs.gridy = indexRequests;
						requestsPanel.add(requisitionLabel, cs);
						
						ReplyRequisition rr = new ReplyRequisition(parent);
						requisitionLabel.addMouseListener(new MouseAdapter()  
						{  
							public void mouseClicked(MouseEvent e)  
							{  
								rr.setVisible(true);
								if(rr.getFile() != null){
									clientOperations.sendFile(rr.getFile().getFileName(), rr.getFile().getFilePath());
								}
							}  
						});
						imageLabel.addMouseListener(new MouseAdapter()  
						{  
							public void mouseClicked(MouseEvent e)  
							{  
								rr.setVisible(true);
								if(rr.getFile() != null){
									clientOperations.sendFile(rr.getFile().getFileName(), rr.getFile().getFilePath());
								}
							}  
						});

						image = new ImageIcon(".\\Icon\\cloud-up.png");
						imageLabel.setIcon(image);
						cs.gridx = 0;
						cs.gridy = indexRequests;
						requestsPanel.add(imageLabel, cs);

						indexRequests++;
					}
				}
			}
			cs.gridx = 3;
			cs.weightx = 1;
			cs.weighty = 1;
			JPanel filler1 = new JPanel();
			filler1.setOpaque(false);
			JPanel filler2 = new JPanel();
			filler2.setOpaque(false);
			cs.gridy = indexRequests;
			requestsPanel.add(filler1, cs);
			cs.gridy = indexUserRequests;
			userRequestsPanel.add(filler2, cs);
		}
	}
}