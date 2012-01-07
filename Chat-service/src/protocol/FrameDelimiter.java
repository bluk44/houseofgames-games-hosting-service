package protocol;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public class FrameDelimiter {
	
	public static ChannelBuffer get(){
		return ChannelBuffers.wrappedBuffer(new byte[] { -128, -128, -128, -128 });
	}
	public static byte[] bytes(){
		return new byte[] {-128, -128, -128, -128};
	}
}
