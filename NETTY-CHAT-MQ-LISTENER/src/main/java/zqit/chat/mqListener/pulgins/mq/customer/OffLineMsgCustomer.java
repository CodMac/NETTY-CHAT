package zqit.chat.mqListener.pulgins.mq.customer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;

import zqit.chat.base.util.JacksonUtil;
import zqit.chat.mqListener.pulgins.mq.customer.po.ChatMsgPo;

@Configuration
public class OffLineMsgCustomer {
	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 离线消息处理 消费者
	 * @param oldUrl
	 * @throws IOException 
	 */
	@JmsListener(destination = "${activeMq.offLineMsg-queueName}")
	public void consumerMsg(String msg) throws IOException{
		LOGGER.info("OffLineMsgCustomer(离线消息处理 消费者) 开始消费 msg: " + msg);
		ChatMsgPo chatMsgPo = JacksonUtil.dateInstance().json2pojo(msg, ChatMsgPo.class);
		String toUuid = chatMsgPo.getToUuid();
		String fromUuid = chatMsgPo.getFromUuid();
		System.out.println(fromUuid + " 给 " + toUuid + "发送了消息, 由于 " + toUuid + "离线,先离线保存!");
		LOGGER.info("OffLineMsgCustomer(离线消息处理 消费者) 消费完成 msg: " + msg);
		
    }
}
