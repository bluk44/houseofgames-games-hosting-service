package events;

import java.util.EventListener;

public interface ClientDroppedListener extends EventListener {
	public void ClientDropped(ClientDroppedEvent evt);
}
