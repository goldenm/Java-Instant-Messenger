package seis635.project.client;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//Not the prettiest of dialog boxes, but close enough for government work
public class LoginDialog extends JDialog {

	private JPanel panel;
	private JLabel text;
	private JTextField username;
	private JButton login, cancel;
	
	public LoginDialog(JFrame parent){
		super(parent, "Login", true);
		
		//Create panel and layout for panel
		panel = new JPanel(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();
		cs.weightx = 0.1;
		cs.weighty = 0.1;
		
		//Set up components and add them to panel
		cs.gridy = 0;
		text = new JLabel("Enter your username:");
		panel.add(text, cs);
		
		cs.gridy = 1;
		cs.gridwidth = 2;
		username = new JTextField(20);
		panel.add(username, cs);
		
		cs.gridx = 0;
		cs.gridy = 2;
		cs.gridwidth = 1;
		login = new JButton("Login");
		panel.add(login, cs);
		
		cs.gridx = 1;
		cs.gridy = 2;
		cancel = new JButton("Cancel");
		panel.add(cancel, cs);
		
		this.setContentPane(panel);
		this.setSize(300, 200);
		
		//Add button listeners to both buttons
		login.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent event) {
				ChatClient.setUsername(username.getText().trim());
				dispose();
			}});
		
		cancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				//Or change to dispose() if you just want the box to go away
				System.exit(0);
			}});
	}
}