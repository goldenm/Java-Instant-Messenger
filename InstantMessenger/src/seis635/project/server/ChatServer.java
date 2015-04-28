package seis635.project.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.util.ArrayList;

import seis635.project.cmn.User;

public class ChatServer implements Remote {

	final static int portNumber = 8001;
	private static ServerSocket serverSocket;
	private static InetAddress IP;
	private static Socket clientSocket;
	private static ObjectInputStream objIn;
	private static ObjectOutputStream objOut;
	public static ArrayList<User> users;
	public static int userCounter;
	
	private static CSView view;
	private static CSController controller;
	
	//Code to initialize the basic server functionality without
	//setting it to listen on the specified port
	public static void init(){
		try{
			//Start server
			serverSocket = new ServerSocket(portNumber);			
			controller.writeMsg("Server running on " + IP + ":" + 
			portNumber);
			
			//Instantiate user ArrayList and userCounter
			//This will reset everything if the server is stopped and restarted
			users = new ArrayList<User>();
			userCounter = 0;
			
			//Debugging on console
			System.out.println("Server running on " + IP + ":" + 
			portNumber);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//Code to stop the server and stop it pretty hard
	//Additional functionality could be added here to notify users, etc.
	public static void shutdown(){
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		users = null;
	}
	
	//The server listens on the Port and calls method to log in new users
	public static void listen(){
		try{
			clientSocket = serverSocket.accept();
			controller.writeMsg("Client IP " + clientSocket.getLocalAddress().getHostAddress()
					+ " connected to port " + portNumber);
			//Debugging on console
			System.out.println("Client IP " + clientSocket.getLocalAddress().getHostAddress()
					+ " connected to " + portNumber);
			
			loginUser(clientSocket);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//Log in user and store them in the ArrayList of users
	public static void loginUser(Socket clientSocket) throws IOException{
		String tempUsername = "";
		try{
			objIn = new ObjectInputStream(clientSocket.getInputStream());
			objOut = new ObjectOutputStream(clientSocket.getOutputStream());
			tempUsername = (String) objIn.readObject();
			
			//Debugging
			//controller.writeMsg("User " + tempUsername + " logged in");
			
		}catch (ClassNotFoundException e) {
			e.printStackTrace();	
		}
		
		//If the data verifies, store them in users otherwise go back to listening
		if(clientSocket != null && tempUsername != ""){
			User tempUser = new User(clientSocket, tempUsername);
			users.add(userCounter, tempUser);
			controller.writeMsg("User " + tempUsername + " logged in");
			userCounter++;
			objOut.writeObject(new String("SUCCESS"));
			objOut.flush();
		}
		else{
			objOut.writeObject(new String("FAIL"));
			objOut.flush();
			listen();
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
