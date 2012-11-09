package engine;

import java.lang.*;
import java.io.*;
import java.net.*;

public class ClientConnection {

    public static void connectToDirectoryServer(){
		
		try {
		    Socket skt = new Socket("localhost",12184);
		} catch (UnknownHostException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	}
}
