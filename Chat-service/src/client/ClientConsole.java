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
	private boolean go = true;
	private String messageForServer = null;
	private void initConnection() {
		try {
			socket = new Socket("localhost", 10000);
			is = socket.getInputStream();
			os = socket.getOutputStream();
		} catch (UnknownHostException e) {
			System.out.println("[Client]: " + e.toString());
		} catch (IOException e) {
			System.out.println("[Client]: " + e.toString());
		}
	}
	public ClientConsole(String messageForServer ){
		super();
		this.messageForServer = messageForServer;
	}
	public void run(){
		initConnection();
		PrintWriter writer = new PrintWriter(os);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String message = null ;
		while(go){
			writer.println(messageForServer);
			writer.flush();
			try {
				message = reader.readLine();
				System.out.println(message);
				Thread.sleep(500);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
