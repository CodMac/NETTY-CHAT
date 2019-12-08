package zqit.chat.echoServer.pulgins.netty.handler;

import java.net.SocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import zqit.chat.echoServer.pulgins.netty.protocol.nettyChatProtocol.NettyChatMsgModule;
import zqit.chat.echoServer.pulgins.netty.protocol.nettyChatProtocol.NettyChatPtcol;

/**
 * server-状态检查站-InHandler
 * @author mac
 *
 */
@Sharable
@Component
public class CheckpointInHandler extends ChannelInboundHandlerAdapter {

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		SocketAddress remoteAddress = channel.remoteAddress();
		LOGGER.debug("server-状态检查站-InHandler=channelActive: 通道建立! 来源client("+remoteAddress.toString()+")");
		
		//IP黑名单拦截
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Channel channel = ctx.channel();
		SocketAddress remoteAddress = channel.remoteAddress();
		
		NettyChatPtcol nettyChatMsg = (NettyChatPtcol) msg;
		byte msgModule = nettyChatMsg.getMsgModule();
		LOGGER.debug("server-状态检查站-InHandler=channelRead: 收到新消息! 来源client("+remoteAddress.toString()+"), 内容("+nettyChatMsg.toString()+")");
		
		//登录状态控制
		AttributeKey<Boolean> isLoginKey = AttributeKey.valueOf("isLogin");
		Attribute<Boolean> isLoginAttr = channel.attr(isLoginKey);
		Boolean isLogin = isLoginAttr.get();
		if(!isLogin){//未登录
			if(msgModule == NettyChatMsgModule.login.getModuleIndex()){
				//登录请求: 下发到nextInHandler(LoginInHandler)
				ctx.fireChannelRead(msg);
				return;
			}else {
				LOGGER.debug("server-状态检查站-InHandler=channelRead: 用户未登录, channel通道关闭! 来源client("+remoteAddress.toString()+")");
				destoryCtx(ctx);
				return;
			}
		}
		
		//下发到nextInHandler
		ctx.fireChannelRead(msg);
		
	}
	
	//关闭通道
	private void destoryCtx(ChannelHandlerContext ctx){
		Channel channel = ctx.channel();
		ctx.close();
		channel.disconnect();
	}
	
}
