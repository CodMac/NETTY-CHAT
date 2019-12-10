package zqit.chat.mqListener.pulgins.mq.customer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;

@Configuration
public class RoamMsgCutomer {

private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 漫游消息处理 消费者
	 * @param oldUrl
	 * @throws IOException 
	 */
	@JmsListener(destination = "${activeMq.roamMsg-queueName}")
	public void consumerMsg(String msg) throws IOException{
		LOGGER.info("RoamMsgCutomer(漫游消息处理 消费者) 开始消费 msg: " + msg);
		LOGGER.info("RoamMsgCutomer(漫游消息处理 消费者) 消费完成 msg: " + msg);
		
    }
}
