package requisitionManagement;
//CLIENT
import java.io.Serializable;

import connection.User;

public class Requisition implements Serializable{

	private static final long serialVersionUID = 1L;
	private User user;
	private String name_document;
	private String description;
	private boolean wasAttend;
	
	public Requisition(User user ,String name_document, String description, boolean wasAttend) {
		this.user = user;
		this.name_document = name_document;
		this.description = description;
		this.wasAttend = wasAttend;
	}
	
	public User getUser() {
		return user;
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