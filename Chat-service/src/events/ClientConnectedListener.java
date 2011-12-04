package events;

import java.util.EventListener;

public interface ClientConnectedListener extends EventListener {
	public void clientConnected(ClientConnectedEvent evt);
	
}
