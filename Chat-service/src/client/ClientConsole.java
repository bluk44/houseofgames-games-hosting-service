package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

public class ClientConsole extends Client{
	
	protected SocketAddress serverAddress = null;
	protected SocketChannel channel = null;
	protected ByteBuffer readBuffer = null;
	protected ByteBuffer writeBuffer = null;
	protected WritableByteChannel output = Channels.newChannel(System.out);
	
	protected static String DEF_HOSTNAME = "localhost";
	protected static int DEF_SERVER_PORT = 10000;
	protected static int BSIZE = 1024;
	
	private Thread readingThread;
	private class ReadingTask implements Runnable{

		@Override
		public void run() {
			int t = 9;
			System.out.println(t);
			readBuffer = ByteBuffer.allocate(BSIZE);
			while(true){
				try {
					int bytesRead = channel.read(readBuffer);
					System.out.println("lol");
					readBuffer.flip();
					String message = new String(readBuffer.array());
					System.out.println(message);
					readBuffer.clear();
					
				} catch (IOException e) {
					System.out.println("stopping reading thread");
					return;
					//e.printStackTrace();
				}
			}
			
		}
		
	}
	
	public ClientConsole(){
		super();
	}
	public ClientConsole(String name){
		this.name = name;
	}
	@Override
	public void initConnection(String hostname, int port) throws IOException{
		serverAddress = new InetSocketAddress(hostname, port);
		channel = SocketChannel.open();
		System.out.println("channel opened");
	//	channel.configureBlocking(false);
		channel.connect(serverAddress);
		System.out.println("connected");
		readingThread = new Thread(new ReadingTask(), "reading thread");
		readingThread.start();
	}
	public void initConnection() throws IOException{
		initConnection(DEF_HOSTNAME, DEF_SERVER_PORT);
	}
	@Override
	public void closeConnection() throws IOException{
		channel.close();		
	}
	@Override
	public void sendMessage(String message) throws IOException {
		if(writeBuffer == null){
			writeBuffer = ByteBuffer.allocate(BSIZE);
		}
		writeBuffer.clear();
		writeBuffer.put(message.getBytes());
		writeBuffer.flip();
		channel.write(writeBuffer);
	}
	

}
