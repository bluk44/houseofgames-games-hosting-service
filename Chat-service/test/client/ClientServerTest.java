package client;

import server.GameServer;

public class ClientServerTest {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		GameServer server = new GameServer();
		Client client1 = new Client("pierwszy");
	//	Client client2 = new Client("drugi");
		
		server.start();
		Thread.sleep(1000);
		
		client1.initConnection();
		//client2.initConnection();
		
		Thread.sleep(1000);
				
		client1.sendMessage("Hello", 0);
		//client2.sendMessage("Oi", 1);
		
		Thread.sleep(2000);
//		client1.closeConnection();
//		client2.closeConnection();
//		server.stop();
	}

}
