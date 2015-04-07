package seis635.project.server;

import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

	final static int portNumber = 81;
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	
	public static void init(){
		try{
			serverSocket = new ServerSocket(portNumber);			
			//Debugging
			System.out.println("Server listening on port " + portNumber);
			
			clientSocket = serverSocket.accept();
			//Debugging
			System.out.println("Client connected to " + portNumber);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
