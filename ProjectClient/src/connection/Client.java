package connection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.SingleSelectionModel;

import fileManagement.FileOperations;
import fileManagement.FileToTransfer;

public class Client {
	public static void main(String argv[]) throws Exception {

		String modifiedSentence;
		System.out.println("CLIENTE INICIADO");
		
		try{
			
			
			
			Socket clientSocket = new Socket("localhost",6789);
			
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			
			FileOperations fo = new FileOperations();
			FileToTransfer ftt = fo.readFile("C://Users//guga//Pictures//Saved Pictures//DS3.jpg");
			
			Requisition req = new Requisition("Jo�o", "James Stewart Vol II","Livro de c�lculo utilizado nos cursos de c�lculo 2,3 e 4", false);
			req.sendRequisition(clientSocket);
			Requisition req2 = new Requisition("Joana", "James Stewart Vol I","Livro de c�lculo utilizado nos cursos de c�lculo 1 e 2", false);
			req2.sendRequisition(clientSocket);
			//fo.sendFile(clientSocket, ftt);

			modifiedSentence = inFromServer.readLine();
			System.out.println("FROM SERVER: " + modifiedSentence);

			clientSocket.close();
		}catch(Exception e){
			System.out.println("Erro de conex�o");
		}

	}
}
