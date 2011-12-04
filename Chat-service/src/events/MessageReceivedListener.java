package events;

import java.util.EventListener;

public interface MessageReceivedListener extends EventListener {
	public void MessageRecieved(MessageReceivedEvent evt);
}
