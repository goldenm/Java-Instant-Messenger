package seis635.project.cmn;

import java.io.Serializable;

public class Message implements Serializable {

	//Define type of message
	public static final int MESSAGE = 0;
	public static final int LOGIN = 1;
	public static final int LOGOUT = 2;
	public static final int UPDATE_USERS = 3;
	public static final int SERVER_RESPONSE = 4;
	
	private String recipient;
	private String sender;
	private Object data;
	private int type;
	
	//Changed String message to Object so the Message class can handle logging 
	//in, sending the list of users, and 
	public Message(String recipient, String sender, Object data, int type) {
		this.recipient = recipient;
		this.sender = sender;
		this.data = data;
		this.type = type;
	}
	
	public String getRecipient() {
		return recipient;
	}

	public String getSender() {
		return sender;
	}

	public Object getData() {
		return data;
	}


	public int getType() {
		return type;
	}
}
