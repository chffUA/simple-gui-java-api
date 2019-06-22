package api;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class UI {
	
	private static JDialog loadingWindow;
	private static JDialog logWindow;
	private static JTextArea logContentDisplay = new JTextArea();
	
	private static boolean showingInputsOnly;
	private static JButton modeSelector;
	
	private static StringBuilder logContentSimple = new StringBuilder();
	private static StringBuilder logContentComplete = new StringBuilder();

	public static void startLoading() {	
		loadingWindow = new JOptionPane("Loading...", JOptionPane.INFORMATION_MESSAGE, 
				JOptionPane.DEFAULT_OPTION, fetchIcon(), new Object[]{}).createDialog("API information");
		  
		Thread t = new Thread(new Runnable(){
	        public void run(){
	            loadingWindow.setVisible(true);
	        }
	    });
		t.start();
	}
	
	public static void doneLoading() {
		loadingWindow.dispose();
		Logger.systemLog("API is up.");
	}
	
	public static void showLogger() {
		JScrollPane pane = new JScrollPane(logContentDisplay) {

			private static final long serialVersionUID = 1L;

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(480, 320);
			}
		};	
		
		showingInputsOnly = true;
		modeSelector = new JButton("Show requests & responses");
		modeSelector.addActionListener(e -> { changeMode(); });
		
		JButton scribe = new JButton("Write to disk");
		scribe.addActionListener(e -> { write(); });
		
		JButton terminator = new JButton("Stop API");
		terminator.addActionListener(e -> { exit(); });
		
		JButton unlogger = new JButton("Close logger");
		unlogger.addActionListener(e -> { closeWindow(); });
		
		logWindow = new JOptionPane(pane, -1, JOptionPane.DEFAULT_OPTION, 
				null, new Object[] {modeSelector, scribe, terminator, unlogger}).createDialog("API logs");
		logWindow.addWindowListener(exitOnCloseListener()); // for X button

		Thread t = new Thread(new Runnable(){
	        public void run(){
	        	logWindow.setVisible(true); 
	        }
	    });
		t.start();

	}
	
	private static void update() {
		if (showingInputsOnly) logContentDisplay.setText(logContentSimple.toString());
		else logContentDisplay.setText(logContentComplete.toString());
	}
	
	private static void makeLog(StringBuilder sb, Tuple<String,String> entry) {
		sb.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
	}
	
	public static void receiveSimpleLog(Tuple<String,String> entry) {
		makeLog(logContentSimple, entry);
		update();
	}
	
	public static void receiveCompleteLog(Tuple<String,String> entry) {
		makeLog(logContentComplete, entry);
		update();
	}
	
	public static void receiveSystemLog(Tuple<String,String> entry) {
		makeLog(logContentSimple, entry);
		makeLog(logContentComplete, entry);
		update();
	}
	
	private static void changeMode() {
		showingInputsOnly = !showingInputsOnly;
		
		if (showingInputsOnly) modeSelector.setText("Show requests & responses");
		else modeSelector.setText("Show only user requests");
		
		update();
	}
	
	private static void write() {
		File sout = new File("simplelogs.txt");
		File cout = new File("completelogs.txt");
		
		try {
			sout.createNewFile(); //only creates if
			cout.createNewFile(); //files don't exist
		} catch (IOException e) {
			Logger.systemLog("Error creating target files to write to: "+e.getMessage());
			return;
		}  
		
		FileWriter fw;
		
		try {
			fw = new FileWriter(sout,true);
			fw.write(Logger.simpleLogsAsString());
			fw.close();
		} catch (IOException e) {
			Logger.systemLog("Error writing to simplelogs file: "+e.getMessage());
			return;
		}
		
		try {
			fw = new FileWriter(cout,true);
			fw.write(Logger.completeLogsAsString());
			fw.close();
		} catch (IOException e) {
			Logger.systemLog("Error writing to completelogs file: "+e.getMessage());
			return;
		}
		
		Logger.systemLog("Wrote logger contents to files.");
	}
	
	private static void exit() {	
		System.exit(0);
	}
	
	private static void closeWindow() {
		logWindow.dispose();
	}
	
	private static WindowAdapter exitOnCloseListener() {
		return new WindowAdapter() {
			public void windowClosing(WindowEvent e) { exit(); }
		};
	}
	
	private static ImageIcon fetchIcon() {		
		try {
			InputStream stream = UI.class.getClassLoader().getResourceAsStream("resources/icon.png");
			return new ImageIcon(ImageIO.read((stream)));
		} catch (IllegalArgumentException | IOException e) {
			Logger.systemLog("Tried to fetch loading icon but failed: "+e.getMessage());
			return null;
		}
	}

}
