package client;

import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.apache.http.client.methods.HttpGet;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class MainWindow {

    private static ObjectOutputStream outputStream;
    private static ObjectInputStream inputStream;
    public final static int port = 8181;
    StyledText consoleOut;
    private static final FormToolkit formToolkit = new FormToolkit(
	    Display.getDefault());
    private static StyledText consoleOutput;
    private static Socket dirSocket;
    private static InetAddress directoryServer;
    private static Table tblSearchResults;
    private static int LIBRARY_SIZE = 1024;

    public static void close() {

    }

    /**
     * Launch the client Gui, the server is launched in the main method of
     * program execution.
     * 
     * @param args
     * @wbp.parser.entryPoint
     */
    public static void build() {

	Display display = Display.getDefault();
	Shell FileChucker = new Shell();
	FileChucker.setSize(697, 463);
	FileChucker.setText("File Chucker");
	FileChucker.setLayout(null);

	Menu menu = new Menu(FileChucker, SWT.BAR);
	FileChucker.setMenuBar(menu);

	MenuItem mainMenuFile = new MenuItem(menu, SWT.CASCADE);
	mainMenuFile.setText("File");
	HttpGet fileRequest = new HttpGet(".fileDownloadRequest");

	Composite cmpsConsole = new Composite(FileChucker, SWT.NONE);
	cmpsConsole.setBounds(10, 312, 661, 83);
	formToolkit.adapt(cmpsConsole);
	formToolkit.paintBordersFor(cmpsConsole);

	consoleOutput = new StyledText(cmpsConsole, SWT.BORDER);
	consoleOutput.setEditable(false);
	consoleOutput.setBounds(0, 0, 668, 83);
	formToolkit.adapt(consoleOutput);
	formToolkit.paintBordersFor(consoleOutput);

	Menu menu_2 = new Menu(mainMenuFile);
	mainMenuFile.setMenu(menu_2);

	MenuItem mainMenuOptions = new MenuItem(menu, SWT.CASCADE);
	mainMenuOptions.setText("Options");

	Menu menu_3 = new Menu(mainMenuOptions);
	mainMenuOptions.setMenu(menu_3);

	Composite cmpsButtonFrame = new Composite(FileChucker, SWT.NONE);
	consoleOutput.append("Test message being sent\n");
	cmpsButtonFrame.setBounds(10, 10, 169, 296);

	Button btnInformAndUpdate = new Button(cmpsButtonFrame, SWT.NONE);

	btnInformAndUpdate.setToolTipText("Click here to update the Server");
	formToolkit.adapt(btnInformAndUpdate, true, true);
	btnInformAndUpdate.setText("Inform/Update Directory Server");
	btnInformAndUpdate.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseDown(MouseEvent e) {
		informAndUpdate();
	    }
	});
	btnInformAndUpdate.setBounds(0, 229, 169, 67);

	Button btnPingServer = new Button(cmpsButtonFrame, SWT.NONE);
	btnPingServer.setBounds(0, 140, 169, 67);
	formToolkit.adapt(btnPingServer, true, true);
	btnPingServer.setText("Ping Directory Server");

	Button btnClose = new Button(cmpsButtonFrame, SWT.NONE);
	btnClose.addMouseListener(new MouseAdapter() {

	    @Override
	    public void mouseDown(MouseEvent e) {
		try {
		    if (null != dirSocket) {
			if (dirSocket.isConnected()) {
			    outputStream.writeObject(".exit");
			}
		    } else {
			JOptionPane.showMessageDialog(null,
				"NO SERVER CONNECTION!!!");
		    }
		} catch (IOException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
	    }
	});

	btnClose.setBounds(0, 80, 169, 54);
	formToolkit.adapt(btnClose, true, true);
	btnClose.setText("Kill");

	Button btnSearch = new Button(cmpsButtonFrame, SWT.NONE);
	btnSearch.setBounds(0, 10, 169, 54);
	formToolkit.adapt(btnSearch, true, true);
	btnSearch.setText("New Button");

	tblSearchResults = new Table(FileChucker, SWT.BORDER
		| SWT.FULL_SELECTION);
	tblSearchResults.setBounds(181, 10, 500, 235);
	formToolkit.adapt(tblSearchResults);
	formToolkit.paintBordersFor(tblSearchResults);
	tblSearchResults.setHeaderVisible(true);
	tblSearchResults.setLinesVisible(true);
	// consoleOut.append("\nNo connection, no test message sent");

	btnPingServer.addMouseListener(new MouseAdapter() {

	    @Override
	    public void mouseDown(MouseEvent e) {
		consoleOutput.append("\nTest message to server!!\n");
		try {
		    if (null != dirSocket) {
			outputStream.writeUTF("Hola There!");
			consoleOutput.append(inputStream.readLine());
		    } else
			consoleOutput.append("No Server connection");

		} catch (IOException e1) {
		    JOptionPane.showMessageDialog(null,
			    "\nNo Server to connect To");
		    e1.printStackTrace();
		}

	    }
	});

	FileChucker.open();
	FileChucker.layout();
	while (!FileChucker.isDisposed()) {
	    if (!display.readAndDispatch()) {
		display.sleep();
	    }
	}
    }

    // This method will provide shoot a UDP datagram to the directory server to
    // let them know they are open for business.

    @SuppressWarnings("unused")
    public static void informAndUpdate() {
	boolean directoryAck = true;
	byte[] rcvData = new byte[4096];
	byte[] sendData;
	byte[] tempf = new byte[128];
	final int infoPort = 8185;
	int offset = 128;
	try {
	    LibraryMaker.scan();
	    // Stack<Byte> scanBytes = new Stack<Byte>();
	    Scanner scan = new Scanner(new FileReader("library.txt"));
	    DatagramPacket udpReciept = new DatagramPacket(rcvData, offset);
	    Path path = Paths.get("library.txt");
	    byte[] scanBytes = Files.readAllBytes(path);
	    // this will be where we hardcode the server address
	    directoryServer = InetAddress.getLocalHost();
	    DatagramSocket clientSocket = new DatagramSocket(infoPort);

	    // scan and store the txt file into a byte stack which will grow to
	    // suit
	    // a variety of size libraries.
	    // store library size to verify receipt from server.
	    // while (directoryAck) {

	    for (int i = 0; i < scanBytes.length; i += 128) {
		consoleOutput.append("\nPushing bytes: " + i
			+ " then some others!!");
		for (int j = 0; (j > 127) || (j + i > scanBytes.length); j++) {
		    tempf[j] = scanBytes[i + j];

		}
		DatagramPacket sendPacket = new DatagramPacket(tempf, offset,
			InetAddress.getLocalHost(), 8182);
		clientSocket.send(sendPacket);
	    }

	    // HttpResponseFactory response;
	    // Reciept of UDP response from directory server confirming that
	    // client
	    // can send search inqueries.
	    clientSocket.setSoTimeout(6000);
	    clientSocket.receive(udpReciept);
	    if (null != udpReciept && udpReciept.getData().equals("^Success")) {

		JOptionPane.showMessageDialog(null, "SUCCESS!! \n "
			+ udpReciept + "\n" + udpReciept.getData() + "\n"
			+ udpReciept.getData().toString());
	    } else {

		JOptionPane.showMessageDialog(null,
			"You Failed....\nTry again fuck ass!!!\n"
				+ udpReciept.getData().toString());
		clientSocket.close();
	    }
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    JOptionPane
		    .showMessageDialog(null, "Server Timeout, \nTry again!!");
	}
    }
}