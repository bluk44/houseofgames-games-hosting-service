package nonblockingio;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClientChannel client = new ClientChannel();
//		ServerChannel server = new ServerChannel();
		ChargenServer server = new ChargenServer();
		Thread serverThread = new Thread(server);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread clientThread = new Thread(client);
		serverThread.start();
		clientThread.start();
		
	}

}
