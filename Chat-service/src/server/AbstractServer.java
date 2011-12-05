package server;

import javax.swing.event.EventListenerList;

import events.ClientConnectedListener;
import events.ClientDroppedListener;
import events.MessageReceivedListener;

public interface AbstractServer {
	public void start();
	public void stop();
	public String getIPAdress();
	public Integer getPortNumber();
	public void addClientConnectedListener(ClientConnectedListener listener);
	public void removeClientConnectedListener(ClientConnectedListener listener);
	public void addClientDroppedListener(ClientDroppedListener listener);
	public void removeClientDroppedListener(ClientDroppedListener listener);
	public void addMessageRecievedListener(MessageReceivedListener listener);
	public void removeMessageRecievedEvent(MessageReceivedListener listener);
	
	final EventListenerList listenerList = null;
	
}
