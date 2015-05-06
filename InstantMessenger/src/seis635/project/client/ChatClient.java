package seis635.project.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import seis635.project.cmn.Message;

public class ChatClient {

	final static int portNumber = 8001;
	private static String hostName = "";
	private static CCView view;
	private static ObjectInputStream cObjIn;
	private static ObjectOutputStream cObjOut;
	private static Socket socket;
	private static String username;
	public static HashMap<String, ChatWindow> chats;
	public static Queue<Message> msgQueue;
	
	public ChatClient() {
		view = new CCView();
		try {
			connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		msgQueue = new LinkedList<Message>();
		chats = new HashMap<String, ChatWindow>();
		
		Listener chatListener = new Listener();
		chatListener.start();
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
				
				//Initial update of users
				Message users = (Message) cObjIn.readObject();
				view.updateUsers((String[]) users.getData());
			}
			else if(confirmation.getData().equals("")){
				System.out.println("Username is blank.  Try connecting again with a username");
			}	
			else{
				System.out.println("Connection failed.  Username may not be unique or is blank.  Try again.");
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
		}
	}	
	
	//Parse Message -- determine the type of Message and act accordingly
	public void parseMessage(Message message){
		try {
			switch (message.getType()){
			case Message.MESSAGE: receiveMessage(message);
				break;
			case Message.UPDATE_USERS: view.updateUsers((String[]) message.getData());
				break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Receive message and route to correct ChatWindow
	public void receiveMessage(Message message){
		
		if(chats.containsKey(message.getSender())){
			chats.get(message.getSender()).receiveMessage(message.getSender(), (String) message.getData());
		}
		else{
			view.newChat(message);
			chats.get(message.getSender()).receiveMessage(message.getSender(), (String) message.getData());
		}
	}
	
	//Send Message
	public static void sendMessage(String recipient, String message, int type){
		Message msg = new Message(recipient, username, message, type);
		try {
			cObjOut.writeObject(msg);
			cObjOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setUsername(String user){
		username = user;
	}
	
	public static void setIP(String IP){
		hostName = IP;
	}
	
	public static String getUsername(){
		return username;
	}
	
	public static void shutdown(){
		//TODO - upon closing, send logout message that closes streams, removes user from
		sendMessage(null, "LOGOUT", Message.LOGOUT);
	}
	
	public static void main(String[] args) throws IOException{
		@SuppressWarnings("unused")
		ChatClient client = new ChatClient();
	}
	
	//Update 5/5/2015: Create member class as helper for ChatClient.  This 
	//class will contain the Thread listening on the Port and will have access 
	//to all the ChatClient's incoming data.  The Listener will poll a message 
	//queue.  Many synchronization issues can effectively be solved by
	//synchronizing this member class's run method and passing all Message objects
	//through it.
	public class Listener extends Thread {

		public synchronized void run(){
			try{
				
				while(true){
					//Probably need a lock here
					msgQueue.add((Message)cObjIn.readObject());
					parseMessage(msgQueue.poll());
				}
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}