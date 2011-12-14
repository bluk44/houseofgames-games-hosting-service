package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import events.MessageReceivedEvent;

public class ClientHandler implements Runnable{

	private String name;
	private Integer ID;

	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	
	private boolean receiving = false;
	
	public ClientHandler(Socket socket) throws IOException{
		this.socket = socket;
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writer = new PrintWriter(socket.getOutputStream());
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}

	public void startReceiving(){
		receiving = true;
		while(receiving){
			try {
				if(reader.ready()){
					String message = reader.readLine();
				}
			} catch (IOException e) {
				System.out.println("[Client"+"#"+ID+"]: input stream error");
				e.printStackTrace();
			}
		}
	}
	
	
	@Override
	public void run() {
		startReceiving();
		
	}
	
}
