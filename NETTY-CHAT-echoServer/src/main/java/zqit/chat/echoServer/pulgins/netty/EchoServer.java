package zqit.chat.echoServer.pulgins.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import zqit.chat.echoServer.pulgins.netty.handler.ChatMsgInHandler;
import zqit.chat.echoServer.pulgins.netty.handler.CheckpointInHandler;
import zqit.chat.echoServer.pulgins.netty.handler.LoginInHandler;
import zqit.chat.echoServer.pulgins.netty.protocol.nettyChatProtocol.decode.NettyChatDecoder;
import zqit.chat.echoServer.pulgins.netty.protocol.nettyChatProtocol.encode.NettyChatEncoder;

/**
 * 
 * @author mac
 *
 */
@Component
public class EchoServer {
	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Value("${netty.port}")
	private int port;
	@Value("${netty.url}")
	private String url;
	
	@Autowired
	LoginInHandler loginInHandler;
	@Autowired
	ChatMsgInHandler chatMsgInHandler;
	
	/**
	 * NioEventLoop并不是一个纯粹的I/O线程，它除了负责I/O的读写之外 创建了两个NioEventLoopGroup，
	 * 它们实际是两个独立的Reactor线程池。 
	 * boss 用于接收客户端的TCP连接，
	 * worker 用于处理I/O相关的读写操作，或者执行系统Task、定时任务Task等。
	 */
	NioEventLoopGroup boss = new NioEventLoopGroup();
	NioEventLoopGroup worker = new NioEventLoopGroup();
	
	/**
	 * 启动netty服务
     * @param args
     */
	public ChannelFuture start() throws InterruptedException{
		// 1. new ServerBootstrap 对象
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		// 2. 绑定 boos线程组 和 worker线程组
		serverBootstrap.group(boss, worker);
		// 3. 设置channel类型为NIO类型
		serverBootstrap.channel(NioServerSocketChannel.class);
		// 4. 添加ChannelHandler
		serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel socketChannel) throws Exception {
				// 当一个新的连接 被接受时，一个新的子 Channel 将会被创建，而 ChannelInitializer 将会把一个你的 ChannelHandler 的实例添加到该 Channel 的 ChannelPipeline 中。这个 ChannelHandler 将会收到有关入站消息的通知
				
				//attr
				AttributeKey<Boolean> isLoginKey = AttributeKey.valueOf("isLogin");
				Attribute<Boolean> isLoginAttr = socketChannel.attr(isLoginKey);
				isLoginAttr.set(false);
				
				//编解码
				socketChannel.pipeline().addLast(new NettyChatDecoder());
				socketChannel.pipeline().addLast(new NettyChatEncoder());
				
				//In-Handler
				CheckpointInHandler checkpointInHandler = new CheckpointInHandler();
				socketChannel.pipeline().addLast(checkpointInHandler);
				socketChannel.pipeline().addLast(loginInHandler);
				socketChannel.pipeline().addLast(chatMsgInHandler);
				
			}
		});
		// 5. bind mainReactor的监听端口
		ChannelFuture future = serverBootstrap.bind(port).sync();
		
		// 6. 添加成功监听
		future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				LOGGER.info("----------------------------> netty服务-监听端口: " + port + " 成功启动 <----------------------------");
			}
		});

		return future;
	}
	
	/**
	 * 销毁netty服务
	 */
	public void destory(ChannelFuture future){
		Channel channel = future.channel();
		if(channel != null) { 
			channel.close();
		}
		boss.shutdownGracefully();
		worker.shutdownGracefully();
		LOGGER.info("----------------------------> netty服务-监听端口: " + port + " 已停用 <----------------------------");
	}
}
