package test;

import java.io.IOException;

import protocol.ChatMessage;
import server.TestServer;
import client.ClientConsole;

public class Main {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	static TestServer server;
	
	public static void main(String[] args) throws InterruptedException {
		try {
			server = new TestServer(10000);
			
			(new Thread(new Runnable(){
				public void run(){
					server.start();
				}
			})).start();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Thread.sleep(1000);
		ClientConsole cl1 = new ClientConsole("klient", "localhost", 10000);
		cl1.initConnection();
		cl1.startReceiving();
		try {
			while (true) {
				Thread.sleep(500);
				String[] tab = {"chuy", "ci", "w", "tshapke"};
				ChatMessage message = new ChatMessage(ChatMessage.Type.HELLO,
						tab);
				try {
					cl1.sendMessage(message);
				} catch (IOException e) {
					System.out.println("sending message failed");
					e.printStackTrace();
				}
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}