package server;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class GameServerHandler extends SimpleChannelHandler {
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e){
		System.out.println(e.getMessage());
	}
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e){
		GameServer.allChannels.add(e.getChannel());
		
	}
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e){
		GameServer.allChannels.remove(e.getChannel());
		
	}
}
