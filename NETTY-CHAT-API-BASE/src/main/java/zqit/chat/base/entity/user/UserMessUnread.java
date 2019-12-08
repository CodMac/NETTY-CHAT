package zqit.chat.base.entity.user;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "user_mess_unread")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserMessUnread {

	/**
	 * 用户uuid
	 */
	@Column(name = "user_uuid")
	private String userUuid;
	/**
	 * 消息uuid
	 */
	@Column(name = "mess_uuid")
	private String messUuid;
}
