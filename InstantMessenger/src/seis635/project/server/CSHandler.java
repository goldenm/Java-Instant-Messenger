package seis635.project.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

//CSHandler is a representation of the action needed to be performed when a
//Message type is sent between clients.  Each client will be assigned a Handler
//to hold the open ObjectStreams in the Hash map.
public class CSHandler {

	private Socket socket;
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	
	public CSHandler(Socket socket, ObjectInputStream objIn, ObjectOutputStream objOut){
		this.socket = socket;
		this.objOut = objOut;
		this.objIn = objIn;
	}
}
