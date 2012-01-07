package server;

import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;

import protocol.FrameDelimiter;
import protocol.MessageDecoder;
import protocol.MessageEncoder;

public class GameServerPipelineFactory implements ChannelPipelineFactory {

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		
		ChannelPipeline pipeline = pipeline();
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, FrameDelimiter.get()));
		pipeline.addLast("message decoder", new MessageDecoder());
		pipeline.addLast("message encoder", new MessageEncoder());
		pipeline.addLast("handler", new GameServerHandler());
		
		return pipeline;
	}

}
