package fileManagement;

public class FileOperations {
	private String fileName;
	private String filePath;
	
	public FileOperations() {
		this.fileName = null;
		this.filePath = null;
	}
	
	public FileOperations(String filePath) {
		this.fileName = getFileNameByLocation(filePath);
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getFileNameByLocation(String location){
		String aux = location.replace("//", "");
		int i = location.length() - aux.length();
		return location.split("\\/")[i];
	}
	
}
