package client;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.SystemColor;
import java.io.PrintWriter;

import javax.swing.JRootPane;

import org.eclipse.jface.text.TextViewer;
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

    private static ClientConnection clientConnection = new ClientConnection();
    private static final FormToolkit formToolkit = new FormToolkit(
	    Display.getDefault());
    private static Text text;
    private static boolean connStatus = false;
    private static Text consoleText;
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

	Menu menu_2 = new Menu(mainMenuFile);
	mainMenuFile.setMenu(menu_2);

	MenuItem mntmExtra = new MenuItem(menu, SWT.SEPARATOR);

	MenuItem mainMenuOptions = new MenuItem(menu, SWT.CASCADE);
	mainMenuOptions.setText("Options");

	Menu menu_3 = new Menu(mainMenuOptions);
	mainMenuOptions.setMenu(menu_3);

	Composite connectionInformation = new Composite(FileChucker, SWT.NONE);
	connectionInformation.setBounds(10, 10, 130, 296);

	Button connect = new Button(connectionInformation, SWT.NONE);
	connect.setToolTipText("Click here to Connect to Directory Server");
	formToolkit.adapt(connect, true, true);
	connect.setText("Connect to Server");
	connect.addMouseListener(new MouseAdapter() {
	@Override
	public void mouseDown(MouseEvent e) {
	    //connStatus = true;
	    clientConnection.connectToDirectoryServer();
	    
	}
	});
	connect.setBounds(0, 273, 130, 23);

	Button serverPing = new Button(connectionInformation, SWT.NONE);
	serverPing.setBounds(0, 242, 130, 25);
	formToolkit.adapt(serverPing, true, true);
	serverPing.setText("Ping Directory Server");
	serverPing.addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseDown(MouseEvent e) {
		
		    consoleText.setText("Test message being sent");
		    consoleText.setText(clientConnection.sendtoServer("test message to server!!"));
		
		    consoleText.append("No connection, no test message sent");
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
	text.setText("Hello Test hopefully \n this shows!!");
	
	Composite consoleComposite = new Composite(FileChucker, SWT.NONE);
	consoleComposite.setBounds(10, 312, 661, 83);
	formToolkit.adapt(consoleComposite);
	formToolkit.paintBordersFor(consoleComposite);
	
	consoleText = new Text(consoleComposite, SWT.BORDER);
	consoleText.setBounds(0, 0, 661, 83);
	formToolkit.adapt(consoleText, true, true);
	
	FileChucker.open();
	FileChucker.layout();
	while (!FileChucker.isDisposed()) {
	    if (!display.readAndDispatch()) {
		display.sleep();
	    }
	}
    }

}
