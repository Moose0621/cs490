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
   private Mp3ScannerFactory mp3Scanner;
   private UDPDatagramSend udpSend;
   private static final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
   private PrintWriter consoleOut;
   private static Text text;
   private static TextViewer textViewer;
   private static StyledText consoleOut_1;

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

      Composite composite_1 = new Composite(FileChucker, SWT.NONE);
      composite_1.setBounds(10, 314, 669, 66);
      formToolkit.adapt(composite_1);
      formToolkit.paintBordersFor(composite_1);

      textViewer = new TextViewer(composite_1, SWT.BORDER);
      textViewer.setEditable(false);
      consoleOut_1 = textViewer.getTextWidget();
      consoleOut_1.setEditable(false);
      consoleOut_1.setBounds(0, 0, 669, 66);

      Composite connectionInformation = new Composite(FileChucker, SWT.NONE);
      connectionInformation.setBounds(10, 10, 130, 296);

      Button connect = new Button(connectionInformation, SWT.NONE);
      connect.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseDown(MouseEvent e) {
            clientConnection.connectToDirectoryServer();
         }
      });
      connect.setToolTipText("Click here to Connect to Directory Server");
      connect.setBounds(0, 273, 130, 23);
      formToolkit.adapt(connect, true, true);
      connect.setText("Connect to Server");

      Composite composite = new Composite(FileChucker, SWT.EMBEDDED);
      composite.setBounds(141, 10, 538, 296);
      formToolkit.adapt(composite);
      formToolkit.paintBordersFor(composite);

      Frame frame = SWT_AWT.new_Frame(composite);

      Panel panel = new Panel();
      frame.add(panel);
      panel.setLayout(new BorderLayout(0, 0));

      JRootPane rootPane = new JRootPane();
      rootPane.getContentPane().setBackground(SystemColor.inactiveCaptionText);
      panel.add(rootPane);

      text = new Text(composite, SWT.BORDER);
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

   public StyledText getConsoleOut_1() {
      return consoleOut_1;
   }
}
