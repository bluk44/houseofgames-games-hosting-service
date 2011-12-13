package protocol;

public class ChatMessage {
	public static enum Type {
		HELLO,
		OK,
		SENDTO,
		NAME,
		LIST,
		ERR,
		ID,
	}

	private Type messageType;
	private String[] messageContent;
	
	public ChatMessage(Type type, String[] messageContent){
		this.messageType = type;
		this.messageContent = messageContent;
	}
	
	public Type getType(){
		return messageType;
	}
	
	public String[] getContent(){
		return messageContent;
	}
}
