import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import swing2swt.layout.BoxLayout;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.custom.StackLayout;


public class MainWindow {
	private static TextViewer textViewer;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell FileChucker = new Shell();
		FileChucker.setSize(697, 463);
		FileChucker.setText("File Chucker");
		FileChucker.setLayout(null);
		
		Composite peers = new Composite(FileChucker, SWT.NONE);
		peers.setBounds(10, 5, 174, 400);
		peers.setLayout(new StackLayout());
		
		Composite connection = new Composite(FileChucker, SWT.NONE);
		connection.setBounds(182, 5, 499, 400);
		
		textViewer = new TextViewer(connection, SWT.BORDER);
		StyledText console = textViewer.getTextWidget();
		console.setToolTipText("connection status console");
		console.setBlockSelection(true);
		console.setBounds(10, 275, 479, 115);
		
		Button btnNewButton = new Button(FileChucker, SWT.NONE);
		btnNewButton.setBounds(97, -19, 63, 13);
		btnNewButton.setText("New Button");
		
		Button btnNewButton_1 = new Button(FileChucker, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnNewButton_1.setBounds(19, -19, 63, 13);
		btnNewButton_1.setText("New Button");
		
		Menu menu = new Menu(FileChucker, SWT.BAR);
		FileChucker.setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");
		
		Menu menu_2 = new Menu(mntmFile);
		mntmFile.setMenu(menu_2);
		
		MenuItem mntmOptions = new MenuItem(menu, SWT.CASCADE);
		mntmOptions.setText("Options");
		
		Menu menu_3 = new Menu(mntmOptions);
		mntmOptions.setMenu(menu_3);
		
		MenuItem mntmAbout = new MenuItem(menu, SWT.CASCADE);
		mntmAbout.setText("About");
		
		Menu menu_4 = new Menu(mntmAbout);
		mntmAbout.setMenu(menu_4);
		
		MenuItem mntmExtra = new MenuItem(menu, SWT.SEPARATOR);

		FileChucker.open();
		FileChucker.layout();
		while (!FileChucker.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	public TextViewer getTextViewer() {
		return textViewer;
	}
}
