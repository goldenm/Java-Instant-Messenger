package seis635.project.client;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JTextArea;

public class CCView {

	private CCController controller;
	private JFrame frame;
	private Container contentPane;
	private JMenuBar menuBar;
	private JLabel userLabel;
	private JTextArea msgWindow, chatWindow;
	
	public CCView(CCController controller){
		this.controller = controller;	//Constructor dependency injection
		
		//Create frame, set size, center, and set exit on close
		frame = new JFrame("Chat Client");
		frame.setSize(800, 600);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = frame.getContentPane();
		
		frame.setContentPane(contentPane);
		frame.setVisible(true);
		
		LoginDialog loginDialog = new LoginDialog(frame);
		loginDialog.setLocationRelativeTo(frame);
		loginDialog.setVisible(true);
	}
}