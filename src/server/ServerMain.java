package server;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    static int port = 8181;
    static ObjectInputStream inputStream;
    static ObjectOutputStream outputStream;
    public static udpServerThread udpServer;

    public static void main(String[] args) {
	ServerThread serverThread;
	new File("directory.txt");

	try {
	    byte[] buf = new byte[128];
	    ServerSocket serverSkt = new ServerSocket(port, 6,
		    InetAddress.getLocalHost());

	    // udpServerThread.start();
	    while (true) {
		Socket clientConnection = serverSkt.accept();
		inputStream = new ObjectInputStream(
			clientConnection.getInputStream());
		outputStream = new ObjectOutputStream(
			clientConnection.getOutputStream());
		serverThread = new ServerThread(outputStream, inputStream);
		System.out.println("Connection to client "
			+ clientConnection.getInetAddress() + "\n");
		udpServer.start();
		serverThread.start();
	    }

	} catch (IOException e) {
	    System.out.println("Connection Failed: " + port);
	    e.printStackTrace();

	    // TODO must implement interaction with MySQL dp to get info about
	    // files in storage.

	    // TODO Only GET request needs to be implemented. The URL field in
	    // GET method contains the name of the
	    // requested file;

	}
    }

    class udpServerThread extends Thread {

	DatagramSocket udpSkt;
	byte[] buf = new byte[128];

	public udpServerThread() throws Exception {
	    udpSkt = new DatagramSocket(8181);
	}

	public void run() {
	    while (true) {
		DatagramPacket packet = new DatagramPacket(buf, 128, buf.length);
		try {
		    udpSkt.receive(packet);
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	}
    }
}
