package stream;

import java.net.*;
import java.io.*;

public class RecieveThread extends Thread{
	
	private MulticastClient client;
	private MulticastSocket socket;
	private BufferedReader input;
	
	public RecieveThread( MulticastSocket s, MulticastClient c) {
		socket = s;
		client = c;
	}
	
	public void run() {
  	  try { 
  		  
          for(;;) {
              byte[] buffer = new byte[2000];
              DatagramPacket pack = new DatagramPacket(buffer, buffer.length);
              socket.receive(pack);
              String s = new String(pack.getData(), 0, pack.getLength());
              s = s+"\n";
        	  client.writeMessage(s);
              }
      } catch (Exception e) {
          System.err.println("Error in ReceiveThread:" + e);
          e.printStackTrace();
      }
     }

	
}


