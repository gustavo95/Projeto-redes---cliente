package connection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import fileManagement.FileOperations;
import requisitionManagement.ManagerRequisition;
import requisitionManagement.Requisition;

public class Client {
	
	private static Socket clientSocket;
	private static DataOutputStream outToServer;
	private static BufferedReader inFromServer;
	private static ArrayList<Requisition> list_of_requisitions  = new ArrayList<Requisition>();
	private static String nome;
	
	public static void main(String argv[]) throws Exception {

		System.out.println("CLIENTE INICIADO");

		try{
			boolean isConnected = true;
			Scanner sc = new Scanner(System.in);
			clientSocket = new Socket("localhost",6789);
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			
			while (isConnected){

				System.out.println("DIGITE 0 PARA SAIR\n"
						+ "DIGITE 1 PARA ENVIAR REQUISI��O\n"
						+ "DIGITE 2 PARA PEDIR LISTA DE REQUISI��ES\n"
						+ "DIGITE 3 PARA ENVIAR DOCUMENTOS\n"
						+ "DIGITE 4 PARA PEDIR DOCUMENTO\n"
						);
				int value = sc.nextInt();
				if(value == 0){
					sc.close();
					closeConection();
					isConnected = false;
				}
				if(value == 1){
					System.out.println("DIGITE SEU NOME E DEPOIS ENTER ");
					Scanner x = new Scanner(System.in);
					nome = x.next();	

					Requisition req = new Requisition(nome, "James Stewart Vol II","Livro de c�lculo utilizado nos cursos de c�lculo 2,3 e 4", false);
					sendRequisition(req);

					//					Requisition req2 = new Requisition("Joana", "James Stewart Vol I","Livro de c�lculo utilizado nos cursos de c�lculo 1 e 2", false);
					//					sendRequisition(req2);
					//x.close();
				}
				if(value == 2){
					askForList();
				}
				if(value == 3){
					String fileLocation = "";
					FileOperations fo = new FileOperations();
					sendFile(fo.getFileNameByLocation(fileLocation), fileLocation);
				}
				if(value == 4){
					String fileServerLocation = "";
					FileOperations fo = new FileOperations();
					receiveFile(fo.getFileNameByLocation(fileServerLocation), fileServerLocation);
				}
			}

		}catch(Exception e){
			System.out.println("Erro de conex�o" + e.toString());
			e.printStackTrace();
		}

	}
	
	//Fechar conex�o
	public static void closeConection() throws IOException{
		outToServer.writeBytes("0 Exit\n");
		clientSocket.close();
	}
	
	//Enviar Requisi��o para o servidor
	public static void sendRequisition(Requisition req) throws IOException{
		String str = req.toString() + ";" + clientSocket.toString() + "\n";
		
		outToServer.writeBytes("1 SendingRequisition\n");
		outToServer.writeBytes(str);
		
		System.out.println(inFromServer.readLine());
	}
	
	//Receber lista de Requisi��es
	public static void askForList() throws IOException, ClassNotFoundException{

		outToServer.writeBytes("2 AskList\n");
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

	//Enviar arquivo do cliente para o servidor
	public static void sendFile(String fileName, String fileLocation) throws IOException{
		Socket dataSocket = null;
		FileInputStream fileIn = null;
		OutputStream os = null;

		try{
			outToServer.writeBytes("3 SendFile\n");
			outToServer.writeBytes(fileName + "\n");
			
			dataSocket = new Socket("localhost",6790);

			// Criando tamanho de leitura
			byte[] cbuffer = new byte[1024];
			int bytesRead;

			// Criando arquivo que sera transferido pelo cliente
			File file = new File(fileLocation);
			fileIn = new FileInputStream(file);

			// Criando canal de transferencia
			os = dataSocket.getOutputStream();

			// Lendo arquivo criado e enviado para o canal de transferencia
			while ((bytesRead = fileIn.read(cbuffer)) != -1) {
				os.write(cbuffer, 0, bytesRead);
				os.flush();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {		
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (dataSocket != null) {
				try {
					dataSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileIn != null) {
				try {
					fileIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println(inFromServer.readLine());
	}
	
	//Receber arquivo enviado pelo servidor
	public static void receiveFile(String fileName, String fileServerLocation) throws IOException{
		Socket dataSocket = null;
		FileOutputStream fos = null;
		InputStream is = null;

		try{
			outToServer.writeBytes("4 ReceiveFile\n");
			
			outToServer.writeBytes(fileServerLocation + "\n");
			
			dataSocket = new Socket("localhost",6790);
			
			is = dataSocket.getInputStream();

			// Cria arquivo local no cliente
			fos = new FileOutputStream(new File("C://Users//guga//Documents//" + fileName));

			// Prepara variaveis para transferencia
			byte[] cbuffer = new byte[1024];
			int bytesRead;

			// Copia conteudo do canal
			while ((bytesRead = is.read(cbuffer)) != -1) {
				fos.write(cbuffer, 0, bytesRead);
				fos.flush();
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if (dataSocket != null) {
				try {
					dataSocket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		System.out.println(inFromServer.readLine());
	}
}
