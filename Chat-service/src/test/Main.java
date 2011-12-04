package test;

import server.GameServer;
import server.ServerGUI;
import client.ClientConsole;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ServerGUI server = new ServerGUI();
		ClientConsole cl1 = new ClientConsole("spierdalaj");
		cl1.start();
		ClientConsole cl2 = new ClientConsole("ssij jaja");
		cl2.start();
		
	}
}