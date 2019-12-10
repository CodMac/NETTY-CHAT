package zqit.chat.echoServer.pulgins.mq;

import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {

	/**
	 * 离线消息处理 订阅
	 * 
	 * @return
	 */
	@Value("${activeMq.offLineMsg-topicName}")
	private String offLineMsgTopicName;
	@Bean(name = "offLineMsgTopic")
	public Topic offLineMsgTopic() {
		return new ActiveMQTopic(offLineMsgTopicName);
	}

}
