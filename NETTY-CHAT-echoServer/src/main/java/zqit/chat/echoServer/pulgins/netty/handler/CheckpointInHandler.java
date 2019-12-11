package zqit.chat.echoServer.pulgins.netty.handler;

import java.net.SocketAddress;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import zqit.chat.echoServer.pulgins.netty.channelMap.ChannelCache;
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
	
	@Autowired
	ChannelCache<String, Channel> channelCache;
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		SocketAddress remoteAddress = channel.remoteAddress();
		LOGGER.info("server-状态检查站-InHandler=channelActive: 通道建立! 来源client("+remoteAddress.toString()+")");
		
		//IP黑名单拦截
		blackIpFilter(ctx);
	}
	

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		SocketAddress remoteAddress = channel.remoteAddress();
		LOGGER.info("server-状态检查站-InHandler=channelActive: 通道已关闭! 来源client("+remoteAddress.toString()+")");
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
				//登录请求: 下发到nextInHandler(LoginInHandler), 登录成功后会移除(LoginInHandler)
				ctx.fireChannelRead(msg);
				return;
			}else if(msgModule == NettyChatMsgModule.heart.getModuleIndex()){
				heartPong(ctx);
				return;
			}else{
				LOGGER.error("server-状态检查站-InHandler=channelRead: 用户未登录, channel通道关闭! 来源client("+remoteAddress.toString()+")");
				destoryCtx(ctx);
				return;
			}
		}
		
		//心跳消息 -> 返回心跳信息: pong
		if(msgModule == NettyChatMsgModule.heart.getModuleIndex()){
			heartPong(ctx);
			return;
		}
		
		//下发到nextInHandler
		ctx.fireChannelRead(msg);
		
	}
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		Channel channel = ctx.channel();
		IdleStateEvent stateEvent = (IdleStateEvent) evt;
		
		switch (stateEvent.state()) {
        case READER_IDLE:
        	//n秒没收到client端的信息啦
        	AttributeKey<Integer> heartTimesKey = AttributeKey.valueOf("heartTimes");
    		Attribute<Integer> heartTimes = channel.attr(heartTimesKey);
    		Integer heart = heartTimes.get()==null?0:heartTimes.get();
    		if(heart > 12){
    			//连续12次心跳失败, 关闭通道
    			destoryCtx(ctx);
    		}else{
    			heartTimes.set(heart++);
    		}
            break;
        case WRITER_IDLE:
            break;
        case ALL_IDLE:
            break;  
        default:
            break;
        }
	}
	
	//黑名单IP拦截
	private void blackIpFilter(ChannelHandlerContext ctx) {
	}
	
	//关闭通道
	private void destoryCtx(ChannelHandlerContext ctx){
		Channel channel = ctx.channel();
		//channelCache移除通道
		AttributeKey<String> uuidKey = AttributeKey.valueOf("uuid");
		Attribute<String> uuidAttr = channel.attr(uuidKey);
		String uuid = uuidAttr.get();
		if(!StringUtils.isEmpty(uuid)){
			channelCache.remove(uuid);
		}
		//关闭通道
		ctx.close();
		channel.disconnect();
		channel.close();
	}
	
	//发送心跳回应pong
	private void heartPong(ChannelHandlerContext ctx){
		Channel channel = ctx.channel();
		//回应pong
		NettyChatPtcol pongMsg = new NettyChatPtcol();
		pongMsg.setMsgModule(NettyChatMsgModule.heart.getModuleIndex());
		pongMsg.setBody("pong");
		ctx.writeAndFlush(pongMsg);
		//重置心跳原数
		AttributeKey<Integer> heartTimesKey = AttributeKey.valueOf("heartTimes");
		Attribute<Integer> heartTimes = channel.attr(heartTimesKey);
		heartTimes.set(0);
	}
	
}
