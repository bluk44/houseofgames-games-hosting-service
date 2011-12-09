package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConsole extends Thread {
	private Socket socket = null;
	private InputStream is = null;
	private OutputStream os = null;
	private PrintWriter writer = null;
	private BufferedReader reader = null;

	private boolean read = true;
	private void initConnection() {
		try {
			socket = new Socket("localhost", 10000);
			is = socket.getInputStream();
			os = socket.getOutputStream();
			writer = new PrintWriter(os);
			reader = new BufferedReader(new InputStreamReader(is));
			
		} catch (UnknownHostException e) {
			System.out.println("[Client]: " + e.toString());
		} catch (IOException e) {
			System.out.println("[Client]: " + e.toString());
		}
	}
	public ClientConsole(){
		super();
	}
	public void sendMessage(String message){
		if(writer != null){
			writer.println(message);
			writer.flush();
		}
	}
	
	public void run(){
		initConnection();
		while(read){
			String message = null;
			try {
				message = reader.readLine();
				System.out.println(message);
			} catch (IOException e) {
				System.out.println("[Client]: error reading line");
				e.printStackTrace();
			} 
		}
	}
}
