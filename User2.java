package org.chat;

import java.awt.*;

import java.net.*;
import java.awt.event.*;
import java.io.*;

public class User2 implements Runnable,ActionListener {    //Socket
	
	TextField textfield;
	TextArea textarea;
	Button send;
	Button clear;

	Socket socket;
	
	DataInputStream inputStream;
	DataOutputStream outputStream;
	
	Thread thread;
	
	
	User2() {
		
		Frame frame = new Frame("USER-2");
		
		Label label = new Label("WELCOME USER-2");
		label.setBounds(190, 50, 250, 30);
		textarea = new TextArea();
		textarea.setBounds(100,100, 270, 200);
		textfield = new TextField();
		textfield.setBounds(100, 350, 200, 40);
		
		clear = new Button("Clear");
		clear.setBounds(100, 400, 60, 30);
		ActionListener clearin = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textarea.setText("");
				
			}	
		};
		clear.addActionListener(clearin);
		
		send = new Button("Send");
		send.setBounds(320,355 , 50, 30);
		//send.setBackground(Color.lightGray);
		send.addActionListener(this);
		
		try {
			socket = new Socket("localhost",1010);
			
			inputStream = new DataInputStream(socket.getInputStream());
			outputStream = new DataOutputStream(socket.getOutputStream());
		} 
		catch (Exception e) {
			
		}


		
		frame.add(label);
		frame.add(textarea);
		frame.add(textfield);
		frame.add(send);
		frame.add(clear);
		
		thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
		
		frame.setBackground(Color.pink);
		frame.setLayout(null);
		frame.setSize(500, 500);
		frame.setVisible(true);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
	}
	
	public static void main(String[] args){
		
		new User2();
		
	}

	public void run() {
		while(true) {
			try {
				String msg = inputStream.readUTF();
				textarea.append("user1 :"+msg+"\n");
			}
			catch(Exception e){
				
			}
		}
		
	}
	public void actionPerformed(ActionEvent arg0) {
				String msg = textfield.getText();
				textarea.append("user2 :"+msg+"\n");
				textfield.setText("");
				try {
					outputStream.writeUTF(msg);
					outputStream.flush();
				} catch (Exception e) {

				}
					
			} 
	
}
