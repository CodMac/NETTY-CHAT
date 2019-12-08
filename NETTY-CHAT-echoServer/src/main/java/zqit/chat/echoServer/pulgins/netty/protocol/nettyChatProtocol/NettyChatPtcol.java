package zqit.chat.echoServer.pulgins.netty.protocol.nettyChatProtocol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 自定义 NETTY-CHAT 协议
 * 4位		-- 魔数: 用于标注消息开头: 'M','A','C','!'
 * 1位 		-- 协议版本号 0b00000001
 * 1位 		-- msgModule: 用于区分消息类型. 参考枚举 NettyChatMsgModule
 * 4位 		-- 消息体长度
 * others 	-- 消息体(json)
 * @author mac
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NettyChatPtcol {
	
	/**
	 * 消息类型
	 */
	private byte msgModule;
	/**
	 * 消息体
	 */
	private String body;
	
}
