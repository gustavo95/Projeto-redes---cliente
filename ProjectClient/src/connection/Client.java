package connection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	private static User user;
	
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
						+ "DIGITE 1 PARA ENVIAR REQUISIÇÃO\n"
						+ "DIGITE 2 PARA PEDIR LISTA DE REQUISIÇÕES\n"
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
					System.out.println("DIGITE SEU name E DEPOIS ENTER ");
					Scanner x = new Scanner(System.in);
					//name = x.next();	

					//Requisition req = new Requisition(name, "James Stewart Vol II","Livro de cálculo utilizado nos cursos de cálculo 2,3 e 4", false);
					//sendRequisition(req);

					//					Requisition req2 = new Requisition("Joana", "James Stewart Vol I","Livro de cálculo utilizado nos cursos de cálculo 1 e 2", false);
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
					//receiveFile(fo.getFileNameByLocation(fileServerLocation), fileServerLocation);
				}
			}

		}catch(Exception e){
			System.out.println("Erro de conexão" + e.toString());
			e.printStackTrace();
		}

	}
	
	//Fechar conexão
	public static void closeConection() throws IOException{
		outToServer.writeBytes("0 Exit\n");
		clientSocket.close();
	}
	
	//Realizar login de um usuario
	public static void login(String name,String email,String password  ) throws IOException{

		user  =  new User(name, email, password);

		outToServer.writeBytes("6 Login\n");
		ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

		out.writeObject(user);
		out.flush();

		System.out.println(inFromServer.readLine());

	}
	
	
	public static void createUser(String name, String email, String password) throws IOException{

		User user =  new User(name, email, password);

		outToServer.writeBytes("5 CreateUser\n");
		ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

		out.writeObject(user);
		out.flush();

		System.out.println(inFromServer.readLine());
	}
	
	//Enviar Requisição para o servidor
	public static void sendRequisition(Requisition req) throws IOException{
		String str = req.toString() + ";" + clientSocket.toString() + "\n";
		
		outToServer.writeBytes("1 SendingRequisition\n");
		outToServer.writeBytes(str);
		
		System.out.println(inFromServer.readLine());
	}
	
	//Receber lista de Requisições
	public static ArrayList<Requisition> askForList(){
		ArrayList<Requisition> requisitions = null;
		
		try {
			outToServer.writeBytes("2 AskList\n");

			ObjectInputStream in =   new ObjectInputStream(clientSocket.getInputStream());
			requisitions =(ArrayList<Requisition>) in.readObject();
			list_of_requisitions = requisitions;
			
			System.out.println(list_of_requisitions.size()+ "size");
			//		for (int i =0 ; i<list_of_requisitions.size(); i++){
			//			System.out.println(list_of_requisitions.get(i).getName_client());
			//			
			//		}


			ManagerRequisition manager = new ManagerRequisition(list_of_requisitions);


			//ManagerRequisition manager = new ManagerRequisition(list_of_requisitions);
			//System.out.println(manager.separateList(name, list_of_requisitions)[0].size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return requisitions;
	}

	//Enviar arquivo do cliente para o servidor
	public static void sendFile(String fileName, String fileLocation){
		try{
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

			}catch (FileNotFoundException e){
				System.out.println("O arquivo especificado não foi encontrado");
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
		}catch(IOException e){
			//do something
		}
	}

	//Receber arquivo enviado pelo servidor
	public static void receiveFile(String fileName, String fileServerLocation, String folder){
		try{
			Socket dataSocket = null;
			FileOutputStream fos = null;
			InputStream is = null;

			try{
				outToServer.writeBytes("4 ReceiveFile\n");

				outToServer.writeBytes(fileServerLocation + "\n");

				dataSocket = new Socket("localhost",6790);

				is = dataSocket.getInputStream();

				// Cria arquivo local no cliente
				fos = new FileOutputStream(new File(folder + "//" + fileName));

				// Prepara variaveis para transferencia
				byte[] cbuffer = new byte[1024];
				int bytesRead;

				// Copia conteudo do canal
				while ((bytesRead = is.read(cbuffer)) != -1) {
					fos.write(cbuffer, 0, bytesRead);
					fos.flush();
				}

			}catch (FileNotFoundException e){
				System.out.println("O arquivo especificado não foi encontrado");
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
		}catch(IOException e){
			//do something
		}
	}
	
	public User getActiveUser(){
		return user;
	}
}
