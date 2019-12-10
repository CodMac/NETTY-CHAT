package zqit.chat.echoServer.pulgins.netty.handler;

import java.net.SocketAddress;
import java.util.List;

import javax.jms.Queue;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import zqit.chat.base.entity.user.UserMessRoam;
import zqit.chat.base.mapper.user.UserMessRoamMapper;
import zqit.chat.base.util.JacksonUtil;
import zqit.chat.echoServer.pulgins.netty.channelMap.ChannelCache;
import zqit.chat.echoServer.pulgins.netty.po.ChatMsgPo;
import zqit.chat.echoServer.pulgins.netty.protocol.nettyChatProtocol.NettyChatMsgModule;
import zqit.chat.echoServer.pulgins.netty.protocol.nettyChatProtocol.NettyChatPtcol;

/**
 * server-ChatMsgInHandler
 * @author mac
 *
 */
@Sharable
@Scope("prototype")
@Component
public class ChatMsgInHandler extends ChannelInboundHandlerAdapter{

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	// MQ
	@Autowired
	private JmsMessagingTemplate jms;
	@Autowired
	@Qualifier("offLineMsgQueue")
	private Queue offLineMsgQueue;
	@Autowired
	@Qualifier("roamMsgQueue")
	private Queue roamMsgQueue;
	
	@Autowired
	ChannelCache<String, Channel> channelCache;
	
	@Autowired
	UserMessRoamMapper userMessRoamMapper;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Channel channel = ctx.channel();
		SocketAddress remoteAddress = channel.remoteAddress();
		
		NettyChatPtcol nettyChatMsg = (NettyChatPtcol) msg;
		byte msgModule = nettyChatMsg.getMsgModule();
		String body = nettyChatMsg.getBody();
		LOGGER.debug("server-ChatMsgInHandler=channelRead: 收到新消息! 来源client("+remoteAddress.toString()+"), 内容("+nettyChatMsg.toString()+")");
		
		//非聊天消息, 则消息继续下发到下一个Handler
		if(msgModule != NettyChatMsgModule.chat.getModuleIndex()){
			//下发到nextInHandler
			ctx.fireChannelRead(msg);
			return;
		}
		
		/**
		 * 聊天消息处理
		 * 1. 消息转换为po
		 * 2. 判断to目标 是否在线
		 * 3. to目标在线->转发消息 / to目标离线->用户离线信息处理
		 * 4. 判断是否form/to是否开启消息漫游
		 */
		ChatMsgPo chatMsgPo = JacksonUtil.dateInstance().json2pojo(body, ChatMsgPo.class);
		String toUuid = chatMsgPo.getToUuid();
		String fromUuid = chatMsgPo.getFromUuid();
		//2. 判断to目标 是否在线
		boolean online = channelCache.isOnline(toUuid);
		//3. to目标在线->转发消息 / to目标离线->用户离线信息处理
		if(online == true){
			//to目标在线->转发消息
			Channel toChannel = channelCache.get(toUuid);
			NettyChatPtcol newMsg = new NettyChatPtcol();
			newMsg.setMsgModule(NettyChatMsgModule.chat.getModuleIndex());
			newMsg.setBody(body);
			toChannel.writeAndFlush(newMsg);
		}else {
			//to目标离线->用户离线信息处理
			jms.convertAndSend(offLineMsgQueue, body);
		}
		//4. 判断是否form/to是否开启消息漫游
		UserMessRoam userMessRoamQuery = new UserMessRoam();
		userMessRoamQuery.setFromUuid(fromUuid);
		userMessRoamQuery.setToUuid(toUuid);
		List<UserMessRoam> umrList = userMessRoamMapper.select(userMessRoamQuery);
		if(!CollectionUtils.isEmpty(umrList)){
			jms.convertAndSend(roamMsgQueue, body);
		}
	}
	
}
