package connection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import fileManagement.FileOperations;

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
			
			Requisition req = new Requisition("João", "James Stewart Vol II","Livro de cálculo utilizado nos cursos de cálculo 2,3 e 4", false);
			sendRequisition(req);
			
			Requisition req2 = new Requisition("Joana", "James Stewart Vol I","Livro de cálculo utilizado nos cursos de cálculo 1 e 2", false);
			sendRequisition(req2);
			
			String fileLocation = "C://Users//guga//Pictures//Saved Pictures//DS3.jpg";
			FileOperations fo = new FileOperations();
			
			sendFile(fo.getFileNameByLocation(fileLocation) , fileLocation);
			
			fileLocation = "C://Users//guga//Documents//Redes//final-fantasy-xv.jpg";
			
			receiveFile(fo.getFileNameByLocation(fileLocation) , fileLocation);
			
			closeConection();
		}catch(Exception e){
			System.out.println("Erro de conexão");
		}

	}
	
	public static void closeConection() throws IOException{
		outToServer.writeBytes("0 Exit\n");
		clientSocket.close();
	}
	
	public static void sendRequisition(Requisition req) throws IOException{
		String str = req.toString() + ";" + clientSocket.toString() + "\n";
		
		outToServer.writeBytes("1 SendingRequisition\n");
		outToServer.writeBytes(str);
		
		System.out.println(inFromServer.readLine());
	}

	//Enviar arquivo do cliente para o servidor
	public static void sendFile(String fileName, String fileLocation) throws IOException{
		Socket dataSocket = null;
		FileInputStream fileIn = null;
		OutputStream os = null;

		try{
			outToServer.writeBytes("2 SendFile\n");
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
			outToServer.writeBytes("3 ReceiveFile\n");
			
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
