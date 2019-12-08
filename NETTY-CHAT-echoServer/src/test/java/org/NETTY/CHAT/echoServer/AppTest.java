package org.NETTY.CHAT.echoServer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import zqit.chat.base.entity.user.User;
import zqit.chat.base.mapper.user.UserMapper;
import zqit.chat.echoServer.EchoServerApp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { EchoServerApp.class })
public class AppTest{
	
	@Autowired
	UserMapper UserMapper;
	
	@Test 
	public void testApp() {
		Example example = new Example(User.class);
		Criteria usrCriteria = example.createCriteria();
		usrCriteria.andEqualTo("phone", "13129307306");
		UserMapper.selectByExample(example);
	}
}
