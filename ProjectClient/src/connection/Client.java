package connection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
	public static void main(String argv[]) throws Exception {
		String sentence;
		String modifiedSentence;
		System.out.println("DIGITE UM TEXTO: ");

		//BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		//inFromUser.readLine();
		
		try{
			
			
			
			Socket clientSocket = new Socket("localhost",6789);
			System.out.println("CLIENTE INICIADO");
			Requisition req = new Requisition("Jo�o", "Livro de Calculo", false);
			req.sendRequisition(clientSocket);
			
			
			
			
//			DataOutputStream outToServer = new DataOutputStream(
//					clientSocket.getOutputStream());
//			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
//					clientSocket.getInputStream()));
//			sentence = inFromUser.readLine();
//			outToServer.writeBytes(sentence + '\n');
//			modifiedSentence = inFromServer.readLine();
//			System.out.println("FROM SERVER: " + modifiedSentence);

			clientSocket.close();
		}catch(Exception e){
			System.out.println("Erro de conex�o");
		}

	}
}
