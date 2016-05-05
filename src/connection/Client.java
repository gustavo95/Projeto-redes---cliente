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
import java.io.ObjectOutputStream;
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
	public static boolean state;
	public static User usuario;
	@SuppressWarnings("resource")
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
						+ "DIGITE 4 PARA PEDIR LISTA DE REQUISIÇÕES\n"
						+ "DIGITE 5 PARA CRIAR USUÁRIO\n"
						+ "DIGITE 6 PARA FAZER LOGIN");
				int value = sc.nextInt();
				if (value == 1){

					Scanner x = new Scanner(System.in);
					String name_document = x.next();	
					String description = x.next();
					sendRequisition(name_document, description);


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
				if (value == 5){
					Scanner x = new Scanner(System.in);
					String nome = x.next();	
					String email = x.next();
					String senha  =  x.next();
					createUser(nome, email,senha);
				}
				if (value ==6){

					Scanner x = new Scanner(System.in);
					String nome = x.next();	
					String email = x.next();
					String senha  =  x.next();
					login(nome, email, senha);
				}
			}

		}catch(Exception e){
			System.out.println("Erro de conexão" + e.toString());
		}

	}
	public static void login(String nome,String email,String senha  ) throws IOException{

		usuario  =  new User(nome, email, senha);

		outToServer.writeBytes("6 Login\n");
		ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

		out.writeObject(usuario);
		out.flush();

		String x =inFromServer.readLine();
		if (x.equals("Ok")){
			state = true;

		}
		else{
			state = false;


		}

	}

	public static void createUser(String nome, String email, String senha) throws IOException{

		User user =  new User(nome, email, senha);

		outToServer.writeBytes("5 CreateUser\n");
		ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

		out.writeObject(user);
		out.flush();

		String x = inFromServer.readLine();
		if (x.equals("Ok")){
			state = true;
		}
		else{
			state =false;
		}
	}

	public static void sendRequisition(String name_document, String description) throws IOException{
		outToServer.writeBytes("1 SendingRequisition\n");

		Requisition req = new Requisition(usuario, name_document,description, false, clientSocket);
		ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
		out.writeObject(req);
		out.flush();
		String x = inFromServer.readLine();
		if (x.equals("Ok")){
			state = true;
		}
		else{
			state = false;
		}
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
	@SuppressWarnings("unchecked")
	public static ArrayList<ArrayList<Requisition>>  askForList() throws IOException, ClassNotFoundException{

		outToServer.writeBytes("2 AskList\n");
		ObjectInputStream in =   new ObjectInputStream(clientSocket.getInputStream());
		list_of_requisitions =(ArrayList<Requisition>) in.readObject();
		ManagerRequisition manager = new ManagerRequisition(list_of_requisitions);
		return manager.separateList(usuario.getNome());



	}
}