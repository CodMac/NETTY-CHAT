package zqit.chat.base.entity.mess;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "mess_record")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MessRecord {

	/**
	 * 消息uuid
	 */
	@Column(name = "msg_uuid")
	private String msgUuid;
	/**
	 * 来源uuid
	 */
	@Column(name = "from_uuid")
	private String fromUuid;
	/**
	 * 目标uuid
	 */
	@Column(name = "to_uuid")
	private String toUuid;
	/**
	 * 会话类型 1-私聊 2-群聊
	 */
	@Column(name = "conversation_type")
	private Integer conversationType;
	/**
	 * 消息类型 1-文本 2-图片 3-语音 4-视频 5-文件 6-链接 7-表情包
	 */
	@Column(name = "mess_type")
	private String messType;
	/**
	 * 文本内容
	 */
	@Column(name = "content")
	private String content;
	/**
	 * 图片/视频/音频/文件url
	 */
	@Column(name = "url")
	private String url;
	/**
	 * 缩略图
	 */
	@Column(name = "thumbnail_url")
	private String thumbnailUrl;
	/**
	 * 表情包uuid
	 */
	@Column(name = "face_uuid")
	private String faceUuid;
	/**
	 * 状态 1-正常 2-已撤回
	 */
	@Column(name = "status")
	private Integer status;
	/**
	 * 
	 */
	@Column(name = "create_time")
	private Date createTime;
	
}
