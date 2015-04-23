package seis635.project.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;

public class ChatServer implements Remote {

	final static int portNumber = 8001;
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	
	static CSView view;
	static CSController controller;
	
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
	
	public static void main(String[] args){
		
		controller = new CSController();
		view = new CSView(controller);
		controller.setCSView(view);
		init();
	}
}
