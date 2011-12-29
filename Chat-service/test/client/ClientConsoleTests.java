package client;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import protocol.ChatMessage;

import server.TestServer;

public class ClientConsoleTests {

	TestServer testServer;
	static int PORT = 10000;
	static String IP = "localhost";
	@Before
	public void setUp() throws Exception {
		testServer = new TestServer(PORT);
	}

	@After
	public void tearDown() throws Exception {
	}

	
	public void InitCloseTest() {
		testServer.start();
		ClientConsole client = new ClientConsole("client1");
		try {
			Thread.sleep(1000);
			client.initConnection(IP, PORT);
			Thread.sleep(2000);
			client.closeConnection();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void SuddenServerDeathTest(){
		try{
		testServer.start();
		testServer.stop();
		Thread.sleep(1000);
		testServer.start();
		testServer.stop();
		} catch (Exception e){
			System.out.println(e);
		}
//		ClientConsole client = new ClientConsole("client1");
//		try {
//			Thread.sleep(1000);
//			client.initConnection(IP, PORT);
//			Thread.sleep(2000);
//			testServer.stop();
//			
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
