package zqit.chat.base.entity.user;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户-好友表
 * @author mac
 *
 */
@Table(name = "user_friend")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserFriend {

	/**
	 * 用户id
	 */
	@Column(name = "user_uuid")
	private String userUuid;
	/**
	 * 好友uuid
	 */
	@Column(name = "friend_uuid")
	private String friendUuid;
	/**
	 * 好友分组uuid null/0-未分组
	 */
	@Column(name = "friend_group_uuid")
	private String friendGroupUuid;
	/**
	 * 备注名 15字限制
	 */
	@Column(name = "remark_name")
	private String remarkName;
	/**
	 * 个人备注
	 */
	@Column(name = "remark")
	private String remark;
	/**
	 * 好友状态 1-正常 2-黑名单 3-待审核
	 */
	@Column(name = "status")
	private Integer status;
}

