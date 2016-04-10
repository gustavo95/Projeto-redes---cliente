package connection;
//CLIENT
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

public class Requisition implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name_client;
	private String name_document;
	private String description;
	private boolean wasAttend;
	private Socket socket;
	private ArrayList<Requisition> l;
	public Requisition(String name_client ,String name_document, String description, boolean wasAttend, Socket socket) {
		this.name_client = name_client;
		this.name_document = name_document;
		this.description = description;
		this.wasAttend = wasAttend;
		this.socket = socket;
	}
	public Requisition(ArrayList<Requisition> l) {
		this.l = l;
	}
	
	public String toString(){
		return name_client+ ";" + name_document +";" + description +";" + String.valueOf(wasAttend);
	}

	public String getName_client() {
		return name_client;
	}

	public void setName_client(String name_client) {
		this.name_client = name_client;
	}

	public String getName_document() {
		return name_document;
	}

	public void setName_document(String name_document) {
		this.name_document = name_document;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isWasAttend() {
		return wasAttend;
	}

	public void setWasAttend(boolean wasAttend) {
		this.wasAttend = wasAttend;
	}
	

}