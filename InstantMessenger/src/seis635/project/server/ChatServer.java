package seis635.project.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.util.ArrayList;

public class ChatServer implements Remote {

	final static int portNumber = 8001;
	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	private static InetAddress IP;
	
	private static CSView view;
	private static CSController controller;
	
	//Code to initialize the basic server functionality without
	//setting it to listen on the specified port
	public static void init(){
		try{
			serverSocket = new ServerSocket(portNumber);			
			controller.writeMsg("Server running on " + IP + ":" + 
			portNumber);
			
			//Debugging on console
			System.out.println("Server running on " + IP + ":" + 
			portNumber);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void listen(){
		try{
			clientSocket = serverSocket.accept();
			controller.writeMsg("Client connected to " + portNumber);
			
			//Debugging on console
			System.out.println("Client connected to " + portNumber);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		
		controller = new CSController();
		view = new CSView(controller);
		controller.setCSView(view);
		
		//Get local IP address to display in Frame
		//Maybe toss this in a getter if needed later in other classes
		try{
			IP = InetAddress.getLocalHost();
		}catch(IOException e){
			e.printStackTrace();
		}
			
		init();				//Initialize the Chat Server
		listen();			//Listen on port, log
	}
}
