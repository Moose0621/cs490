package client;

import java.net.*;

public class UDPDatagramSend {

    public void UDPMetaSend(String TheDataStructureXMLHereToBeSent) {

	try {

	    // DirectoryServer INformation
	    InetAddress hostIP = null;
	    int port = 12184;

	    byte[] message = "This will become the place for Meta Data from Mp3ScannerFactory"
		    .getBytes();

	    // Initialize a datagram packet with data and address
	    DatagramPacket packet = new DatagramPacket(message, message.length,
		    hostIP, port);

	    // After UDP packet has been packaged
	    // open an arbitrary UDP socket, send the META file
	    // through it, and then close it.
	    DatagramSocket udpskt = new DatagramSocket();
	    udpskt.send(packet);
	    udpskt.close();
	} catch (Exception e) {
	    System.err.println(e);
	}

    }
}