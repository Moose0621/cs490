package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Scanner;

public class LibraryToArrayList {

    public static ArrayList<SharedFile> sharedFiles = new ArrayList<SharedFile>();
    private Scanner inputFile2;
    private Scanner inputFile;

    public void processInform() throws IOException {
	System.out.println("Reading library.txt");
	// inputFile2 = new Scanner(new FileInputStream(new
	// File("directory.txt")));
	inputFile = new Scanner(new FileInputStream(new File("library.txt")));

	while (inputFile.hasNextLine()) {
	    String thisLine = inputFile.nextLine();
	    System.out.println(thisLine);
	    InetAddress thisAddress = InetAddress.getLocalHost();
	    SharedFile thisFile = new SharedFile(thisLine, thisAddress);
	    sharedFiles.add(thisFile);
	}
    }

    public static void removePeer(InetAddress peerAddress) {
	for (int i = 0; i < sharedFiles.size(); i++) {
	    if (sharedFiles.get(i).getInetAddress().equals(peerAddress))
		sharedFiles.remove(i);
	}
    }
}

class SharedFile {

    private String filePath = null;
    private InetAddress sharingPeer = null;

    public SharedFile(String path, InetAddress sharer) {
	filePath = path;
	sharingPeer = sharer;
    }

    public InetAddress getInetAddress() {
	return sharingPeer;
    }

    public String getPath() {
	return filePath;
    }

}