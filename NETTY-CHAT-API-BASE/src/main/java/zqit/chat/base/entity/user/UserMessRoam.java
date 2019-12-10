package zqit.chat.base.entity.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户消息漫游表
 * @author mac
 *
 */
@Table(name = "user_mess_roam")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserMessRoam {

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
	 * 消息保存时长
	 */
	@Column(name = "keep_day")
	private Integer keepDay;
	/**
	 * 
	 */
	@Column(name = "update_time")
	private Date updateTime;
	/**
	 * 
	 */
	@Column(name = "create_time")
	private Date create_time;
}
