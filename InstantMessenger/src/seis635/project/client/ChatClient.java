package seis635.project.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Set;

import seis635.project.cmn.Message;

public class ChatClient {

	final static int portNumber = 8001;
	private static String hostName = "";
	private static CCView view;
	private static ObjectInputStream cObjIn;
	private static ObjectOutputStream cObjOut;
	private static Socket socket;
	private static String username;
	private static boolean connected;
	
	public ChatClient() {
		view = new CCView();
		try {
			connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void connect() throws IOException{
		try {
			socket = new Socket(hostName, portNumber);
			
			//Debugging
			System.out.println("Connected to server.");

			//Set up the streams
			cObjOut = new ObjectOutputStream(socket.getOutputStream());
			cObjIn = new ObjectInputStream(socket.getInputStream());

			//Try logging into the server with username provided in dialog box
			cObjOut.writeObject(new Message(null, null, username, Message.LOGIN));
			cObjOut.flush();

			//Login confirmation is a String object from server
			Message confirmation;

			confirmation = (Message) cObjIn.readObject();

			if(confirmation.getData().equals("SUCCESS")){
				System.out.println("Connection successful.");
				connected = true;
				
				//Initial update of users
				Message users = (Message) cObjIn.readObject();
				view.updateUsers((String[]) users.getData());
			}
			else if(confirmation.getData().equals("")){
				System.out.println("Username is blank.  Try connecting again with a username");
				connected = false;
			}	
			else{
				System.out.println("Connection failed.  Username may not be unique or is blank.  Try again.");
				connected = false;
			}
		} catch (ClassNotFoundException e) {
			System.err.println("Wrong class returned.");
			e.printStackTrace();
		} catch (UnknownHostException e) {
			System.err.println("Host " + hostName + " and Port " + portNumber
					+ " could not be resolved.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Unable to establish I/O connection with " +
					hostName + " and Port " + portNumber);
			e.printStackTrace();
		}	finally{
			cObjIn.close();
			cObjOut.close();
		}
	}
	
	//Adding a main method to test out basic
	//functionality
	public static void main(String[] args) throws IOException{
		@SuppressWarnings("unused")
		ChatClient client = new ChatClient();
	}
	
	public static void setUsername(String user){
		username = user;
	}
	
	public static void setIP(String IP){
		hostName = IP;
	}
}