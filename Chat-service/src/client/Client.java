package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import protocol.MessageTo;

/**
 * Abstract client
 * 
 * @author Łukasz Budkowski
 * 
 */
public class Client {
	protected String name;
	protected int id;
	protected static int clientCounter = 0;

	protected ChannelFactory channelFactory;
	protected ClientBootstrap bootstrap;
	protected ChannelFuture channelFuture;
	protected Channel channel;
	protected ChannelBuffer writeBuffer;

	protected static String DEF_HOSTNAME = "localhost";
	protected static int DEF_SERVER_PORT = 10000;
	
	protected Logger logger = Logger.getLogger(getClass());
	public void setName(String name) {
		this.name = name;
	}

	public Client() {
		this("bezimienny");
	}

	public Client(String name) {
		this.name = name;
		id = clientCounter++;

	}

	/**
	 * Tries to connect to specified server
	 * 
	 * @param hostname
	 *            Server ip address
	 * @param port
	 *            Server port
	 */
	void initConnection(String hostname, int port) {
		logger.info(name+": connecting to "+hostname+":"+port+" ...");
		channelFactory = new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());

		bootstrap = new ClientBootstrap(channelFactory);
		bootstrap.setPipelineFactory(new ClientPipelineFactory());
		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", true);

		channelFuture = bootstrap.connect(new InetSocketAddress(hostname, port));
		channelFuture.awaitUninterruptibly();
		if (!channelFuture.isSuccess()) {
			channelFuture.getCause().printStackTrace();
			logger.error(name+": connection failed");
		}
		channel = channelFuture.getChannel();
		logger.info(name+": connected");
	}

	/**
	 * Tries to connect to default server
	 */
	void initConnection() {
		initConnection(DEF_HOSTNAME, DEF_SERVER_PORT);
	}

	/**
	 * Disconnects from server
	 */
	void closeConnection() {
		ChannelFuture closeFuture = channel.close();
		closeFuture.awaitUninterruptibly();
		channelFactory.releaseExternalResources();
		logger.info(name+": dziekuje dobranoc");
	}

	/**
	 * 
	 * @param message
	 *            sends message to server
	 * @throws IOException
	 */
	void sendMessage(String message, int playerId) {
		String[] content = { message };
		MessageTo messageTo = new MessageTo(this.id, playerId, content);
		channel.write(message);
		logger.info(name+": wrote to channel");
	}
	/**
	 */
	
}
