package api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Logger {

	private static ArrayList<Tuple<String,String>> allLogs = new ArrayList<>();
	private static ArrayList<Tuple<String,String>> userLogs = new ArrayList<>();
	private static SimpleDateFormat dateformat = new SimpleDateFormat("dd/MMM/yyyy HH:mm");
	
	public static void log(boolean isInput, String msg) {
		String date = dateformat.format(new Date());
		
		if (isInput) {
			Tuple<String,String> in = new Tuple<>(buildTupleDate(date), buildTupleMessageInput(msg));
			Tuple<String,String> s = new Tuple<>(buildTupleDate(date), msg);
			allLogs.add(in);
			UI.receiveCompleteLog(in);
			userLogs.add(s);
			UI.receiveSimpleLog(s);
		} else {
			Tuple<String,String> out = new Tuple<>(buildTupleDate(date), buildTupleMessageOutput(msg));
			allLogs.add(out);
			UI.receiveCompleteLog(out);
		}

	}
	
	public static void systemLog(String msg) {
		String date = dateformat.format(new Date());

		Tuple<String,String> sys = new Tuple<>(buildTupleDate(date), buildTupleMessageSystem(msg));
		allLogs.add(sys);
		userLogs.add(sys);
		UI.receiveSystemLog(sys);

	}
	
	public static String simpleLogsAsString() {
		StringBuilder sb = new StringBuilder(30*userLogs.size());
		for (Tuple<String,String> tup: userLogs)
			sb.append(tup.getKey()).append(" ").append(tup.getValue()).append("\n");
		return sb.toString();
	}
	
	public static String completeLogsAsString() {
		StringBuilder sb = new StringBuilder(30*allLogs.size());
		for (Tuple<String,String> tup: allLogs)
			sb.append(tup.getKey()).append(" ").append(tup.getValue()).append("\n");
		return sb.toString();
	}
	
	private static String buildTupleDate(String d) {
		return new StringBuilder("[").append(d).append("]").toString();
	}
	
	private static String buildTupleMessageSystem(String m) {
		return new StringBuilder("(System) ").append(m).toString();
	}
	
	private static String buildTupleMessageInput(String m) {
		return new StringBuilder("> ").append(m).toString();
	}
	
	private static String buildTupleMessageOutput(String m) {
		return new StringBuilder("< ").append(m).toString();
	}
	
}
