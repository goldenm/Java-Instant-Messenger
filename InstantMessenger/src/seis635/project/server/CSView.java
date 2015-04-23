package seis635.project.server;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class CSView {

	private JFrame frame;
	private Container contentPane;
	private JTextArea userWindow;
	private JTextArea serverMsgWindow;
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
		
		//Set up the content in frame
		contentPane = frame.getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		frame.setContentPane(contentPane);
		frame.setVisible(true);
	}
}
