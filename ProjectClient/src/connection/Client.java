package connection;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import fileManagement.FileOperations;
import fileManagement.FileToTransfer;

public class Client {
	
	private static Socket clientSocket;
	private static DataOutputStream outToServer;
	private static BufferedReader inFromServer;
	
	public static void main(String argv[]) throws Exception {
		System.out.println("CLIENTE INICIADO");
		
		try{
			
			clientSocket = new Socket("localhost",6789);
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			
			FileOperations fo = new FileOperations();
			FileToTransfer ftt = fo.readFile("C://Users//guga//Pictures//Saved Pictures//DS3.jpg");
			
			Requisition req = new Requisition("João", "James Stewart Vol II","Livro de cálculo utilizado nos cursos de cálculo 2,3 e 4", false);
			sendRequisition(req);
			
			Requisition req2 = new Requisition("Joana", "James Stewart Vol I","Livro de cálculo utilizado nos cursos de cálculo 1 e 2", false);
			sendRequisition(req2);
			
			sendFile(ftt);
			
			outToServer.writeBytes("0 Exit\n");

			clientSocket.close();
		}catch(Exception e){
			System.out.println("Erro de conexão");
		}

	}
	
	public static void sendRequisition(Requisition req) throws IOException{
		String str = req.toString() + ";" + clientSocket.toString() + "\n";
		outToServer.writeBytes("1 SendingRequisition\n");
		outToServer.writeBytes(str);
		System.out.println(inFromServer.readLine());
	}
	
	public static void sendFile(FileToTransfer file) throws IOException{
		BufferedOutputStream bf = new BufferedOutputStream(clientSocket.getOutputStream());;
		FileOperations fo = new FileOperations();	
		outToServer.writeBytes("2 SendingFile\n");
		bf.write(fo.serializeFile(file));
		bf.flush();
		System.out.println(inFromServer.readLine());
		bf.close();
	}
}
