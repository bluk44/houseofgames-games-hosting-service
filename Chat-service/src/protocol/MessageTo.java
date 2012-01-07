package protocol;

public class MessageTo extends ChatMessage {
	private int sender;
	private int receiver;
	private String[] content;
	
	public MessageTo(int sender, int receiver, String[] content){
		this.sender = sender;
		this.receiver = receiver;
		this.content = content;
	}

	public int getSender() {
		return sender;
	}

	public int getReceiver() {
		return receiver;
	}

	public String[] getContent() {
		return content;
	}

	@Override
	public String toString() {
		return "MessageTo [sender=" + sender + ", receiver=" + receiver + "content: "+content[0]+"]";
	}

}
