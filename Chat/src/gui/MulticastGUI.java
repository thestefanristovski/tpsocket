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

	String userName = "";
	
	MulticastClient mcc = new MulticastClient(this); 
	
	public JButton sendButton;
	public JButton joinButton;
	public JButton leaveButton;

	JLabel addrLabel = new JLabel("Address :");
	JLabel portLabel = new JLabel("Port :");
	
	JTextField addrField;
	JTextField portField;
	JTextField sendField;
	JTextArea chatField;
	
	
	
	JPanel mainFrame = new JPanel(new BorderLayout());
	JPanel northFrame = new JPanel( new FlowLayout());
	JPanel southFrame = new JPanel(new FlowLayout());
	JPanel westFrame = new JPanel( new BorderLayout());

	
	public MulticastGUI() {
		
		super("Multicast GUI");
		this.setSize(600,800);
		this.setResizable(false);
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
	
	public void writeMessage( String message) {
		chatField.append(message);
	}

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
	
	public static void main(String[] args) {
		
		new MulticastGUI();
	}
	
	
}
