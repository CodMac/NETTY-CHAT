package zqit.chat.echoClient.pulgins.netty.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 登录请求消息po
 * @author mac
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginPo {
	private String phone;
	private String token;
}
