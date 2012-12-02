package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.http.HttpRequestFactory;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.io.HttpRequestParser;
import org.apache.http.impl.io.HttpResponseWriter;
import org.apache.http.impl.nio.reactor.SessionInputBufferImpl;
import org.apache.http.impl.nio.reactor.SessionOutputBufferImpl;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.message.LineFormatter;
import org.apache.http.message.LineParser;
import org.apache.http.params.HttpParams;

public class ClientServer {

    public static void launch() {
	// launch clientside server.
	try {
	    ServerSocket cServer = new ServerSocket(4545);
	    // client server will listen on 4545, this next forever loop will
	    // listen for client connections to request downloads.
	    while (true) {
		Socket clientSocket = cServer.accept();
		ObjectOutputStream output = new ObjectOutputStream(
			clientSocket.getOutputStream());
		ObjectInputStream input = new ObjectInputStream(
			clientSocket.getInputStream());
		// create a thread start for client server here
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static HttpGet getMp3(String fileName) {
	HttpGet clientGetRequest = new HttpGet();

	return clientGetRequest;
    }
}

class ClientServerThread extends Thread {
    ObjectOutputStream output;
    ObjectInputStream input;
    SessionInputBufferImpl inputBuf;
    SessionOutputBufferImpl outputBuf;
    Socket cSkt;
    int readFlag = 0;

    public ClientServerThread(Socket server, ObjectInputStream in,
	    ObjectOutputStream out) throws Exception {
	HttpParams params = null;
	int linebuffersize = 128;
	inputBuf = new SessionInputBufferImpl(readFlag, linebuffersize, params);
	cSkt = server;
	input = in;
	output = out;
    }

    @SuppressWarnings("deprecation")
    public void run() {

	while (true) {
	    HttpRequestFactory requestFactory = null;
	    HttpParams params = null;
	    LineParser parser = null;
	    SessionInputBuffer buffer = null;
	    HttpRequestParser readInput = new HttpRequestParser(buffer, parser,
		    requestFactory, params);
	    switch (readFlag) {

	    case 0:
		if (readInput.equals("^fileRequest")) {
		    readFlag = 1;
		    break;
		} else if (readInput.equals("^")) {
		    LineFormatter formatter = null;
		    SessionOutputBuffer sessionBuffer = null;
		    HttpParams httpParams1 = null;
		    HttpResponseWriter response = new HttpResponseWriter(
			    sessionBuffer, formatter, httpParams1);

		    break;
		}
	    case 1:

	    }

	}
    }
}
