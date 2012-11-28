package client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class LibraryMaker {

    public static ArrayList<String> libraryItems = new ArrayList<String>();

    public static void scan() throws IOException {
	PrintWriter outputFile;
	String userDir = System.getProperty("user.dir");
	outputFile = new PrintWriter(new FileWriter("library.txt"));
	getDirContents(userDir);

	for (int i = 0; i < libraryItems.size(); i++) {
	    outputFile.println(libraryItems.get(i));
	}
	outputFile.close();
	// return outputFile;
    }

    private static void getDirContents(String dir) throws UnknownHostException {
	File dirFile = new File(dir);
	File[] dirContents = dirFile.listFiles();
	for (int i = 0; i < dirContents.length; i++) {
	    if (dirContents[i].isDirectory())
		getDirContents(dirContents[i].toString());
	    else if (dirContents[i].toString().endsWith(".mp3")) {
		libraryItems
			.add((dirContents[i].toString() + "\t"
				+ InetAddress.getLocalHost().toString() + "test data to add"));
	    }
	}
    }
}
