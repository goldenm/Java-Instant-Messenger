package seis635.project.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {

	//Hard-code in server's IP Address and port
	//at this point for dev purposes
	final static String hostName = "172.16.214.125";
	final static int portNumber = 8001;
	private static CCController controller;
	private static CCView view;
	private static ObjectInputStream cObjIn;
	private static ObjectOutputStream cObjOut;
	private static Socket socket;
	private static String username;
	private static boolean connected;
	
	public static void connect(){
		try {
			socket = new Socket(hostName, portNumber);
			//Debugging
			System.out.println("Connected to server.");
			
			//Set up the streams
			cObjOut = new ObjectOutputStream(socket.getOutputStream());
			cObjIn = new ObjectInputStream(socket.getInputStream());
			
			//Try logging into the server with username provided in dialog box
			cObjOut.writeObject(username);
			cObjOut.flush();
			
			//Login confirmation is a String object from server
			String confirmation = "";
			try {
				confirmation = (String) cObjIn.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			if(confirmation.equals("SUCCESS")){
				System.out.println("Connection successful.");
				connected = true;
			}
			else if(confirmation.equals("")){
				System.out.println("Username is blank.  Try connecting again with a username");
				connected = false;
			}
			else{
				System.out.println("Connection failed.  Username may not be unique.  Try again.");
				connected = false;
			}
			
		} catch (UnknownHostException e) {
			System.err.println("Host " + hostName + " and Port " + portNumber
					+ " could not be resolved.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Unable to establish I/O connection with " +
				hostName + " and Port " + portNumber);
			e.printStackTrace();
		}
	}
	
	//Adding a main method to test out basic
	//functionality
	public static void main(String[] args){
		
		controller = new CCController();
		view = new CCView(controller);
		controller.setCCView(view);
		
		connect();
	}
	
	public static void setUsername(String user){
		username = user;
	}
}