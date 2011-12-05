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

	@Override
	public void start() {
		if(server == null){
			try {
				server = new ServerSocket(10000);
				System.out.println("[GameServer] starting accepting thread");
				acceptingThread = new Thread(new Runnable(){

					@Override
					public void run() {
						while(accept){
							try {
								//CZEKANIE NA KLIENTA
								Socket newClientSocket = server.accept();
								if(clients == null){
									clients = new ArrayList<ClientThread>();
								}
								//KLIENT POLACZONY
								//ODPALIC ZDARZENIE
								fireClientConnectedEvent(new ClientConnectedEvent(this, clientCounter));
								//STWORZYC WATEK
								ClientThread newClientThread = new ClientThread(newClientSocket,clientCounter++);
								if(messageListener!=null){
									newClientThread.addMessageReceivedListener(messageListener);
								}
								clients.add(newClientThread);
								//WYSTARTOWAC WATEK
								clients.get(clients.size()-1).start();
								
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					
				});
				acceptingThread.start();
			} catch (IOException e) {
				System.out.println("[GameServer] error creating ServerSocket");
				e.printStackTrace();
			}
		}
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
	
	public void fireClientConnectedEvent(ClientConnectedEvent evt){
		Object[] listeners = listenerList.getListenerList();
		for(int i=0;i<listeners.length;i+=2){
			if(ClientConnectedListener.class == listeners[i]){
				((ClientConnectedListener)listeners[i+1]).clientConnected(evt);
			}
		}
	}
	public void sendToClient(String message, int clientID){
		clients.get(clientID).sendToClient(message);
	}
	private static String IPADRESS = "localhost";
	private static Integer PORTNUMBER = 666;
	private ServerSocket server = null;
	private Thread acceptingThread = null;
	private boolean accept = true;
	private ArrayList<ClientThread> clients = null;
	private int clientCounter = 0;
	private MessageReceivedListener messageListener = null;
	private EventListenerList listenerList = new EventListenerList();
	
	@Override
	public String getIPAdress() {
		return IPADRESS;
	}

	@Override
	public Integer getPortNumber() {
		return PORTNUMBER;
	}

}
