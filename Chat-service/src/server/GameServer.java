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
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;



public class GameServer {
	private static int DEFAULT_PORT = 10000;
	private ChannelFactory channelFactory;
	private ServerBootstrap serverBootstrap;
	private ChannelGroup allChannels = new DefaultChannelGroup("all clients");

	public void start() {
		channelFactory = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());
		serverBootstrap = new ServerBootstrap(channelFactory);
		serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() {
				return Channels.pipeline(new ChatServerHandler(allChannels));
			}
		});

		serverBootstrap.setOption("child.tcpNoDelay", true);
		serverBootstrap.setOption("child.keepAlive", true);

		Channel serverChannel = serverBootstrap.bind(new InetSocketAddress(
				DEFAULT_PORT));
		//allChannels.add(serverChannel);
	}

	public void stop() {
		ChannelGroupFuture closeFuture = allChannels.close();
		closeFuture.awaitUninterruptibly();
		channelFactory.releaseExternalResources();
	}
	
}
