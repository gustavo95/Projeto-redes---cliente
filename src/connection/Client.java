package connection;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import fileManagement.FileOperations;
import fileManagement.FileToTransfer;

public class Client {

	private static Socket clientSocket;
	private static DataOutputStream outToServer;
	private static BufferedReader inFromServer;
	public static ArrayList<Requisition> list_of_requisitions  = new ArrayList<Requisition>();
	public static String nome;
	public static void main(String argv[]) throws Exception {

		System.out.println("CLIENTE INICIADO");

		try{

			Scanner sc = new Scanner(System.in);
			clientSocket = new Socket("localhost",6789);
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			while (true){

				System.out.println("DIGITE 1 PARA ENVIAR REQUISIÇÃO\n"
						+ "DIGITE 2 PARA ENVIAR DOCUMENTOS\n"
						+ "DIGITE 4 PARA PEDIR LISTA DE REQUISIÇÕES\n");
				int value = sc.nextInt();
				if (value == 1){
					System.out.println("DIGITE SEU NOME E DEPOIS ENTER ");
					Scanner x = new Scanner(System.in);
					nome = x.next();	

					Requisition req = new Requisition(nome, "James Stewart Vol II","Livro de cálculo utilizado nos cursos de cálculo 2,3 e 4", false, clientSocket);
					sendRequisition(req);

					//					Requisition req2 = new Requisition("Joana", "James Stewart Vol I","Livro de cálculo utilizado nos cursos de cálculo 1 e 2", false);
					//					sendRequisition(req2);
					//					outToServer.writeBytes("0 Exit\n");
					//					clientSocket.close();
				}
				if (value ==2){
					FileOperations fo = new FileOperations();
					FileToTransfer ftt = fo.readFile("C://Users//CésarAugusto//Pictures//water.jpg");
					sendFile(ftt);
					//					outToServer.writeBytes("0 Exit\n");
					//					clientSocket.close();
				}

				if (value ==4){

					askForList();
					//outToServer.writeBytes("0 Exit\n");
					//clientSocket.close();
				}
			}

		}catch(Exception e){
			System.out.println("Erro de conexão" + e.toString());
		}

	}

	public static void sendRequisition(Requisition req) throws IOException{
		String str = req.toString() + ";" + clientSocket.toString() + "\n";
		outToServer.writeBytes("1 SendingRequisition\n");
		outToServer.writeBytes(str);
		System.out.println(inFromServer.readLine());
	}

	public static void sendFile(FileToTransfer file) throws IOException{
		BufferedOutputStream bf = new BufferedOutputStream(clientSocket.getOutputStream());
		FileOperations fo = new FileOperations();	
		outToServer.writeBytes("2 SendingFile\n");
		bf.write(fo.serializeFile(file));
		bf.flush();
		System.out.println(inFromServer.readLine());
		bf.close();
	}
	public static void askForList() throws IOException, ClassNotFoundException{

		outToServer.writeBytes("4 AskList\n");
		ObjectInputStream in =   new ObjectInputStream(clientSocket.getInputStream());
		list_of_requisitions =(ArrayList<Requisition>) in.readObject();
		System.out.println(list_of_requisitions.size()+ "size");
		//		for (int i =0 ; i<list_of_requisitions.size(); i++){
		//			System.out.println(list_of_requisitions.get(i).getName_client());
		//			
		//		}
		

		ManagerRequisition manager = new ManagerRequisition(list_of_requisitions);
		manager.print(nome);
		
		
		//ManagerRequisition manager = new ManagerRequisition(list_of_requisitions);
		//System.out.println(manager.separateList(nome, list_of_requisitions)[0].size());
	}
}