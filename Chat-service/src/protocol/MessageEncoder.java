package protocol;

import static org.jboss.netty.buffer.ChannelBuffers.*;

import java.io.ObjectOutputStream;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferOutputStream;
import org.jboss.netty.buffer.DynamicChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

public class MessageEncoder extends OneToOneEncoder {
	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		ChannelBuffer buffer = dynamicBuffer(100);
		ObjectOutputStream out = new ObjectOutputStream(new ChannelBufferOutputStream(buffer));
		out.writeObject(msg);
		buffer.writeBytes(FrameDelimiter.bytes());
		return buffer;
	}

}
