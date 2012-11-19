package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConnection {

    private final String FAIL = "nothing sent, you failed!";
    private static DataOutputStream outputStream;
    public final static int port = 8181;

    /**
     * @return the outputStream
     */
    public static DataOutputStream getOutputStream() {
	return outputStream;
    }

    /**
     * @param outputStream
     *            the outputStream to set
     * 
     *            public static void setOutputStream(DataOutputStream
     *            outputStream) { ClientConnection.outputStream = outputStream;
     *            }
     * 
     *            /**
     * @return the inputstream
     */
    public static BufferedReader getInputstream() {
	return inputstream;
    }

    public String sendtoServer(String toBeSent) {

	try {

	    outputStream.writeUTF(toBeSent);
	    return inputstream.toString();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return FAIL;

    }

    /**
     * @param inputstream
     *            the inputstream to set
     */
    public static void setInputstream(BufferedReader inputstream) {
	ClientConnection.inputstream = inputstream;
    }

    private static BufferedReader inputstream;

    public void connectToDirectoryServer() {

	try {
	    Socket dirSocket = new Socket(InetAddress.getLocalHost(),port);
	    
	    outputStream = new DataOutputStream(dirSocket.getOutputStream());
	    inputstream = new BufferedReader(new InputStreamReader(
		    dirSocket.getInputStream()));
	
	} catch (UnknownHostException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
