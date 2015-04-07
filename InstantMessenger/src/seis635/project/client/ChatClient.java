package seis635.project.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {

	//Hard-code in server's IP Address and port
	//at this point for dev purposes
	final static String hostName = "192.168.1.84";
	final static int portNumber = 81;
	
	private static Socket socket;
	
	public static void connect(){
		try {
			socket = new Socket(hostName, portNumber);
			
			//Debugging
			System.out.println("Connected to server.");
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
		connect();
	}
}
