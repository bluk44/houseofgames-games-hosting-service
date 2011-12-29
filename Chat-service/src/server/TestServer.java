package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import protocol.ChatMessage;

import events.ClientConnectedListener;
import events.ClientDroppedListener;
import events.MessageReceivedEvent;
import events.MessageReceivedListener;

public class TestServer implements ServerInterface {

	private String IPAdress = "localhost";
	private int portNumber;
	private ServerSocket server;
	private boolean go = true;
	private Socket clientSocket = null;
	private Thread messageReceiver = null;
	private Thread clientAcceptor = null;
	private ObjectOutputStream writer;
	private ObjectInputStream reader;

	private class ReceivingTask implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					
					ChatMessage message = (ChatMessage) reader.readObject();
					System.out.println(message);
				} catch (ClassNotFoundException e) {
					System.out.println("[TestServer - receiving task] " + e);
					//e.printStackTrace();
				} catch (IOException e) {
					System.out.println("[TestServer - receiving task] " + e);
					break;
					//e.printStackTrace();
				}

			}

		}

	}

	private class AcceptingTask implements Runnable {

		@Override
		public void run() {

			try {
				clientSocket = server.accept();
				System.out
						.println("[TestServer - accepting task] client connected");
				writer = new ObjectOutputStream(clientSocket.getOutputStream());
				writer.flush();
				reader = new ObjectInputStream(clientSocket.getInputStream());
				System.out
						.println("[TestServer] got object streams from client");
				messageReceiver = new Thread(new ReceivingTask(), "receiving thread");
				messageReceiver.start();
				messageReceiver.join();
			} catch (IOException e) {
				System.out.println("[TestServer - accepting task] " + e);
			} catch (InterruptedException e) {
				System.out.println("[TestServer - accepting task] " + e);
				e.printStackTrace();
			}
		}

	}

	public TestServer(int portNumber) throws IOException {
		this.portNumber = portNumber;
	}

	@Override
	public void start() {
		try {
			if(server != null && !server.isClosed()){
				server.close();
			}
			if(clientSocket != null && !clientSocket.isClosed()){
				clientSocket.close();
			}
			server = new ServerSocket(portNumber);
		} catch (IOException e) {
			System.out.println("[TestServer] "+e);
		}
		System.out.println("[TestServer] initializing server");
		clientAcceptor = new Thread(new AcceptingTask(), "accepting thread");
		clientAcceptor.start();
		
	}

	@Override
	public void stop() {
		try {
			if(clientSocket != null){
				clientSocket.close();
			}
			if(server != null){
				server.close();
			}
		} catch (IOException e) {
			System.out.println("[TestServer] " + e);
			e.printStackTrace();
		}

	}

	@Override
	public String getIPAdress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getPortNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addClientConnectedListener(ClientConnectedListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeClientConnectedListener(ClientConnectedListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addClientDroppedListener(ClientDroppedListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeClientDroppedListener(ClientDroppedListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addMessageRecievedListener(MessageReceivedListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeMessageRecievedEvent(MessageReceivedListener listener) {
		// TODO Auto-generated method stub

	}

}
