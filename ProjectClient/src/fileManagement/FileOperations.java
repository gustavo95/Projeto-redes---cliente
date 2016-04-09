package fileManagement;

public class FileOperations {
	
	public String getFileNameByLocation(String location){
		String aux = location.replace("//", "");
		int i = location.length() - aux.length();
		return location.split("\\/")[i];
	}
	
}
