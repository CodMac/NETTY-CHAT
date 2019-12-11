package zqit.chat.echoServer.pulgins.netty.handler;

import java.net.SocketAddress;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import zqit.chat.base.entity.user.User;
import zqit.chat.base.mapper.user.UserMapper;
import zqit.chat.base.pulgins.jwt.JWTUtil;
import zqit.chat.base.util.JacksonUtil;
import zqit.chat.echoServer.pulgins.netty.channelMap.ChannelCache;
import zqit.chat.echoServer.pulgins.netty.po.LoginPo;
import zqit.chat.echoServer.pulgins.netty.protocol.nettyChatProtocol.NettyChatMsgModule;
import zqit.chat.echoServer.pulgins.netty.protocol.nettyChatProtocol.NettyChatPtcol;

/**
 * server-LoginInHandler-登录处理器
 * @author mac
 *
 */
@Sharable
@Component
public class LoginInHandler extends ChannelInboundHandlerAdapter{

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserMapper UserMapper;
	@Autowired
	ChannelCache<String, Channel> channelCache;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Channel channel = ctx.channel();
		SocketAddress remoteAddress = channel.remoteAddress();
		
		NettyChatPtcol nettyChatMsg = (NettyChatPtcol) msg;
		byte msgModule = nettyChatMsg.getMsgModule();
		String body = nettyChatMsg.getBody();
		LOGGER.debug("server-LoginInHandler-登录处理器=channelRead: 收到登录请求! 来源client("+remoteAddress.toString()+"), 内容("+nettyChatMsg.toString()+")");
		
		if(msgModule != NettyChatMsgModule.login.getModuleIndex()){
			LOGGER.error("server-LoginInHandler-登录处理器=channelRead: 消息类型出错, 非登录请求, 通道关闭! 来源client("+remoteAddress.toString()+"), 内容("+nettyChatMsg.toString()+")");
			destoryCtx(ctx);
			return;
		}
		
		/**
		 * 处理登录
		 * 1. 消息转换为po
		 * 2. 校验用户是否存在
		 * 3. 校验token有效性
		 */
		//1. 消息转换为po
		LoginPo loginPo = JacksonUtil.dateInstance().json2pojo(body, LoginPo.class);
		String phone = loginPo.getPhone();
		String token = loginPo.getToken();
		//2. 校验用户是否存在
		Example example = new Example(User.class);
		Criteria usrCriteria = example.createCriteria();
		usrCriteria.andEqualTo("phone", phone);
		List<User> usrList = UserMapper.selectByExample(example);
		if(CollectionUtils.isEmpty(usrList)){
			LOGGER.error("server-LoginInHandler-登录处理器=channelRead: 账号无法关联到用户, 请确定账号存在, 通道关闭! 来源client("+remoteAddress.toString()+"), 内容("+nettyChatMsg.toString()+")");
			destoryCtx(ctx);
			return;
		}
		User currentUser = usrList.get(0);
		String uuid = currentUser.getUuid();
		//3. 校验token有效性
		boolean verify = JWTUtil.verify(token, uuid);
		if(verify != true){
			LOGGER.error("server-LoginInHandler-登录处理器=channelRead: token无效, 通道关闭! 来源client("+remoteAddress.toString()+"), 内容("+nettyChatMsg.toString()+")");
			destoryCtx(ctx);
			return;
		}
		
		/**
		 * 登录成功
		 * 1. channel通道设置已登录attr + 当前通道的uuid
		 * 2. 将channel通道信息保存
		 * 3. pipeline移除当前Handler
		 */
		//1. channel通道设置已登录attr + 当前通道的uuid
		AttributeKey<Boolean> isLoginKey = AttributeKey.valueOf("isLogin");
		Attribute<Boolean> isLoginAttr = channel.attr(isLoginKey);
		isLoginAttr.set(true);
		AttributeKey<String> uuidKey = AttributeKey.valueOf("uuid");
		Attribute<String> uuidAttr = channel.attr(uuidKey);
		uuidAttr.set(uuid);
		//2. 将channel通道信息保存
		channelCache.set(uuid, channel);
		//3. pipeline移除当前Handler
		ChannelPipeline pipeline = channel.pipeline();
		pipeline.remove(this);
		
		LOGGER.info("server-LoginInHandler-登录处理器=channelRead: 登录成功! 来源client("+remoteAddress.toString()+"), 内容("+nettyChatMsg.toString()+")");
		
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
	
	
}
