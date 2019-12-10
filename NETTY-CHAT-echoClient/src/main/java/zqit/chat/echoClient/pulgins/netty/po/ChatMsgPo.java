package zqit.chat.echoClient.pulgins.netty.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 聊天消息po
 * @author mac
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ChatMsgPo {
	
	/**
	 * 消息uuid
	 */
	private String msgUuid;
	/**
	 * 来源uuid
	 */
	private String fromUuid;
	/**
	 * 目标uuid
	 */
	private String toUuid;
	/**
	 * 会话类型 1-私聊 2-群聊
	 */
	private Integer conversationType;
	/**
	 * 消息类型 1-文本 2-图片 3-语音 4-视频 5-文件 6-链接 7-表情包
	 */
	private Integer messType;
	/**
	 * 文本内容
	 */
	private String content;
	/**
	 * 图片/视频/音频/文件url
	 */
	private String url;
	/**
	 * 表情包uuid
	 */
	private String faceUuid;
}
