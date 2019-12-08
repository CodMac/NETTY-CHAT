package zqit.chat.echoClient.pulgins.netty.protocol.nettyChatProtocol.decode;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import zqit.chat.echoClient.pulgins.netty.protocol.nettyChatProtocol.NettyChatPtcol;

/**
 * 自定义 NETTY-CHAT 协议 解码器
 * 
 * @author mac
 *
 */
@Component
public class NettyChatDecoder extends ByteToMessageDecoder {

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	// 协议版本号 - 1
	public final static byte ptcolversion = 0b00000001;
	// 魔数
	public final static byte[] magicCode = {'M','A','C','!'};

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// 可读位 = 魔数+协议版本号+msgModule+消息体长+消息体 = 4+1+1+4+n
		if (in.readableBytes() < 10) {
			LOGGER.info("NETTY-CHAT协议解码器 - 可读位数小于10[魔数+协议版本号+msgModule+消息体长+消息体], ByteBuf继续缓冲等待...");
			return;
		}

		// 魔数判断
		byte[] magicCodeByte = new byte[4];
		in.readBytes(magicCodeByte);
		if (Arrays.equals(magicCode, magicCodeByte) == false) {
			LOGGER.info("NETTY-CHAT协议解码器 - ByteBuf流错误, 魔数无法匹配...");
			// 非魔术,则丢弃
			in.release();
			ctx.close();
			return;
		}

		//版本号
		byte version = in.readByte();
		if(ptcolversion != version){
			LOGGER.info("NETTY-CHAT协议解码器 - ByteBuf流错误, 版本号无法匹配..., version: " + version);
			in.release();
			ctx.close();
		}
		
		//msgModule
		byte msgModule = in.readByte();
		
		//消息体长
		int bodyLen = in.readInt();
		if (bodyLen <= 0) {
			LOGGER.info("NETTY-CHAT协议解码器 - 消息体长<=0");
            ctx.close();
            return;
        }
		
		//消息体
		if (in.readableBytes() < bodyLen) {
			LOGGER.info("NETTY-CHAT协议解码器 - 可读消息体长度<消息体长, ByteBuf继续缓冲等待...");
            in.resetReaderIndex();
            return;
        }
		byte[] body = new byte[bodyLen];
        in.readBytes(body);
        String bodyJson = new String(body);
        
        //消息
        NettyChatPtcol nettyChatMsg = new NettyChatPtcol();
        nettyChatMsg.setMsgModule(msgModule);
        nettyChatMsg.setBody(bodyJson);
        
        out.add(nettyChatMsg);
        LOGGER.info("NETTY-CHAT协议解码器 - 消息解码完成: " + nettyChatMsg.toString());
	}

}
