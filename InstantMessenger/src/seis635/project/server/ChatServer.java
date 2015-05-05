package seis635.project.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import seis635.project.cmn.Message;

public class ChatServer {

	final static int portNumber = 8001;
	private static ServerSocket serverSocket;
	private static InetAddress IP;
	private static Socket clientSocket;
	public static Map<String, CSHandler> users;
	
	private static CSView view;
	private static CSController controller;
	
	public static void init(){
		try{
			//Start server
			IP = InetAddress.getLocalHost();
			serverSocket = new ServerSocket(portNumber);
			controller.writeMsg("Server running on " + IP + ":" + portNumber);
			
			//Debugging on console
			System.out.println("Server running on " + IP + ":" + portNumber);
		} catch(Exception e){
			System.err.println("Server could not be started.");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	//The server listens and calls the method to log in new users
	public static void listen(){
		
		try{
			clientSocket = serverSocket.accept();
			controller.writeMsg("Client IP " + clientSocket.getLocalAddress().getHostAddress()
					+ " connected.");
			
			//Debugging on console
			System.out.println("Client IP " + clientSocket.getLocalAddress().getHostAddress()
					+ " connected.");
			
			loginUser(clientSocket);
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void loginUser(Socket clientSocket) throws IOException{

		try{
			ObjectInputStream objIn = new ObjectInputStream(clientSocket.getInputStream());
			ObjectOutputStream objOut = new ObjectOutputStream(clientSocket.getOutputStream());
			Message loginRequest = (Message) objIn.readObject();
			
			if(loginRequest.getType() == Message.LOGIN && !loginRequest.getData().equals("")){
				
				//Create new handler, log user in HashMap
				CSHandler handler = new CSHandler(clientSocket, objIn, objOut);
				users.put((String) loginRequest.getData(), handler);
				controller.writeMsg("User " + loginRequest.getData() + " logged in");
				
				//Confirm login to user
				objOut.writeObject(new Message(null, null, "SUCCESS", Message.SERVER_RESPONSE));
				objOut.flush();
				
				//Send current users to update GUI initially
				objOut.writeObject(new Message(null, null, getUsernames(), Message.UPDATE_USERS));
				objOut.flush();
				
				//Update users on Server GUI
				view.updateUsers(getUsernames());
			}
			else{
				objOut.writeObject(new Message(null, null, "FAIL", Message.SERVER_RESPONSE));
				objOut.flush();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();	
		}
	}
	
	public static String[] getUsernames(){
		return users.keySet().toArray(new String[users.size()]);
	}
	
	public static void main(String[] args){
		
		controller = new CSController();
		view = new CSView(controller);
		controller.setCSView(view);
		
		users = new HashMap<String, CSHandler>();
		
		init();				//Initialize the Chat Server
		
		while(true){
			listen();		//Listen on port indefinitely
		}
	}
	
	
	//Create member class as helper for ChatServer.  This class will contain
	//the Thread listening on the Port and will have access to all the ChatServer's
	//data.  The Listener will poll a message queue, which oddly enough is not 
	//implemented in Java except as a Linked List.
	/*public class Listener extends Thread {
		
		public synchronized void run(){
			try{
				while(true){
					
				}
			} catch(Exception e){
				
			}
		}
	}*/
}
