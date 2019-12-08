package zqit.chat.echoServer.pulgins.netty.channelMap;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import io.netty.channel.Channel;

/**
 * 用于保存 channel通道信息
 * 
 * @author mac
 *
 */
@Component
public class ChannelConcrtHshMap implements ChannelCache<String, Channel> {

	private ConcurrentHashMap<String, Channel> channelCacheMap = new ConcurrentHashMap<String, Channel>();
	
	
	@Override
	public Channel get(String key) {
		Channel channel = channelCacheMap.get(key);
		return channel;
	}

	@Override
	public Channel set(String key, Channel channel) {
		Channel put = channelCacheMap.put(key, channel);
		return put;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
