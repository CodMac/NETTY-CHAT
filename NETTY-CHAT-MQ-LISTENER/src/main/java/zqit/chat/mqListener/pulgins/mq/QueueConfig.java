package zqit.chat.mqListener.pulgins.mq;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

	/**
	 * 离线消息处理 队列
	 */
	@Value("${activeMq.offLineMsg-queueName}")
	private String offLineMsgQueueName;
	@Bean(name = "offLineMsgQueue")
	public Queue offLineMsgQueue() {
		return new ActiveMQQueue(offLineMsgQueueName);
	}
	
	/**
	 * 漫游消息处理 队列
	 */
	@Value("${activeMq.roamMsg-queueName}")
	private String roamMsgQueueName;
	@Bean(name = "roamMsgQueue")
	public Queue roamMsgQueue() {
		return new ActiveMQQueue(roamMsgQueueName);
	}
}
