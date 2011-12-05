package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.event.EventListenerList;

import events.MessageReceivedEvent;
import events.MessageReceivedListener;

public class ClientThread extends Thread {

	public ClientThread(Socket socket, int clientID) {
		this.clientID = clientID;
		this.socket = socket;
		try {
			is = socket.getInputStream();
			os = socket.getOutputStream();
			reader = new BufferedReader(new InputStreamReader(is));
			writer = new PrintWriter(os);
		} catch (IOException e) {
			System.out.println("[Client#" + clientID
					+ "Thread] error getting streams");
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		try {

			while (receive) {
				String clientMessage = reader.readLine();
				MessageReceivedEvent evt = new MessageReceivedEvent(this);
				evt.setText(clientMessage);
				fireMessageReceivedEvent(evt);
			}

		} catch (IOException e) {
			System.out.println("[Client#" + clientID + "] error reading line");
			e.printStackTrace();
		}

	}

	public void sendToClient(String message) {
		if (writer != null) {
			writer.println(message.toUpperCase());
			writer.flush();
		}
	}

	public void addMessageReceivedListener(MessageReceivedListener listener) {
		listenerList.add(MessageReceivedListener.class, listener);
	}

	public void removeMessageReceivedListener(MessageReceivedListener listener) {
		listenerList.remove(MessageReceivedListener.class, listener);
	}

	public int getClientID() {
		return clientID;
	}

	public void stopReceiving() {
		receive = false;
	}

	private void fireMessageReceivedEvent(MessageReceivedEvent evt) {
		MessageReceivedListener[] listeners = listenerList
				.getListeners(MessageReceivedListener.class);
		for (int i = 0; i < listeners.length; i += 2) {
			listeners[i].MessageRecieved(evt);
		}
	}

	private final int clientID;
	private Socket socket = null;
	private boolean receive = true;
	private InputStream is = null;
	private OutputStream os = null;
	private PrintWriter writer = null;
	private BufferedReader reader = null;
	private EventListenerList listenerList = new EventListenerList();

}
