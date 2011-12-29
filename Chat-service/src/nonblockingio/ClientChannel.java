package nonblockingio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

public class ClientChannel implements Runnable {
	protected SocketAddress serverAddress = null;
	protected SocketChannel channel = null;
	protected ByteBuffer buffer = null;
	protected WritableByteChannel output = Channels.newChannel(System.out);

	private static String SERVER_IP = "localhost";
	private static int PORT = 11111;
	private static int BSIZE = 1024;
	private boolean go = true;

	public ClientChannel() {
			serverAddress = new InetSocketAddress(PORT);
			
	}

	@Override
	public void run() {
		
		try {
			
			channel = SocketChannel.open();
		//	channel.configureBlocking(false);
			channel.connect(serverAddress);
			buffer = ByteBuffer.allocate(BSIZE);
			while (channel.read(buffer)!=-1) {
				buffer.flip();
				String str = new String(buffer.array());
				System.out.println(str);
				buffer.clear();	
			}
		} catch (IOException e) {
			System.out.println("[ClientChannel] " + e);
			e.printStackTrace();
		}

	}

}
