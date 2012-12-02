package server;

import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

public class ServerMain {

    static int port = 8181;
    FileWriter fileOut;
    static ObjectInputStream inputStream;
    static ObjectOutputStream outputStream;
    public static DatagramPacket rcvPkt = null;
    public static udpServerThread udpServer;

    public static void main(String[] args) {

	try {
	    byte[] buf = new byte[1024];

	    DatagramSocket udpSocket = new DatagramSocket(8181);
	    rcvPkt = new DatagramPacket(buf, buf.length);
	    byte[] data = new byte[1024];
	    // udpServerThread.start();
	    while (true) {
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

    class udpServerThread extends Thread {

	List<byte[]> data = new ArrayList<byte[]>();

	public udpServerThread(DatagramPacket sentInfo) throws Exception {
	    if (null != sentInfo) {
		data.add(sentInfo.getData());
	    }
	}

	public void run() {
	    try {
		fileOut = new FileWriter("directory.txt");
	    } catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }
	    while (true) {
		try {
		    CharSequence csq = data.toString();
		    fileOut.append(csq);
		    fileOut.close();
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();

		}
	    }
	}
    }
}

class udpResponse implements Runnable {

    DatagramSocket socket = null;
    DatagramPacket packet = null;

    public udpResponse(DatagramSocket socket, DatagramPacket packet) {
	this.socket = socket;
	this.packet = packet;
    }

    public void run() {
	byte[] data = "Success".getBytes(); // code not shown
	DatagramPacket response = new DatagramPacket(data, data.length,
		packet.getAddress(), packet.getPort());
	try {
	    socket.send(response);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
