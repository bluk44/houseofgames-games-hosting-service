package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConsole {
	private int portNumber;
	private String IPAdress = null;
	private String name = null;
	private Socket socket = null;
	private PrintWriter writer = null;
	private BufferedReader reader = null;
	public Thread listeningThread = null;
	private boolean listen = true;
	private boolean connected = false;
	private boolean listening = false;

	public void initConnection() {
		try {
			socket = new Socket(IPAdress, portNumber);
			writer = new PrintWriter(socket.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			connected = true;
		} catch (UnknownHostException e) {
			System.out.println("[Client]: " + e.toString());
		} catch (IOException e) {
			System.out.println("[Client]: " + e.toString());
		}
	}
	
	public void closeConnection(){
		if(connected){
			if(listening){
				stopReceiving();
				listening = false;
				try {
					socket.close();
				} catch (IOException e) {
					System.out.println("[Client] "+ e);
					e.printStackTrace();
				}
			}
		}
	}
	
	public ClientConsole(String name, String IPAdress, int portNumber) {
		this.IPAdress = IPAdress;
		this.portNumber = portNumber;
		this.name = name;
	}

	public void startReceiving() {
		if (!connected) {
			System.out.println("Client " + name + "not connected");
			return;
		}
		listen = true;
		listening = true;
		listeningThread = new Thread(new Runnable() {
			public void run() {
				while (listen) {
					try {
						if (reader.ready()) {
							String message = reader.readLine();
							System.out.println(message);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		listeningThread.start();
	}

	public void stopReceiving() {
		if (listening) {
			listen = false;
		}
	}
	
	public void sendMessage(String message) {
		if (!connected) {
			System.out.println("client " + name + "disconnected");
			return;
		}
		if (writer != null) {
			writer.println(message);
			writer.flush();
		}
	}

	public boolean isConnected(){
		return connected;
	}
	
	public boolean isListening(){
		return listening;
	}
}
