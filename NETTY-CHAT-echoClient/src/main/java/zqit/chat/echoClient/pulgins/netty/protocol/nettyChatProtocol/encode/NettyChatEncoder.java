package zqit.chat.echoClient.pulgins.netty.protocol.nettyChatProtocol.encode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import zqit.chat.echoClient.pulgins.netty.protocol.nettyChatProtocol.NettyChatPtcol;

/**
 * 自定义 NETTY-CHAT 协议 编码器
 * @author mac
 *
 */
@Component
public class NettyChatEncoder extends MessageToByteEncoder<NettyChatPtcol> {
	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	//协议版本号 - 1
	public final static byte version = 0b00000001;
	//魔数
	public final static byte[] magicCode = {'M','A','C','!'};

	@Override
	protected void encode(ChannelHandlerContext ctx, NettyChatPtcol msg, ByteBuf out) throws Exception {
		if(msg == null){
			LOGGER.info("NETTY-CHAT协议编码器 - msg为空,无需编码 ");
			return;
		}
		
		byte msgModule = msg.getMsgModule();
		String body = msg.getBody();
		byte[] bodyBytes = body.getBytes();
		int bodyLength = bodyBytes.length;
		
		/**
		 * 自定义NETTY-CHAT协议 - 编码
		 * 1. 添加魔数					- 4位 : 'M','A','C','!'
		 * 2. 添加协议版本号				- 1位 : 0000000001B
		 * 3. 添加消息类型: msgModule		- 1位 
		 * 4. 添加消息体长					- 4位
		 * 5. 添加消息体(json)				- n位
		 */
		//1. 添加魔数
		out.writeBytes(magicCode);
		//2. 添加协议版本号
		out.writeByte(version);
		//3. 添加消息类型: msgModule
		out.writeByte(msgModule);
		//4. 添加消息体长
		out.writeInt(bodyLength);
		//5. 添加消息体(json)
		out.writeBytes(bodyBytes);
		
	}
	
}
