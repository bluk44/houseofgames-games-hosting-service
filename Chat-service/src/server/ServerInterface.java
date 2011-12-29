package server;

import java.io.IOException;

import javax.swing.event.EventListenerList;

import events.ClientConnectedListener;
import events.ClientDroppedListener;
import events.MessageReceivedListener;

public interface ServerInterface extends Runnable{
	public void start() throws IOException;
	public void stop() throws IOException;
	public String getIPAdress();
	public Integer getPortNumber();
	
	
}
