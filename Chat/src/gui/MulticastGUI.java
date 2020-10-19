/***
 * Multicast GUI.
 *
 * @since 13/10/20
 * @author Stefan Ristovksi, Aydin Akaydin
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.net.DatagramPacket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import stream.MulticastClient;;

public class MulticastGUI extends JFrame {

	/**
	 * username chosen by client
	 **/
	String userName = "";

	/**
	 * instance of Multicast client
	 **/
	MulticastClient mcc = new MulticastClient(this);

	/**
	 * button to send message
	 **/
	public JButton sendButton;
	/**
	 * button to join the chat
	 **/
	public JButton joinButton;
	/**
	 * button to leave the chat
	 **/
	public JButton leaveButton;

	/**
	 * address label
	 **/
	JLabel addrLabel = new JLabel("Address :");
	/**
	 * port label
	 **/
	JLabel portLabel = new JLabel("Port :");

	/**
	 * text field for IP address.
	 **/
	JTextField addrField;
	/**
	 * text field for port number
	 **/
	JTextField portField;
	/**
	 * text field for outgoing message
	 **/
	JTextField sendField;
	/**
	 * text area to display chat
	 **/
	JTextArea chatField;

	/**
	 * Main frame of window
	 **/
	JPanel mainFrame = new JPanel(new BorderLayout());
	/**
	 * Top frame of window
	 **/
	JPanel northFrame = new JPanel( new FlowLayout());
	/**
	 * Bottom frame of window
	 **/
	JPanel southFrame = new JPanel(new FlowLayout());
	/**
	 * Left frame of windows
	 **/
	JPanel westFrame = new JPanel( new BorderLayout());

	/**
	 * Constructor.
	 * Initialises the GUI and adds an action listener for the Join button, Send button, leave button, and the text areas
	 **/
	public MulticastGUI() {
		
		super("Multicast GUI");
		this.setSize(600,800);
		//this.setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainFrame.setBackground( new Color(147,112,219));
		northFrame.setBackground(new Color(147,112,219));
		southFrame.setBackground(new Color(147,112,219));
		
		this.setContentPane(mainFrame);
		
		sendField = new JTextField(45);
		addrField = new JTextField("228.5.6.7",10);
		portField = new JTextField("6789",10);
		
		// buttons
		sendButton = new JButton("SEND");
		joinButton = new JButton("JOIN");
		leaveButton = new JButton("LEAVE");
		
		joinButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String addr = addrField.getText();
					int port = Integer.parseInt(portField.getText());
					mcc.Join(addr, port);
					chatField.append("	Joined "+addr+" - "+port+"\n");
					if( userName == "") {
						chatField.append("	Please enter a username: \n");
						sendField.setText("ENTER USERNAME HERE");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		sendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					try {
						send();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			}
		});

		sendField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					try {
						send();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			}
		});
		sendField.addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
					sendField.setText("");
			}
			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
	    });
		
		
		leaveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mcc.recieve.stop();
				chatField.append("	bye\n");
				try {
				String s = "	" + userName + " has left";
				DatagramPacket pack = new DatagramPacket(s.getBytes(),s.length(),mcc.groupAddr,mcc.port);
				mcc.socket.send(pack);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		// text fields

		
		chatField = new JTextArea();
		chatField.setLineWrap(true);
		chatField.setFocusable(false);
		chatField.setEditable(false);	
		
		// add to panels
		
		northFrame.add(addrLabel);
		northFrame.add(addrField);
		northFrame.add(portLabel);
		northFrame.add(portField);
		northFrame.add(joinButton);
		northFrame.add(leaveButton);
		
		southFrame.add(sendField);
		southFrame.add(sendButton);
		
		mainFrame.add(northFrame, BorderLayout.NORTH);
		mainFrame.add(southFrame, BorderLayout.SOUTH);
		mainFrame.add(westFrame, BorderLayout.WEST);
		mainFrame.add(chatField, BorderLayout.CENTER);
		
		 this.setVisible(true);

	}

	/**
	 * Adds a message to the chat window when it's received
	 * @param message the message received
	 **/
	public void writeMessage( String message) {
		chatField.append(message);
	}

	/**
	 * Send method.
	 * Sends a message as a datagram packet to the other users in the group.
	 **/
	public void send() throws IOException {
		if( userName == "" ) {
			userName = sendField.getText();
		}else {
			String s = " " + userName + ": " +sendField.getText();
			DatagramPacket pack = new DatagramPacket(s.getBytes(),s.length(),mcc.groupAddr,mcc.port);
			mcc.socket.send(pack);
		}
		sendField.setText("");	
	}

	/**
	 * main method
	 * @param args  not used.
	 **/
	public static void main(String[] args) {
		
		new MulticastGUI();
	}
	
	
}
