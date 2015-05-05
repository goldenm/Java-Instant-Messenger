package seis635.project.client;

import java.awt.Color;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class ChatWindow {

	private JFrame frame;
	private Container contentPane;
	private Border border;
	private JMenuBar menuBar;
	private JLabel userLabel;
	private JTextArea msgWindow, chatWindow;
	
	public ChatWindow(){
		
		//Create frame, set size, center, and set exit on close
		frame = new JFrame("Chat Client");
		frame.setSize(800, 600);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create Border
		border = BorderFactory.createLineBorder(Color.gray);
		
		//Set up pane
		contentPane = frame.getContentPane();
		
		frame.setContentPane(contentPane);
		frame.setVisible(true);
		
	}
}
