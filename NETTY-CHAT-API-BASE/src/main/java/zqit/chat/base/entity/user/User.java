package zqit.chat.base.entity.user;

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

/**
 * 用户表
 * 
 * @author mac
 *
 */
@Table(name = "`user`")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String uuid;
	/**
	 * 名称 20字限制
	 */
	@Column(name = "name")
	private String name;
	/**
	 * 电话
	 */
	@Column(name = "phone")
	private String phone;
	/**
	 * 头像url
	 */
	@Column(name = "head_url")
	private String headUrl;
	/**
	 * 生日 格式:yyyy-MM-dd
	 */
	@Column(name = "birthday")
	private String birthday;
	/**
	 * 性别 1-男 2-女 3-未知
	 */
	@Column(name = "sex")
	private String sex;
	/**
	 * 国家
	 */
	@Column(name = "country")
	private String country;
	/**
	 * 省
	 */
	@Column(name = "province")
	private String province;
	/**
	 * 城市
	 */
	@Column(name = "city")
	private String city;
	/**
	 * 心情 30字限制
	 */
	@Column(name = "mood")
	private String mood;
	/**
	 * 密码 
	 */
	@Column(name = "pwd")
	private String pwd;
	/**
	 * 盐-100内的随机整数
	 */
	@Column(name = "salt")
	private Integer salt;
	/**
	 * 用户状态 1-可用 2-停用
	 */
	@Column(name = "status")
	private Integer status;
	/**
	 * 登录状态 1-在线 2-离线 3-忙碌 4-离开 5-隐身
	 */
	@Column(name = "online_status")
	private String onlineStatus;
	/**
	 * 注册时间
	 */
	@Column(name = "register_time")
	private String registerTime;
	
}
