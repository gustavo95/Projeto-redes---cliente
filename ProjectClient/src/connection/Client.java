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

import requisitionManagement.Requisition;

public class Client {
	
	private boolean isConnected;
	private Socket clientSocket;
	private DataOutputStream outToServer;
	private BufferedReader inFromServer;
	private User user;
	private boolean status;
	
	public Client(){
		try{
			clientSocket = new Socket("localhost",6789);
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			isConnected = true;
		}catch(IOException e){
			isConnected = false;
		}
	}
	
	//Fechar conexão
	public void closeConection(){
		try{
		outToServer.writeBytes("0 Exit\n");
		clientSocket.close();
		isConnected = false;
		}catch(IOException e){
			//TODO
		}
	}
	
	//Realizar login de um usuario
	public void login(String email, String password){
		Socket dataSocket = null;
		ObjectOutputStream out = null;
		
		try{
			user  =  new User(null, email, password);

			outToServer.writeBytes("6 Login\n");
			
			dataSocket = new Socket("localhost",6790);
			out = new ObjectOutputStream(dataSocket.getOutputStream());

			out.writeObject(user);
			out.flush();

			String x = inFromServer.readLine();
			if (x.equals("NotOk")){
				status = false;
			}
			else{
				user.setName(x);
				status = true;
			}
		}catch (IOException e){
			status = false;
		}finally {		
			if (out != null) {
				try {
					out.close();
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
		}
	}
	
	//Criar novo usuario
	public void createUser(String name, String email, String password){
		Socket dataSocket = null;
		ObjectOutputStream out = null;
		
		try{
			User user =  new User(name, email, password);

			outToServer.writeBytes("5 CreateUser\n");
			
			dataSocket = new Socket("localhost",6790);
			out = new ObjectOutputStream(dataSocket.getOutputStream());

			out.writeObject(user);
			out.flush();

			String x = inFromServer.readLine();
			if (x.equals("Ok")){
				status = true;
			}
			else{
				status = false;
			}
		}catch(IOException e){
			status = false;
		}finally {		
			if (out != null) {
				try {
					out.close();
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
		}
	}
	
	//Enviar Requisição para o servidor
	public void sendRequisition(String name_document, String description){
		Socket dataSocket = null;
		ObjectOutputStream out = null;

		try{
			outToServer.writeBytes("1 SendingRequisition\n");

			Requisition req = new Requisition(user, name_document, description, false);

			dataSocket = new Socket("localhost",6790);
			out = new ObjectOutputStream(dataSocket.getOutputStream());

			out.writeObject(req);
			out.flush();

			String x = inFromServer.readLine();
			if (x.equals("Ok")){
				status = true;
			}
			else{
				status = false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {		
			if (out != null) {
				try {
					out.close();
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
		}
	}
	
	//Receber lista de Requisições
	@SuppressWarnings("unchecked")
	public ArrayList<Requisition> askForList(){
		Socket dataSocket = null;
		ObjectInputStream in = null;
		ArrayList<Requisition> requisitions = null;
		
		try {
			outToServer.writeBytes("2 AskList\n");
			
			dataSocket = new Socket("localhost",6790);
			in =  new ObjectInputStream(dataSocket.getInputStream());
			
			requisitions =(ArrayList<Requisition>) in.readObject();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {		
			if (in != null) {
				try {
					in.close();
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
		}
		
		return requisitions;
	}

	//Enviar arquivo do cliente para o servidor
	public void sendFile(String fileName, String fileLocation){
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
			//TODO
		}
	}

	//Receber arquivo enviado pelo servidor
	public void receiveFile(String fileName, String fileServerLocation, String folder){
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
	
	public boolean getStatus(){
		return status;
	}
	
	public boolean isConnected(){
		return isConnected;
	}
}
