package seis635.project.cmn;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class User {

	String username; //unique identifier
	Socket clientSocket;
	String IP;
	ObjectInputStream objIn;
	ObjectOutputStream objOut;
	
	public User(Socket clientSocket, String username, ObjectInputStream objIn, ObjectOutputStream objOut){
		this.clientSocket = clientSocket;
		this.username = username;
		this.IP = clientSocket.getLocalAddress().getHostName();
		this.objIn = objIn;
		this.objOut = objOut;
	}
	
	public String getUsername(){
		return username;
	}
}
