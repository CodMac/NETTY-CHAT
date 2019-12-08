package zqit.chat.echoServer.pulgins.netty.protocol.nettyChatProtocol;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 自定义 NETTY-CHAT 协议: 消息类型枚举
 * 
 * @author mac
 *
 */
@NoArgsConstructor
@Getter
@ToString
public enum NettyChatMsgModule {
	//1
	login((byte)0b0000001, "登录"), 	
	//2
	logout((byte)0b00000010, "登出"), 
	//3
	chat((byte)0b00000011, "聊天消息"),
	//4
	chatRead((byte)0b00000100, "消息已读通知"),
	//5
	heart((byte)0b0000101, "心跳");

	//
	private byte moduleIndex;
	private String moduleName;

	NettyChatMsgModule(byte moduleIndex, String moduleName) {
		this.moduleIndex = moduleIndex;
		this.moduleName = moduleName;
	}
}
