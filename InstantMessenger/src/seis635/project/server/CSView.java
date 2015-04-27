package seis635.project.server;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextArea;

public class CSView {

	private JFrame frame;
	private Container contentPane;
	public JList userWindow;
	public JTextArea serverMsgWindow;
	private JDialog dialog;
	private CSController controller;
	
	public CSView(CSController controller){
		this.controller = controller;	//Constructor dependency injection
		
		//Create frame, set size, center, and set exit on close
		frame = new JFrame("Chat Server");
		frame.setSize(450, 600);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create the Swing components
		//TO-DO add some borders to components
		serverMsgWindow = new JTextArea(10, 30);
		serverMsgWindow.setEditable(false);
		
		//Set up the content in frame
		contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		//Add components to contentPane
		contentPane.add(serverMsgWindow, BorderLayout.PAGE_END);
		
		frame.setContentPane(contentPane);
		frame.setVisible(true);
	}
}
