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
import java.util.concurrent.Executors;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import chat.Room;

public class GameServer {
	private static int DEFAULT_PORT = 10000;
	private ChannelFactory channelFactory;
	private ServerBootstrap serverBootstrap;
	private Room chatRoom = new Room("BDSM");
	public static final ChannelGroup allChannels = new DefaultChannelGroup("all clients");
	{
		BasicConfigurator.configure();
	}
	private Logger logger = Logger.getLogger(getClass());
	public void start() {
		channelFactory = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());

		serverBootstrap = new ServerBootstrap(channelFactory);
		serverBootstrap.setPipelineFactory(new GameServerPipelineFactory());
		serverBootstrap.setOption("child.tcpNoDelay", true);
		serverBootstrap.setOption("child.keepAlive", true);

		Channel serverChannel = serverBootstrap.bind(new InetSocketAddress(
				DEFAULT_PORT));
		logger.info("server started");
		
		// allChannels.add(serverChannel);
	}

	public void stop() {
		logger.info("stopping server");
		ChannelGroupFuture closeFuture = allChannels.close();
		closeFuture.awaitUninterruptibly();
		channelFactory.releaseExternalResources();
		logger.info("server stopped");
	}

}
