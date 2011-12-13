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
		ClientConsole cl1 = new ClientConsole("klient", "localhost", 10000);
		cl1.initConnection();
		cl1.startReceiving();
		try {
			while (true) {
				Thread.sleep(500);
				cl1.sendMessage("spierdalay");
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}