package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import protocol.ChatMessage;

public class ClientConsole {
	private String name = null;
	private Integer ID = null;
	private ObjectOutputStream writer = null;
	private Thread messageReceiver = null;
	private Socket connection = null;

	private class ReceivingTask implements Runnable {
		private ObjectInputStream reader = null;

		public ReceivingTask(ObjectInputStream reader) {
			this.reader = reader;
		}

		@Override
		public void run() {
			try {
				while (true) {
					ChatMessage message = (ChatMessage) reader.readObject();
					System.out.println(message);
				}
			} catch (ClassNotFoundException e) {
				System.out.println("[Client] " + name + ": " + e);
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("[Client] " + name + ": " + e);
			} finally {
				closeConnection();
			}

		}

	}

	public ClientConsole(String name) {
		this.name = name;
	}

	public void initConnection(String IPAdress, int port)
			throws UnknownHostException, IOException {
		connection = new Socket(IPAdress, port);
		System.out.println("[Client] connection established");
		System.out.println(connection.getLocalPort());
		writer = new ObjectOutputStream(connection.getOutputStream());
		writer.flush();

		ObjectInputStream reader = new ObjectInputStream(
				connection.getInputStream());
		messageReceiver = new Thread(new ReceivingTask(reader));

		messageReceiver.start();
	}

	public void closeConnection() {
		try {
			if(connection !=null){
				connection.close();
			}
		} catch (IOException e) {
			System.out.println("[Client] " + name + ": " + e);
		}
	}

	public void sendMessage(ChatMessage message) {
		try {
			writer.writeObject(message);
		} catch (IOException e) {
			System.out.println("[Client] " + name + ": " + e);
			e.printStackTrace();
		}
	}
}
