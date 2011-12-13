package client;

import static org.junit.Assert.fail;

import java.net.ServerSocket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ClientConsoleTests {

	ServerSocket server;

	@Before
	public void setUp() throws Exception {
		server = new ServerSocket(10000);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void StartStopTestt() {
		ClientConsole client = new ClientConsole("client1", "localhost", 10000);
		client.initConnection();
		client.startReceiving();

		try {
			Thread.sleep(100);
			client.stopReceiving();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {

			client.listeningThread.join();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
