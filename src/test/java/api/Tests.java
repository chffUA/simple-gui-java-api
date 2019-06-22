package api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

import org.json.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;

public class Tests {
	
	private String b = "http://localhost:8080/";
	
	@BeforeClass
	public static void setUpClass() {
		SpringApplication.run(Application.class, new String[0]);
	}
	
	@Test
	public void user() throws JSONException, IOException, NoSuchAlgorithmException {
		JSONObject j = new JSONObject();
		j.put("name", "n");
		sendPost(b+"user",j);
	}
	
	@Test
	public void item() throws JSONException, IOException, NoSuchAlgorithmException {
		JSONObject j = new JSONObject();
		j.put("name", "n");
		j.put("price", 3.3);
		sendPost(b+"item",j);
	}
	
	@Test
	public void itemGet() throws Exception {
		sendGet(b+"item/1");
	}
	
	private void sendPost(String urlstr, JSONObject body) throws IOException {
		String urlParameters = body.toString();
		URL url = new URL(urlstr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestProperty("Content-type", "application/json");
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		

		OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

		writer.write(urlParameters);
		writer.flush();

		String line;
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		
		int responseCode = conn.getResponseCode();
		System.out.println("Sending 'POST' request to URL : " + urlstr);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		while ((line = reader.readLine()) != null) {
		    System.out.println(line);
		}
		writer.close();
		reader.close();  
		
	}
	
	private void sendGet(String url) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("GET");

		String urlParameters = "";
		
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("Sending 'GET' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//print result
		System.out.println(response.toString()+"\n");

	}
}
