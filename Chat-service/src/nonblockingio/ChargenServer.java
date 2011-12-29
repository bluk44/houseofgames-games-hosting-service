package nonblockingio;

import java.nio.*;

import java.nio.channels.*;

import java.net.*;

import java.util.*;

import java.io.IOException;

public class ChargenServer implements Runnable{

	public static int DEFAULT_PORT = 10000;
	
	public void run() {
		int port = DEFAULT_PORT;



		System.out.println("Listening for connections on port " + port);

		byte[] rotation = new byte[95 * 2];

		for (byte i = ' '; i <= '~'; i++) {

			rotation[i - ' '] = i;

			rotation[i + 95 - ' '] = i;

		}

		ServerSocketChannel serverChannel;

		Selector selector;

		try {

			serverChannel = ServerSocketChannel.open();

			ServerSocket ss = serverChannel.socket();

			InetSocketAddress address = new InetSocketAddress(port);

			ss.bind(address);

			serverChannel.configureBlocking(false);

			selector = Selector.open();

			serverChannel.register(selector, SelectionKey.OP_ACCEPT);

		}

		catch (IOException ex) {

			ex.printStackTrace();

			return;

		}

		while (true) {

			try {

				selector.select();

			}

			catch (IOException ex) {

				ex.printStackTrace();

				break;

			}

			Set readyKeys = selector.selectedKeys();

			Iterator iterator = readyKeys.iterator();

			while (iterator.hasNext()) {

				SelectionKey key = (SelectionKey) iterator.next();

				iterator.remove();

				try {

					if (key.isAcceptable()) {

						ServerSocketChannel server = (ServerSocketChannel) key
								.channel();

						SocketChannel client = server.accept();

						System.out
								.println("Accepted connection from " + client);

						client.configureBlocking(false);

						SelectionKey key2 = client.register(selector,
								SelectionKey.

								OP_WRITE);

						ByteBuffer buffer = ByteBuffer.allocate(74);
						buffer.put("hello".getBytes());
//						buffer.put(rotation, 0, 72);
//
//						buffer.put((byte) '\r');
//
//						buffer.put((byte) '\n');
//
//						buffer.flip();

						key2.attach(buffer);

					}

					else if (key.isWritable()) {
						SocketChannel client = (SocketChannel) key.channel();
						ByteBuffer buffer = (ByteBuffer) key.attachment();
						if(!buffer.hasRemaining()){
							buffer.clear();
							
							buffer.put("hello".getBytes());
							buffer.flip();
							
							
							int x =0;
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} 
						client.write(buffer);

					}

				}

				catch (IOException ex) {

					key.cancel();

					try {

						key.channel().close();

					}

					catch (IOException cex) {
					}

				}

			}

		}

	}

}
