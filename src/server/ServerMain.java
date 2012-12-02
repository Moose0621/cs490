package server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerMain {

    private static final int BUFFER_SIZE = 4096;
    static int port = 8181;
    PrintWriter fileOut;
    static ObjectInputStream inputStream;
    static ObjectOutputStream outputStream;
    public static DatagramPacket rcvPkt = null;

    public static void main(String[] args) throws Exception {

	try {
	    byte[] buf = new byte[BUFFER_SIZE];

	    DatagramSocket udpSocket = new DatagramSocket(8182);
	    rcvPkt = new DatagramPacket(buf, buf.length);
	    byte[] data = new byte[BUFFER_SIZE];
	    // udpServerThread.start();
	    while (true) {
		System.out.println("Listening on: " + udpSocket);
		udpSocket.receive(rcvPkt);
		new Thread(new udpResponse(udpSocket, rcvPkt)).start();

		// DatagramPacket response = new DatagramPacket(data,
		// data.length,
		// packet.getAddress(), packet.getPort());
		// socket.send(response);
		// udpSocket.udpServer.start();
	    }

	} catch (IOException e) {
	    System.out.println("Connection Failed: " + port);
	    e.printStackTrace();

	}
    }
}

class udpResponse implements Runnable {

    DatagramSocket socket = null;
    DatagramPacket packet = null;
    DatagramPacket response = null;
    byte[] data = null;

    public udpResponse(DatagramSocket socket, DatagramPacket packet)
	    throws Exception {
	BufferedWriter fileOut = new BufferedWriter(new FileWriter(
		"directory.txt"));
	System.out.println("At Constructor of udpRepo Thread");
	this.socket = socket;
	this.packet = packet;
	DatagramPacket response = new DatagramPacket(data, data.length,
		packet.getAddress(), 8185);
    }

    public void run() {
	if (null != packet.getData()) {

	    data = "^Success".getBytes(); // code not shown
	} else
	    try {
		System.out.println("About to send response\n");
		socket.send(response);
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
    }
}