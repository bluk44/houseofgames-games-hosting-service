package protocol;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ChatMessageTests {
	
	ChatMessage message;
	String[] content = {"lol", "lol", "lol"};
	@Before
	public void setUp() throws Exception {
		String[] content2 = {"lol", "lol", "lol"};

		message = new ChatMessage(ChatMessage.Type.HELLO, content2);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		assertEquals(ChatMessage.Type.HELLO, message.getType());
		assertArrayEquals(content, message.getContent());
	}

}
