package zqit.chat.echoServer.pulgins.netty.channelMap;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
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
		return channelCacheMap.size();
	}

	@Override
	public boolean isOnline(String key) {
		Channel channel = channelCacheMap.get(key);
		if(channel == null){
			return false;
		}
		return true;
	}

	@Override
	public boolean remove(String key) {
		if(!StringUtils.isEmpty(key)){
			channelCacheMap.remove(key);
		}
		return true;
	}

	
}
