package events;

import java.util.EventObject;

public class MessageReceivedEvent extends EventObject {
	
	public MessageReceivedEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	public void setText(String messageText){
		this.messageText = messageText;
	}
	public String getText(){
		return messageText;
	}
	private String messageText = null;
}
