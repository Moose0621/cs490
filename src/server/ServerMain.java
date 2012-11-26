package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    static int port = 8181;
    static ObjectInputStream inputStream;
    static ObjectOutputStream outputStream;

    public static void main(String[] args) {
	try {
	    ServerSocket serverSkt = new ServerSocket(port, 6,
		    InetAddress.getLocalHost());
	    ServerConnectionThread serverThread;

	    while (true) {
		System.out.println("Listening for Connection on port: "
			+ serverSkt + "!!!");
		Socket clientConnection = serverSkt.accept();
		inputStream = new ObjectInputStream(
			clientConnection.getInputStream());
		outputStream = new ObjectOutputStream(
			clientConnection.getOutputStream());
		serverThread = new ServerConnectionThread(outputStream,
			inputStream);
		System.out.println("Connection to client "
			+ clientConnection.getInetAddress() + "\n");
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
}
