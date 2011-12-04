package events;

import java.util.EventObject;

public class ClientConnectedEvent extends EventObject {
	private int clientID;

	private ClientConnectedEvent(Object source) {
		super(source);
	}

	public ClientConnectedEvent(Object source, int clientID) {
		this(source);
		this.clientID = clientID;
	}

	public int getClientID() {
		return clientID;
	}
}
