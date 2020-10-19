/***
 * MulticastClient.
 *
 * @since 13/10/20
 * @author Stefan Ristovksi, Aydin Akaydin
 */
package stream;

import java.io.IOException;
import java.net.*;

import gui.MulticastGUI;

public class MulticastClient {

	/**
	 * GUI instance
	 **/
	MulticastGUI ui;

	/**
	 * Client socket
	 **/
	public MulticastSocket socket;
	/**
	 * Receiving thread for client
	 **/
	public RecieveThread recieve;
	/**
	 * IP address of the group
	 **/
	public InetAddress groupAddr;
	/**
	 * Port number
	 **/
	public int port;

	/**
	 * Constructor. Initialises the GUI of Multicast Client.
	 * @param ui the UI instance.
	 **/
	public MulticastClient( MulticastGUI ui) {
		this.ui = ui;
	}

	/**
	 * Join method.
	 * Initialises the reveive thread with the IP address and port number
	 * @param addr the IP address.
	 * @param port the port number.
	 **/
	@SuppressWarnings("deprecation")
	public void Join( String addr, int port) throws IOException {
		
			this.port = port;
			groupAddr = InetAddress.getByName(addr);
			// Create a multicast socket
			socket = new MulticastSocket(port);
			// Join the group
			socket.joinGroup(groupAddr);
			recieve = new RecieveThread(socket,this);
			recieve.start();
	 }

	/**
	 * Gets the message from the receiving thread and send it to the GUI
	 * @param message the message received.
	 **/
	public void writeMessage( String message ) {
		ui.writeMessage(message);
	}

	/**
	 * main method. not used.
	 **/
	@SuppressWarnings("deprecation")
	public static void main( String args[] ) {
		try {

		} catch ( Exception e) {
			System.out.println( "Error in MulticastClient : " +e  );
			e.printStackTrace();
		}
		
	}
	
}
