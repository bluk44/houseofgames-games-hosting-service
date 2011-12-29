package nonblockingio;

import java.nio.*;

import java.nio.channels.*;

import java.net.*;

import java.io.IOException;

public class ChargenClient {

	public static int DEFAULT_PORT = 11111;
	public static String IPADDERSS = "localhost";
	public static void main(String[] args) {
		
	


		try {
			
			Thread server = new Thread(new ChargenServer());
			server.start();
			Thread.sleep(1000);
			SocketAddress address = new InetSocketAddress(IPADDERSS, DEFAULT_PORT);

			SocketChannel client = SocketChannel.open(address);

			ByteBuffer buffer = ByteBuffer.allocate(74);

			WritableByteChannel out = Channels.newChannel(System.out);

			while (client.read(buffer) != -1) {

				buffer.flip();

				out.write(buffer);

				buffer.clear();

			}

		}

		catch (IOException ex) {

			ex.printStackTrace();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
