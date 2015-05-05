package seis635.project.client;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

import seis635.project.cmn.Message;

@SuppressWarnings("unused")
public class CCView {

	private JFrame frame;
	private Container contentPane;
	private Border border;
	private DefaultListModel<String> model;
	@SuppressWarnings("rawtypes")
	private JList userList;
	
	public CCView(){
		
		//Create frame, set size, center, and set exit on close
		frame = new JFrame("Chat Client");
		frame.setSize(250, 500);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create Border
		border = BorderFactory.createLineBorder(Color.gray);
		
		//Set up pane
		contentPane = frame.getContentPane();
		
		//Create userList
		model = new DefaultListModel<String>();
		userList = new JList<String>(model);
		userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		userList.setBorder(border);
		
		contentPane.add(userList);
		
		frame.setContentPane(contentPane);
		frame.setVisible(true);
		
		LoginDialog loginDialog = new LoginDialog(frame);
		loginDialog.setLocationRelativeTo(frame);
		loginDialog.setVisible(true);
		
		userList.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
		    	if(e.getClickCount()==2){
		    		String recipient = (String) userList.getSelectedValue();
		    		new ChatWindow(recipient, frame);
		        }
		    }
		});
	}
	
	public void updateUsers(String[] userArray){

		model.clear();
		
		for(int i = 0; i < userArray.length; i++){
			model.add(i, userArray[i]);
		}
	}

	public void receiveMessage(Message incoming) {
		// TODO If no ChatWindow is open for this chat, open a chat window with this message
		//Debugging to test:
		System.out.println("Message received: " + incoming.getData());
	}
}
