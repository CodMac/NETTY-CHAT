package zqit.chat.base.entity.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "user_friend_group")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserFriendGroup {

	/**
	 * 好友分组uuid
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String friendGroupUuid;
	/**
	 * 好友分组名称
	 */
	@Column(name = "friend_group_name")
	private String friendGroupName;
	/**
	 * 用户uuid
	 */
	@Column(name = "user_uuid")
	private String userUuid;
	/**
	 * 状态 1-可用 2-停用
	 */
	@Column(name = "status")
	private Integer status;
	/**
	 * 
	 */
	@Column(name = "create_time")
	private Date create_time;
	
}
