package zqit.chat.mqListener;

import javax.jms.Queue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MQListenerApp.class })
public class AppTest {

	// MQ
	@Autowired
	private JmsMessagingTemplate jms;
	@Autowired
	@Qualifier("offLineMsgQueue")
	private Queue offLineMsgQueue;
	
	@Test
	public void produceOffLineMsg(){
		jms.convertAndSend(offLineMsgQueue, "未读消息");
	}
}
