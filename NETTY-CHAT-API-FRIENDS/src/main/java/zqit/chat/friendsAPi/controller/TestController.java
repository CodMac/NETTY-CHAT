package zqit.chat.friendsAPi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import zqit.chat.base.entity.user.User;
import zqit.chat.base.mapper.user.UserMapper;
import zqit.chat.base.po.RespBean;

@RestController
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	UserMapper userMapper;

	@GetMapping("/a")
	@ResponseBody
	public RespBean<List<User>> testA(){
		RespBean<List<User>> result = new RespBean<List<User>>();
		
		List<User> all = userMapper.selectAll();
		
		result.setSuccess(true);
		result.setData(all);
		
		return result;
	}
	
	@GetMapping("/b")
	@ResponseBody
	public RespBean<User> testB(){
		RespBean<User> result = new RespBean<User>();
		
		User usrRecd = new User();
		usrRecd.setName("mac");
		usrRecd.setCity("广州");
		usrRecd.setCountry("中国");
		usrRecd.setProvince("广东");
		usrRecd.setStatus(1);
		userMapper.insertSelective(usrRecd);
		
		result.setSuccess(true);
		result.setData(usrRecd);
		
		return result;
	}
	
}
