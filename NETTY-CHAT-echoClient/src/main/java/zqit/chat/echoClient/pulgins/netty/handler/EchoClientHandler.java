package zqit.chat.echoClient.pulgins.netty.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import zqit.chat.echoClient.pulgins.netty.protocol.nettyChatProtocol.NettyChatPtcol;

/**
 * 客户端处理类
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<NettyChatPtcol> {

	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	/**
     * 在到服务器的连接已经建立之后将被调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	LOGGER.info("客户端处理类 EchoClientHandler-channelActive - 服务器的连接已经建立");
        
    }

    /**
     * 当从服务器接收到一个消息时被调用
     * @param channelHandlerContext
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, NettyChatPtcol nettyChatMsg) throws Exception {
    	LOGGER.info("客户端处理类 EchoClientHandler-channelRead0 - 收到消息: " + nettyChatMsg.toString());
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	
    }
    
    /**
     * 在处理过程中引发异常时被调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
