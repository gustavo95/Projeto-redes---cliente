package connection;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Requisition {

	String name_client;
	String name_document;
	String  description;
	boolean wasAttend;
	Socket socket_client;
	String finalRequisition;
	
	public Requisition(String name_client ,String name_document, String description, boolean wasAttend) {
	
		this.name_client = name_client;
		this.name_document = name_document;
		this.description = description;
		this.wasAttend = wasAttend;
		
		this.finalRequisition = name_client+ ";" + name_document +";" + description +";" + String.valueOf(wasAttend);
		
		
	}
	
	
	void sendRequisition(Socket socket_destiny) throws IOException{
		DataOutputStream outToServer = new DataOutputStream(socket_destiny.getOutputStream());
		//System.out.println(finalRequisition);
		String x = socket_destiny.toString();
		String str = finalRequisition + ";" + x;
		
		outToServer.writeBytes(str);
		
	}
	
	
	
	
	

}
