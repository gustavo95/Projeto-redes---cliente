package connection;

public class Requisition {

	private String name_client;
	private String name_document;
	private String description;
	private boolean wasAttend;
	
	public Requisition(String name_client ,String name_document, String description, boolean wasAttend) {
		this.name_client = name_client;
		this.name_document = name_document;
		this.description = description;
		this.wasAttend = wasAttend;
	}
	
	public String toString(){
		return name_client+ ";" + name_document +";" + description +";" + String.valueOf(wasAttend);
	}

}
