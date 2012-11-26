package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

/**
 * @author Andrew McCoy -- This class will create a thread instance of a server
 *         connection to allow multiple users to query the directory server
 *         concurrently.
 */

public class ServerConnectionThread extends Thread {
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;
    PrintWriter printWriter;
    String clientQuery;
    int queryFlag;
    String response;

    public ServerConnectionThread(ObjectOutputStream out, ObjectInputStream in) {
	inputStream = in;
	outputStream = out;
    }

    @SuppressWarnings("deprecation")
    @Override
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Thread#run()Javadoc: this will allow us to have the server
     * acceot multiple connections at once, this thread will maintain those
     * connectionThese connections will be short living as the server will only
     * receive UDP requests of different requests, these will be indicatedby the
     * flag conditions of the incoming packet from the client.the flag == int
     * and will coorespond to the follow states:
     * 
     * 0 == new client ready with files, included Datastream of files for
     * sharing. ****1 == query for content from server -- this will give a
     * response of type for display on client screen to minimize client
     * processing of data, only provide basic encoding while over the wire.
     * ****2 == client disconnecting from network, trigger deletion of files
     * from server ****3 == other future implementations
     */
    public void run() {
	PrintWriter printWriter = new PrintWriter(outputStream, true);

	while (true) {

	    printWriter.println("Welcome to the FileChucker basic server");

	    try {
		response = inputStream.readLine();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	    printWriter.println(response + "\n!!!Have a great Day!!");

	}
    }

}
