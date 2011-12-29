package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import events.ClientConnectedEvent;
import events.ClientConnectedListener;
import events.ClientDroppedListener;
import events.MessageReceivedListener;

public class GameServer implements AbstractServer {
	public GameServer(int portNumber) {
		try {
			server = new ServerSocket(portNumber);
		} catch (IOException e) {
			System.out.println("[GameServer] error creating ServerSocket");
			e.printStackTrace();
		}
	}

	@Override
	public void start() {
		if (server == null) {
			System.out.println("server not started");
		}
		System.out.println("[GameServer] starting accepting thread");
		acceptingThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (accept) {
					try {
						// CZEKANIE NA KLIENTA
						Socket newClientSocket = server.accept();
						if (clients == null) {
							clients = new ArrayList<ClientHandler>();
						}
						// KLIENT POLACZONY
						// ODPALIC ZDARZENIE
						fireClientConnectedEvent(new ClientConnectedEvent(this,
								clientCounter));
						// STWORZYC WATEK
						ClientHandler clientHandler = new ClientHandler(
								newClientSocket);
						clientHandler.setID(clientCounter++);
						if (messageListener != null) {
							clientHandler
									.addMessageReceivedListener(messageListener);
						}
						clients.add(clientHandler);
						// WYSTARTOWAC WATEK
						(new Thread(clientHandler)).start();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		});
		acceptingThread.start();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addClientConnectedListener(ClientConnectedListener listener) {
		listenerList.add(ClientConnectedListener.class, listener);
	}

	@Override
	public void removeClientConnectedListener(ClientConnectedListener listener) {
		listenerList.remove(ClientConnectedListener.class, listener);

	}

	@Override
	public void addClientDroppedListener(ClientDroppedListener listener) {
		listenerList.add(ClientDroppedListener.class, listener);

	}

	@Override
	public void removeClientDroppedListener(ClientDroppedListener listener) {
		listenerList.remove(ClientDroppedListener.class, listener);

	}

	@Override
	public void addMessageRecievedListener(MessageReceivedListener listener) {
		messageListener = listener;
	}

	@Override
	public void removeMessageRecievedEvent(MessageReceivedListener listener) {
		// TODO Auto-generated method stub

	}

	public void fireClientConnectedEvent(ClientConnectedEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i += 2) {
			if (ClientConnectedListener.class == listeners[i]) {
				((ClientConnectedListener) listeners[i + 1])
						.clientConnected(evt);
			}
		}
	}

	private Integer portNumber = null;
	private ServerSocket server = null;
	private Thread acceptingThread = null;
	private boolean accept = true;
	private ArrayList<ClientHandler> clients = null;
	private int clientCounter = 0;
	private MessageReceivedListener messageListener = null;
	private EventListenerList listenerList = new EventListenerList();

	@Override
	public String getIPAdress() {
		return server.getInetAddress().toString();
	}

	@Override
	public Integer getPortNumber() {
		return portNumber;
	}

}
