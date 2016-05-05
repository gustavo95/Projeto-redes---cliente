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
	private User usuario;
	private String name_document;
	private String description;
	private boolean wasAttend;
	transient private Socket socket;
	private ArrayList<Requisition> l;
	public Requisition(User usuario,String name_document, String description, boolean wasAttend, Socket socket) {
		super ();
		this.usuario = usuario;
		this.name_document = name_document;
		this.description = description;
		this.wasAttend = wasAttend;
		this.socket = socket;
	}
	public Requisition(ArrayList<Requisition> l) {
		this.l = l;
	}
	
//	public String toString(){
//		return name_client+ ";" + name_document +";" + description +";" + String.valueOf(wasAttend);
//	}
	
	

	public String getName_document() {
		return name_document;
	}

	public User getUsuario() {
		return usuario;
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
