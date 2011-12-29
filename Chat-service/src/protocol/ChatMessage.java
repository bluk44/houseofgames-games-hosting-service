package protocol;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ChatMessage implements Serializable{
	public static enum Type {
		HELLO,
		OK,
		SENDTO,
		NAME,
		LIST,
		ERR,
		ID
	}

	private Type messageType;
	private String[] messageContent;
	
	public ChatMessage(Type type, String[] messageContent){
		this.messageType = type;
		this.messageContent = messageContent;
	}
	public ChatMessage(){
		
	}
	public Type getType(){
		return messageType;
	}
	public void setType(ChatMessage.Type type){
		this.messageType = type;
	}
	public String[] getContent(){
		return messageContent;
	}
	public void setContent(String[] messageContent){
		this.messageContent = messageContent;
	}
	public String toString(){
	String message = "";
	for(int i=0;i<messageContent.length;i++){
		message += messageContent[i] + "\n";
	}
	return message;
	}
}
