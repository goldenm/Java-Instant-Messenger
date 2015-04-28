package seis635.project.cmn;

import java.net.Socket;

public class User {

	String username; //unique identifier
	Socket clientSocket;
	String IP;
	
	public User(Socket clientSocket, String username){
		this.clientSocket = clientSocket;
		this.username = username;
		this.IP = clientSocket.getLocalAddress().getHostName();
	}
	
	public String getUsername(){
		return username;
	}
}
