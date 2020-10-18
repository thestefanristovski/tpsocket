package stream;

import java.io.IOException;
import java.net.*;

import gui.MulticastGUI;

public class MulticastClient {

	MulticastGUI ui;
	
	public MulticastSocket socket;
	public RecieveThread recieve;
	public InetAddress groupAddr;
	public int port;
	
	public MulticastClient( MulticastGUI ui) {
		this.ui = ui;
	}
	
	@SuppressWarnings("deprecation")
	public void Join( String addr, int port) throws IOException {
		
			this.port = port;
			groupAddr = InetAddress.getByName(addr);
			// Create a multicast socket
			socket = new MulticastSocket(port);
			// Join the group
			socket.joinGroup(groupAddr);
			//send = new SendThread(socket, groupAddr, port);
			//send.start();
			recieve = new RecieveThread(socket,this);
			recieve.start();
	 }
	
	public void writeMessage( String message ) {
		ui.writeMessage(message);
	}
	
	@SuppressWarnings("deprecation")
	public static void main( String args[] ) {
		
		
		try {
			// mcc.Join("228.5.6.7",6789);
			// Send Thread


			
		} catch ( Exception e) {
			System.out.println( "Error in MulticastClient : " +e  );
			e.printStackTrace();
		}
		
	}
	
}
