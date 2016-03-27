package connection;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Requisition {

	String name_client;
	String  description;
	boolean wasAttend;
	Socket socket_client;
	String finalRequisition;
	
	public Requisition(String name_client , String description, boolean wasAttend) {
	
		this.name_client = name_client;
		this.description = description;
		this.wasAttend = wasAttend;
		//this.socket_client = Socket.
		this.finalRequisition = name_client+ ";" + description +";" + String.valueOf(wasAttend);
		System.out.println("foi2");
		
	}
	
	
	void sendRequisition(Socket socket_destiny) throws IOException{
		DataOutputStream outToServer = new DataOutputStream(socket_destiny.getOutputStream());
		System.out.println(finalRequisition);
		outToServer.writeBytes(finalRequisition);
		
	}
	
	
	
	
	

}
