package blake2;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.graphics.Point;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class BLAKE2GUI {

	protected Shell shlBlakeb;
	private Text MessageInputBox;
	private Label lblHash;
	private Text hashOutputBox;

	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlBlakeb.open();
		shlBlakeb.layout();
		while (!shlBlakeb.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlBlakeb = new Shell();
		shlBlakeb.setSize(new Point(500, 500));
		shlBlakeb.setMinimumSize(new Point(500, 500));
		shlBlakeb.setSize(500,500);
		shlBlakeb.setText("BLAKE2b");
		
		MessageInputBox = new Text(shlBlakeb, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		MessageInputBox.setBounds(10, 34, 464, 298);
		
		Label lblNewLabel = new Label(shlBlakeb, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblNewLabel.setBounds(10, 10, 75, 25);
		lblNewLabel.setText("Message:");
		
		lblHash = new Label(shlBlakeb, SWT.NONE);
		lblHash.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.NORMAL));
		lblHash.setBounds(10, 338, 65, 20);
		lblHash.setText("Hash:");
		
		hashOutputBox = new Text(shlBlakeb, SWT.BORDER | SWT.WRAP);
		hashOutputBox.setEditable(false);
		hashOutputBox.setBounds(10, 364, 464, 56);
		
		Button btnCalculate = new Button(shlBlakeb, SWT.NONE);
		btnCalculate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Resets messagebox back to empty
				hashOutputBox.setText("");
				
				byte[] hashOutput = new byte[64];
				
				try {
					BLAKE2_Hasher hasher = new BLAKE2_Hasher(MessageInputBox.getText().getBytes());
					hashOutput = hasher.CalculateHash();
					
					hashOutputBox.setText(hasher.bytesToHex(hashOutput));
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnCalculate.setBounds(10, 426, 100, 25);
		btnCalculate.setText("Calculate Hash");
		
		Button btnCopyClipboard = new Button(shlBlakeb, SWT.NONE);
		btnCopyClipboard.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				String output = hashOutputBox.getText();
				Clipboard clipboard = toolkit.getSystemClipboard();
				StringSelection strSel = new StringSelection(output);
				clipboard.setContents(strSel, null);
				
				
				
			}
		});
		btnCopyClipboard.setBounds(116, 426, 109, 25);
		btnCopyClipboard.setText("Copy to Clipboard");
		
		Button btnExit = new Button(shlBlakeb, SWT.NONE);
		btnExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shlBlakeb.dispose();
			}
		});
		btnExit.setBounds(399, 426, 75, 25);
		btnExit.setText("Exit");

	}
}
