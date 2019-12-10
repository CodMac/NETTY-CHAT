package zqit.chat.echoServer.pulgins.netty.channelMap;

/**
 * 用于保存 channel通道信息 定义抽象接口 方便以后扩展
 * 
 * @author mac
 *
 */
public interface ChannelCache<K, T> {
	/**
	 * 根据key获得Channel
	 * 
	 * @param key
	 * @return
	 */
	public T get(K key);

	/**
	 * 根据key设置Channel
	 * 
	 * @param key
	 * @param t
	 * @return
	 */
	public T set(K key, T t);

	/**
	 * 返回当前缓存中的Channel
	 * 
	 * @return
	 */
	public int count();

	/**
	 * 根据key判断Channel是否在线
	 */
	public boolean isOnline(K key);
	
}
