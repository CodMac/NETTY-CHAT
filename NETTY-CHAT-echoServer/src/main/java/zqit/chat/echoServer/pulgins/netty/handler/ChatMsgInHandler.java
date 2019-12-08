package zqit.chat.echoServer.pulgins.netty.handler;

import java.net.SocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import zqit.chat.base.util.JacksonUtil;
import zqit.chat.echoServer.pulgins.netty.po.ChatMsgPo;
import zqit.chat.echoServer.pulgins.netty.protocol.nettyChatProtocol.NettyChatMsgModule;
import zqit.chat.echoServer.pulgins.netty.protocol.nettyChatProtocol.NettyChatPtcol;

/**
 * server-ChatMsgInHandler
 * @author mac
 *
 */
public class ChatMsgInHandler extends ChannelInboundHandlerAdapter{

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
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
		 * 3. to目标在线->转发消息 / to目标离线->保存未读消息
		 * 4. 判断是否form/to是否开启消息漫游
		 */
		ChatMsgPo chatMsgPo = JacksonUtil.dateInstance().json2pojo(body, ChatMsgPo.class);
		
		
//		NettyChatPtcol newMsg = new NettyChatPtcol();
//		newMsg.setMsgModule(NettyChatMsgModule.chat.getModuleIndex());
//		newMsg.setBody("服务器收到消息, 并返回该提示!");
//		ctx.writeAndFlush(newMsg);
		
	}
	
}
