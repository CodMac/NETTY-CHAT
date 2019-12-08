package zqit.chat.loginApi.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import zqit.chat.base.po.RespBean;
import zqit.chat.loginApi.query.LoginQuery;
import zqit.chat.loginApi.service.LoginService;

/**
 * 登录
 * @author mac
 *
 */
@RequestMapping("/login")
@Controller
public class LoginController {
	
	@Autowired
	LoginService loginService;
	
	/**
	 * 登录接口
	 * @return
	 */
	@PostMapping("loginIn")
	@ResponseBody
	public RespBean<Map<String, Object>> loginIn(@RequestBody LoginQuery query){
		RespBean<Map<String, Object>> result = new RespBean<Map<String, Object>>();
		
		String phone = query.getPhone();
		String password = query.getPassword();
		if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)){
			result.setSuccess(false);
			result.setCode(501);
			result.setMsg("参数不合规");
			return result;
		}
		
		result = loginService.loginIn(query);
		
		return result;
		
	}

}
