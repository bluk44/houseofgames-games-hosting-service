package protocol;

import java.io.ObjectInputStream;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferInputStream;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;

public class MessageDecoder extends OneToOneDecoder {

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		System.out.println(msg.getClass());
		System.out.println(channel.getId());
		ChannelBuffer buff = (ChannelBuffer)msg;
		
		//ChannelBufferInputStream str = new ChannelBufferInputStream(buff);
		
		ObjectInputStream in = new ObjectInputStream(new ChannelBufferInputStream(buff));
		Object chuj = in.readObject();
		
//		Message message = (Message)in.readObject();
//		String message = buff.toString(Charset.defaultCharset());
		return chuj;
	}

}
