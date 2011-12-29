package server;

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

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class GameServer implements ServerInterface, Runnable {

	private static int DEF_PORT = 10000;
	private static int DEF_BSIZE = 1024;

	private ServerSocketChannel serverChannel;
	private ServerSocket serverSocket;
	private SocketChannel client;
	private ByteBuffer readBuffer;
	private InetSocketAddress serverAddress;
	private Selector selector;
	private Logger logger = Logger.getLogger(GameServer.class);
	{
		BasicConfigurator.configure();
	}

	public GameServer() {
		serverAddress = new InetSocketAddress(DEF_PORT);
		logger.info("server port set to " + DEF_PORT);
	}

	public GameServer(int port) {
		serverAddress = new InetSocketAddress(port);
		logger.info("server port set to " + port);
	}

	@Override
	public void start() throws IOException {
		logger.info("starting server");

		serverChannel = ServerSocketChannel.open();
		logger.info("server channel opened");

		serverSocket = serverChannel.socket();
		serverSocket.bind(serverAddress);
		logger.info("server socket bound");

		serverChannel.configureBlocking(false);
		selector = Selector.open();
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);

		while (true) {
			selector.select();

			Set readyKeys = selector.selectedKeys();
			Iterator iterator = readyKeys.iterator();

			while (iterator.hasNext()) {
				SelectionKey key = (SelectionKey) iterator.next();
				iterator.remove();
				// checking type of key
				if (key.isAcceptable()) {
					// new client
					ServerSocketChannel server = (ServerSocketChannel) key
							.channel();
					client = server.accept();
					logger.info("accepted client");

					client.configureBlocking(false);
					SelectionKey skey = client.register(selector,
							SelectionKey.OP_READ);
					readBuffer = ByteBuffer.allocate(DEF_BSIZE);
					skey.attach(readBuffer);

				} else if (key.isReadable()) {
					logger.info("a client is ready to read! chuuuuuuuj");

					SocketChannel client = (SocketChannel) key.channel();
					client.read(readBuffer);
					readBuffer.flip();
					String message = new String(readBuffer.array());
					logger.info("message from client {" + message + "}");
				}
			}
		}

	}

	@Override
	public void stop() throws IOException {
		if (client != null) {
			client.close();
			logger.info("client channel closed");
		}
		if (serverChannel != null) {
			logger.info("closing server channel");
			serverChannel.close();
			logger.info("server channel closed");
		}

	}

	@Override
	public String getIPAdress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getPortNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run() {
		try {
			start();
		} catch (IOException e) {
			logger.error("exception in start", e);
		}

	}

}
