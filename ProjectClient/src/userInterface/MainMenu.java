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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import connection.Client;
import requisitionManagement.Requisition;

public class MainMenu extends JDialog{

	private static final long serialVersionUID = 1483918045883562024L;

	private Client clientOperations;

	int i;
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
	
	String userName = "Guga";

	public MainMenu(Frame parent, Client clientOperations){
		super(parent, "Main Menu", true);
		this.parent = parent;
		this.clientOperations = clientOperations;
		
		i  = 0;
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
				i++;
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
				CreateRequisition cr = new CreateRequisition(parent, userName);
				cr.setVisible(true);
				cr.getRequisition();
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
		//ArrayList<Requisition> requisitions = clientOperations.askForList();

		ArrayList<Requisition> requisitions = new ArrayList<Requisition>();
		requisitions.add(new Requisition("Jose", "James Stewart Vol II","Livro de cálculo utilizado nos cursos de cálculo 2,3 e 4", false));
		requisitions.add(new Requisition("Jose", "James Stewart Vol I","Livro de cálculo utilizado nos cursos de cálculo 1 e 2", true));
		requisitions.add(new Requisition("Jose", "gajekekvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv","Livro de cálculo utilizado nos cursos de cálculo 2,3 e 4", false));
		requisitions.add(new Requisition("Guga", "James Stewart","Livro de cálculo utilizado nos cursos de cálculo 1 e 2", false));
		requisitions.add(new Requisition("Guga", "James Stewart Vol II","Livro de cálculo utilizado nos cursos de cálculo 2,3 e 4", false));
		requisitions.add(new Requisition("Guga", "sjkdflgkddkfgjlkjhjkjhj","Livro de cálculo utilizado nos cursos de cálculo 1 e 2", false));
		requisitions.add(new Requisition("Guga", "James Stewart","Livro de cálculo utilizado nos cursos de cálculo 1 e 2", false));
		requisitions.add(new Requisition("Guga", "James Stewart Vol II","Livro de cálculo utilizado nos cursos de cálculo 2,3 e 4", false));
		if(i != 0){
			requisitions.add(new Requisition("Guga", "sjkdflgkddkfgjlkjhjkjhj","Livro de cálculo utilizado nos cursos de cálculo 1 e 2", false));
			requisitions.add(new Requisition("Guga", "James Stewart","Livro de cálculo utilizado nos cursos de cálculo 1 e 2", true));
			requisitions.add(new Requisition("Guga", "James Stewart Vol II","Livro de cálculo utilizado nos cursos de cálculo 2,3 e 4", false));
			requisitions.add(new Requisition("Guga", "sjkdflgkddkfgjlkjhjkjhj","Livro de cálculo utilizado nos cursos de cálculo 1 e 2", false));
			requisitions.add(new Requisition("Guga", "James Stewart","Livro de cálculo utilizado nos cursos de cálculo 1 e 2", false));
			requisitions.add(new Requisition("Guga", "James Stewart Vol II","Livro de cálculo utilizado nos cursos de cálculo 2,3 e 4", false));
			requisitions.add(new Requisition("Guga", "sjkdflgkddkfgjlkjhjkjhj","Livro de cálculo utilizado nos cursos de cálculo 1 e 2", false));
			requisitions.add(new Requisition("Guga", "James Stewart","Livro de cálculo utilizado nos cursos de cálculo 1 e 2", false));
			requisitions.add(new Requisition("Guga", "James Stewart Vol II","Livro de cálculo utilizado nos cursos de cálculo 2,3 e 4", false));
			requisitions.add(new Requisition("Guga", "sjkdflgkddkfgjlkjhjkjhj","Livro de cálculo utilizado nos cursos de cálculo 1 e 2", false));
			requisitions.add(new Requisition("Guga", "James Stewart","Livro de cálculo utilizado nos cursos de cálculo 1 e 2", false));
			requisitions.add(new Requisition("Guga", "James Stewart Vol II","Livro de cálculo utilizado nos cursos de cálculo 2,3 e 4", false));
			requisitions.add(new Requisition("Guga", "sjkdflgkddkfgjlkjhjkjhj","Livro de cálculo utilizado nos cursos de cálculo 1 e 2", false));
		}

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
				requisitionLabel = new JLabel(r.getName_client() + " request: " + r.getName_document());
				imageLabel = new JLabel();
				cs.gridx = 1;
				cs.anchor = GridBagConstraints.FIRST_LINE_START;

				if(userName.contentEquals(r.getName_client())){
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
									clientOperations.receiveFile(dr.getFile().getFileName(), dr.getFile().getFilePath());
								}
							}
						});
						imageLabel.addMouseListener(new MouseAdapter()  
						{  
							public void mouseClicked(MouseEvent e)  
							{  
								dr.setVisible(true);
								if(dr.getFile() != null){
									clientOperations.receiveFile(dr.getFile().getFileName(), dr.getFile().getFilePath());
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
