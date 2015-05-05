package seis635.project.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import seis635.project.cmn.Message;

//CSHandler is a representation of the action needed to be performed when a
//Message type is sent between clients.  Each client will be assigned a Handler
//to hold the open ObjectStreams in the Hash map.
public class CSHandler extends Thread {

	private Socket socket;
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	
	public CSHandler(Socket socket, ObjectInputStream objIn, ObjectOutputStream objOut){
		this.socket = socket;
		this.objIn = objIn;
		this.objOut = objOut;
		start();
	}
	
	//Listens on the ObjectInputStream for incoming messages from client
	//to add them to the queue.
	public synchronized void run(){
		while(true){
			
			try {
				
				Message incoming = (Message) objIn.readObject();
				
				if(incoming.getType() == Message.MESSAGE){
					ChatServer.msgQueue.add(incoming);
				}
				
			} catch (java.net.SocketException e){
				System.err.println("User disconnected");
				try {
					objIn.close();
					objOut.close();
					break;
				} catch (IOException e1) {/*So many try catches*/}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendMessage(Message message) {
		try {
			objOut.writeObject(message);
			objOut.flush();
		} catch (IOException e) {
			System.err.println("Message from " + message.getSender() + " to " + 
					message.getRecipient() + " ran into some issues.");
			e.printStackTrace();
		}
		
	}
}
