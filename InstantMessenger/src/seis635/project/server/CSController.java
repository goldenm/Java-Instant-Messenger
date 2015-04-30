package seis635.project.server;

import java.util.ArrayList;

import seis635.project.cmn.User;

public class CSController {

	private CSView view;
	
	public CSController(){}
	
	public void setCSView(CSView view){
		this.view = view;
	}
	
	//Writes messages to the View's message window
	public void writeMsg(String string){
		view.serverMsgWindow.append(">>> " + string + "\n");
	}
	
	//Starts the Server
	public void startServer(){
		ChatServer.init();
		ChatServer.listen();
	}
	
	public void stopServer(){
		ChatServer.shutdown();
	}
	
	//Change to a plain array because it's just easier to work
	//with on JList and other Swing components
	public String[] updateUserList(){
		
		ArrayList<User> users = ChatServer.getUsers();
		String userArray[] = new String[users.size()];
		//userArray = users.toArray(userArray);
		
		//TODO -- finish this
		for(User user : users){
			int index = 0;
			userArray[index] = user.getUsername();
		}
		
		return userArray;
	}
}
