package client;

import java.io.IOException;

/**
 * Abstract client
 * 
 * @author Łukasz Budkowski
 * 
 */
public abstract class Client {
	protected String name;
	protected int id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getID() {
		return id;
	}

	public void setID(int iD) {
		id = iD;
	}

	/**
	 * no parameter constructor
	 */
	public Client() {
		name = "unnamed_client";
	}

	
	/**
	 * Tries to connect to specified server
	 *  
	 * @param hostname
	 *            Server ip address
	 * @param port
	 *            Server port
	 */
	abstract void initConnection(String hostname, int port) throws IOException;

	/**
	 * Disconnects from server
	 */
	abstract void closeConnection() throws IOException;

	/**
	 * 
	 * @param message
	 *            sends message to server
	 * @throws IOException 
	 */
	abstract void sendMessage(String message) throws IOException;

}
