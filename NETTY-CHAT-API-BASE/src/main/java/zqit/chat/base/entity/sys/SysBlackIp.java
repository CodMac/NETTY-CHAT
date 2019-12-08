package zqit.chat.base.entity.sys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "sys_black_ip")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SysBlackIp {

	/**
	 * 
	 */
	@Column(name = "ip")
	private Date ip;
	
}
