package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.event.EventListenerList;

import protocol.ChatMessage;
import events.MessageReceivedEvent;
import events.MessageReceivedListener;

public class ClientHandler implements Runnable{

	private String name;
	private Integer ID;

	private Socket socket;
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	
	private boolean receiving = false;
	
	private EventListenerList listenerList = new EventListenerList();
	
	private void fireMessageReceivedEvent(MessageReceivedEvent evt) {
		MessageReceivedListener[] listeners = listenerList
				.getListeners(MessageReceivedListener.class);
		for (int i = 0; i < listeners.length; i += 2) {
			listeners[i].MessageRecieved(evt);
		}
	}
	
	public ClientHandler(Socket socket) throws IOException{
		this.socket = socket;
		reader = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		writer = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}

	public void addMessageReceivedListener(MessageReceivedListener listener) {
		listenerList.add(MessageReceivedListener.class, listener);
	}

	public void removeMessageReceivedListener(MessageReceivedListener listener) {
		listenerList.remove(MessageReceivedListener.class, listener);
	}
	
	public void startReceiving(){
		receiving = true;
		while(receiving){
			try {
				if(reader.available()>0){
					ChatMessage message = (ChatMessage)reader.readObject();
					fireMessageReceivedEvent(new MessageReceivedEvent(this));
					String[] cont = message.getContent();
					for(int i=0;i<cont.length;i++){
						cont[i] = cont[i].toUpperCase();
					}
					message.setContent(cont);
					writer.writeObject(message);
					writer.flush();

				}
			} catch (IOException e) {
				System.out.println("[Client"+"#"+ID+"]: input stream error");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("[Client"+"#"+ID+"]: ChatMessage class not found");
				e.printStackTrace();
			}
		}
	}
	
	
	@Override
	public void run() {
		startReceiving();
		
	}
	
}
