package zqit.chat.echoClient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;

import zqit.chat.base.pulgins.jwt.JWTUtil;
import zqit.chat.echoClient.pulgins.netty.EchoClient;
import zqit.chat.echoClient.pulgins.netty.po.LoginPo;
import zqit.chat.echoClient.pulgins.netty.protocol.nettyChatProtocol.NettyChatMsgModule;
import zqit.chat.echoClient.pulgins.netty.protocol.nettyChatProtocol.NettyChatPtcol;
import zqit.chat.echoClient.util.JacksonUtil;

@Controller
@RequestMapping("/clientTest")
public class TestController {
	
	@Autowired
	EchoClient echoClient;

	@ResponseBody
	@PostMapping("/sendMsg")
	public String sendMsg(String msgBody){
		NettyChatPtcol msg1 = new NettyChatPtcol();
    	msg1.setMsgModule(NettyChatMsgModule.chat.getModuleIndex());
    	msg1.setBody(msgBody);
		echoClient.channel.writeAndFlush(msg1);
		
		return msgBody;
	}
	
	@ResponseBody
	@PostMapping("/loginIn")
	public String loginIn() throws JsonProcessingException{
		//发送登录请求
        NettyChatPtcol nettyChatMsg = new NettyChatPtcol();
        nettyChatMsg.setMsgModule(NettyChatMsgModule.login.getModuleIndex());
        LoginPo loginPo = new LoginPo();
        loginPo.setPhone("13129307306");
        String token = JWTUtil.sign("b2607f4016fd11eaaa08fa3c3d7fc663");
        loginPo.setToken(token);
        String body = JacksonUtil.defaultInstance().pojo2json(loginPo);
        nettyChatMsg.setBody(body);
        echoClient.channel.writeAndFlush(nettyChatMsg);
		
		return token;
	}
	
	
}
