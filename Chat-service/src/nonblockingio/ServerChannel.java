package nonblockingio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ServerChannel implements Runnable {
	protected ServerSocketChannel serverChannel = null;
	protected SocketChannel client = null;
	protected ByteBuffer buffer = null;
	
	private static int serverPort = 10000;
	private static int BSIZE = 1024;

	public ServerChannel() {
		try {
			// open server_socket channel
			serverChannel = ServerSocketChannel.open();
			// bind to local port
			ServerSocket ss = serverChannel.socket();
			ss.bind(new InetSocketAddress("localhost", serverPort));
			
			// allocate buffer
			buffer = ByteBuffer.allocate(BSIZE);

		} catch (IOException e) {
			System.out.println("[ServerChannel] " + e);
			e.printStackTrace();
		}

	}
	
	@Override
	public void run() {
		
		try {
			client = serverChannel.accept();
			client.configureBlocking(false);
			System.out.println(client.getRemoteAddress());
			Selector selector = Selector.open();
			client.register(selector, SelectionKey.OP_WRITE);
			while(true){
				selector.select();
				
				Set readyKeys = selector.selectedKeys( );

				Iterator iterator = readyKeys.iterator( );
				
				while (iterator.hasNext( )) {

				  SelectionKey key = (SelectionKey) (iterator.next( ));

				  // Remove key from set so we don't process it twice

				  iterator.remove( );
				  if(key.isWritable()){
					  SocketChannel cl1 = (SocketChannel) key.channel();
					  if(buffer.hasRemaining()){
					  buffer.put((byte)'a');
					  }
					//  buffer.flip();
					  client.write(buffer);
				  }
				  
				}

				
			}
		} catch (IOException e) {
			System.out.println("[ServerChannel] " + e);
		}

	}

}
