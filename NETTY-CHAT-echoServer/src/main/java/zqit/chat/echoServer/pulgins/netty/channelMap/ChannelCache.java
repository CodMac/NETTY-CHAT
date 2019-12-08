package zqit.chat.echoServer.pulgins.netty.channelMap;

/**
 * 用于保存 channel通道信息
 * 定义抽象接口 方便以后扩展
 * @author mac
 *
 */
public interface ChannelCache<K, T> {
	public T get(K key);
	public T set(K key, T t);
	public int count();
}
