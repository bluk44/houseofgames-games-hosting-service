package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import events.MessageReceivedEvent;

public class ClientHandler {

	private String name;
	private Integer ID;

	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	private ClientMessageReceiver messageReceiver;
	
	private boolean receiving = false;
	
	public ClientHandler(Socket socket) throws IOException{
		this.socket = socket;
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		writer = new PrintWriter(socket.getOutputStream());
		messageReceiver = new ClientMessageReceiver(reader);
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
	public boolean getReceiving(){
		return receiving;
	}
	void setReceiving(boolean receiving){
		this.receiving = receiving;
	}
	
}
