package seis635.project.server;

import java.util.ArrayList;

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
}
