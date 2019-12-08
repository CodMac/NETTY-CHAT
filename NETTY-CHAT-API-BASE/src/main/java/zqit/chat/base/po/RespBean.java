package zqit.chat.base.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RespBean<T> {

	private boolean success;
	private String msg;
	private Integer code;
	private T data;
	
}
