package zqit.chat.echoClient.pulgins.netty;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import zqit.chat.echoClient.pulgins.netty.handler.EchoClientHandler;
import zqit.chat.echoClient.pulgins.netty.protocol.nettyChatProtocol.decode.NettyChatDecoder;
import zqit.chat.echoClient.pulgins.netty.protocol.nettyChatProtocol.encode.NettyChatEncoder;

@Component
public class EchoClient {
	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Value("${netty.port}")
	private int port;
	@Value("${netty.url}")
	private String url;
	
	//通道信息方便用于restful调用
	public Channel channel;
	
	/**
	 * Netty用于接收客户端请求的线程池职责如下。 
	 * （1）接收客户端TCP连接，初始化Channel参数；
	 * （2）将链路状态变更事件通知给ChannelPipeline
	 */
	EventLoopGroup group = new NioEventLoopGroup();

	public ChannelFuture start() throws InterruptedException {
		ChannelFuture f = null;
		Bootstrap b = new Bootstrap();
		b.group(group)
			.channel(NioSocketChannel.class)
			.remoteAddress(new InetSocketAddress(url, port))
			.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel socketChannel) throws Exception {
						
						socketChannel.pipeline().addLast(new NettyChatDecoder());
						socketChannel.pipeline().addLast(new NettyChatEncoder());
						
						socketChannel.pipeline().addLast(new EchoClientHandler());
					}
				});
		// 绑定端口
		f = b.connect().sync();
		
		channel = f.channel();

		LOGGER.info("----------------------------> netty客户端-监听服务端口: " + url + ":" + port + " 成功启动 <----------------------------");

		return f;
	}

	public void destory(ChannelFuture f) {
		try {
			f.channel().closeFuture().sync();
			group.shutdownGracefully().sync();
			LOGGER.info("----------------------------> netty客户端-监听服务端口: " + url + ":" + port + " 销毁启动 <----------------------------");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
