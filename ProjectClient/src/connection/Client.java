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
		System.out.println("CLIENTE INICIADO, DIGITE UM TEXTO: ");

		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
				System.in));
		
		try{
			
			
			
			Socket clientSocket = new Socket("localhost",6789);
			
			Requisition req = new Requisition("Jo�o", "James Stewart Vol II","Livro de c�lculo utilizado nos cursos de c�lculo 2,3 e 4", false);
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
