package client;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.SystemColor;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;
import javax.swing.JRootPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class MainWindow {

    private final String FAIL = "nothing sent, you failed!";
    private static DataOutputStream outputStream;
    public final static int port = 8181;
    StyledText consoleOut;
    private static final FormToolkit formToolkit = new FormToolkit(
	    Display.getDefault());
    private static Text text;
    private static boolean connStatus = false;
    private static StyledText consoleOutput;

    private static BufferedReader inputstream;
    private static Socket dirSocket;
    private static InetAddress directoryServer;

    public static void close() {

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

    /**
     * Launch the application.
     * 
     * @param args
     */
    public static void main(String[] args) {

	Display display = Display.getDefault();
	Shell FileChucker = new Shell();
	FileChucker.setSize(697, 463);
	FileChucker.setText("File Chucker");
	FileChucker.setLayout(null);

	Menu menu = new Menu(FileChucker, SWT.BAR);
	FileChucker.setMenuBar(menu);

	MenuItem mainMenuFile = new MenuItem(menu, SWT.CASCADE);
	mainMenuFile.setText("File");

	Composite consoleComposite = new Composite(FileChucker, SWT.NONE);
	consoleComposite.setBounds(10, 312, 661, 83);
	formToolkit.adapt(consoleComposite);
	formToolkit.paintBordersFor(consoleComposite);

	consoleOutput = new StyledText(consoleComposite, SWT.BORDER);
	consoleOutput.setEditable(false);
	consoleOutput.setBounds(0, 0, 668, 83);
	formToolkit.adapt(consoleOutput);
	formToolkit.paintBordersFor(consoleOutput);

	Menu menu_2 = new Menu(mainMenuFile);
	mainMenuFile.setMenu(menu_2);

	MenuItem mntmExtra = new MenuItem(menu, SWT.SEPARATOR);

	MenuItem mainMenuOptions = new MenuItem(menu, SWT.CASCADE);
	mainMenuOptions.setText("Options");

	Menu menu_3 = new Menu(mainMenuOptions);
	mainMenuOptions.setMenu(menu_3);

	Composite connectionInformation = new Composite(FileChucker, SWT.NONE);
	consoleOutput.append("Test message being sent\n");
	connectionInformation.setBounds(10, 10, 130, 296);

	Button connect = new Button(connectionInformation, SWT.NONE);

	connect.setToolTipText("Click here to Connect to Directory Server");
	formToolkit.adapt(connect, true, true);
	connect.setText("Connect to Server");
	connect.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseDown(MouseEvent e) {
		// connStatus = true;
		connectToDirectoryServer();

	    }
	});
	connect.setBounds(0, 273, 130, 23);

	Button serverPing = new Button(connectionInformation, SWT.NONE);
	serverPing.setBounds(0, 242, 130, 25);
	formToolkit.adapt(serverPing, true, true);
	serverPing.setText("Ping Directory Server");

	Button closeConnection = new Button(connectionInformation, SWT.NONE);
	closeConnection.addMouseListener(new MouseAdapter() {

	    @Override
	    public void mouseDown(MouseEvent e) {
		try {
		    if (null != dirSocket) {

			if (dirSocket.isConnected()) {
			    consoleOutput.redraw();
			    consoleOutput.append("\nClosing Connection to: "
				    + dirSocket);
			    dirSocket.shutdownInput();
			    dirSocket.shutdownOutput();
			    dirSocket.close();

			} else {
			    consoleOutput.redraw();
			    consoleOutput.append("No Connection to Close!!");
			    JOptionPane.showMessageDialog(null,
				    "NO SERVER CONNECTION!!!");
			}
		    }
		} catch (IOException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
	    }
	});

	closeConnection.setBounds(0, 211, 130, 25);
	formToolkit.adapt(closeConnection, true, true);
	closeConnection.setText("Kill");
	// consoleOut.append("\nNo connection, no test message sent");

	serverPing.addMouseListener(new MouseAdapter() {

	    @Override
	    public void mouseDown(MouseEvent e) {
		consoleOutput.append("\ntest message to server!!\n");
		try {
		    if (null != dirSocket) {
			outputStream.writeUTF("Hola There!");
			consoleOutput.append(inputstream.readLine());
		    } else
			consoleOutput.append("No Server connection");

		} catch (IOException e1) {
		    consoleOutput.append("\nNo Server to connect To");
		    e1.printStackTrace();
		}

	    }
	});

	Composite fileXferInfo = new Composite(FileChucker, SWT.EMBEDDED);
	fileXferInfo.setBounds(141, 10, 538, 296);
	formToolkit.adapt(fileXferInfo);
	formToolkit.paintBordersFor(fileXferInfo);

	Frame frame = SWT_AWT.new_Frame(fileXferInfo);

	Panel panel = new Panel();
	frame.add(panel);
	panel.setLayout(new BorderLayout(0, 0));

	JRootPane rootPane = new JRootPane();
	rootPane.getContentPane()
		.setBackground(SystemColor.inactiveCaptionText);
	panel.add(rootPane);

	text = new Text(fileXferInfo, SWT.BORDER);
	text.setBounds(55, 98, 205, 59);
	formToolkit.adapt(text, true, true);

	FileChucker.open();
	FileChucker.layout();
	while (!FileChucker.isDisposed()) {
	    if (!display.readAndDispatch()) {
		display.sleep();
	    }
	}
    }

    public static boolean connectToDirectoryServer() {

	try {
	    // this will be where we hardcode the server address
	    directoryServer = InetAddress.getLocalHost();
	    consoleOutput.redraw();
	    consoleOutput.append("\nTrying to connect to: " + directoryServer);
	    dirSocket = new Socket(directoryServer, port);

	    outputStream = new DataOutputStream(dirSocket.getOutputStream());
	    inputstream = new BufferedReader(new InputStreamReader(
		    dirSocket.getInputStream()));

	} catch (UnknownHostException e) {
	    // TODO Auto-generated catch block

	    return false;
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	    return false;
	}
	consoleOutput.redraw();
	consoleOutput.append("\nConnected to : " + dirSocket);
	return true;
    }

    public StyledText getConsoleOut() {
	return consoleOutput;
    }

    public String sendtoServer(String toBeSent) {

	try {
	    outputStream.writeUTF(toBeSent);
	    return inputstream.toString();
	} catch (IOException e) {
	    e.printStackTrace();
	    return FAIL;
	}
    }
}