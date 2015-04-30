package seis635.project.server;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

public class CSView {

	private JFrame frame;
	private Container contentPane;
	private Border border;
	private JList userList;
	private JLabel serverOn, serverOff;
	private JButton startButton, stopButton;
	public JTextArea serverMsgWindow;
	private CSController controller;
	
	public CSView(CSController controller){
		this.controller = controller;	//Constructor dependency injection
		
		//Create frame, set size, center, and set exit on close
		frame = new JFrame("Chat Server");
		frame.setSize(450, 600);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create Border
		border = BorderFactory.createLineBorder(Color.gray);
		
		//Created just for testing
		String users[] = new String[10];
		
		//Create the Swing components
		startButton = new JButton("Start Server");
		stopButton = new JButton("Stop Server");
		
		userList = new JList<String>(users);
		userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		userList.setBorder(border);
		//userList.setVisibleRowCount(10);
		userList.setFixedCellWidth(200);
		
		serverMsgWindow = new JTextArea(10, 30);
		serverMsgWindow.setEditable(false);
		serverMsgWindow.setBorder(border);
		
		//Set up the content in frame
		contentPane = frame.getContentPane();
		contentPane.setLayout(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();
		cs.weightx = 0.1;
		cs.weighty = 0.1;
		
		cs.gridx = 0;
		cs.gridy = 0;
		contentPane.add(startButton, cs);
		
		cs.gridx = 0;
		cs.gridy = 1;
		contentPane.add(stopButton, cs);
		
		//Add components to contentPane
		cs.anchor = GridBagConstraints.PAGE_END;
		cs.fill = GridBagConstraints.HORIZONTAL;
		cs.gridheight = 2;
		contentPane.add(serverMsgWindow, cs);
		
		cs.gridx = 0;
		cs.gridy = 0;
		cs.anchor = GridBagConstraints.EAST;
		cs.fill = GridBagConstraints.VERTICAL;
		contentPane.add(userList, cs);
		
		frame.setContentPane(contentPane);
		frame.setVisible(true);
	}
}
