package zqit.chat.echoClient.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;

import zqit.chat.base.pulgins.jwt.JWTUtil;
import zqit.chat.echoClient.pulgins.netty.EchoClient;
import zqit.chat.echoClient.pulgins.netty.po.ChatMsgPo;
import zqit.chat.echoClient.pulgins.netty.po.LoginPo;
import zqit.chat.echoClient.pulgins.netty.protocol.nettyChatProtocol.NettyChatMsgModule;
import zqit.chat.echoClient.pulgins.netty.protocol.nettyChatProtocol.NettyChatPtcol;
import zqit.chat.echoClient.util.JacksonUtil;

@Controller
@RequestMapping("/clientTestB")
public class TestBController {
	
	@Autowired
	EchoClient echoClient;

	@ResponseBody
	@PostMapping("/sendMsg")
	public String sendMsg(String msgBody) throws JsonProcessingException{
		ChatMsgPo chatMsgPo = new ChatMsgPo();
    	chatMsgPo.setFromUuid("c1f7aa7816fd11eaaa08fa3c3d7fc663");
    	chatMsgPo.setToUuid("b2607f4016fd11eaaa08fa3c3d7fc663");
    	chatMsgPo.setConversationType(1);//会话类型 1-私聊 2-群聊
    	chatMsgPo.setMessType(1);//消息类型 1-文本 2-图片 3-语音 4-视频 5-文件 6-链接 7-表情包
    	chatMsgPo.setMsgUuid(UUID.randomUUID().toString().replaceAll("-", ""));
    	chatMsgPo.setContent(msgBody);
    	
    	NettyChatPtcol msg1 = new NettyChatPtcol();
    	msg1.setMsgModule(NettyChatMsgModule.chat.getModuleIndex());
    	msg1.setBody(JacksonUtil.dateInstance().pojo2json(chatMsgPo));
		echoClient.channel.writeAndFlush(msg1);
		
		return msgBody;
	}
	
	
	@ResponseBody
	@PostMapping("/loginIn")
	public String loginInB() throws JsonProcessingException{
		//发送登录请求
        NettyChatPtcol nettyChatMsg = new NettyChatPtcol();
        nettyChatMsg.setMsgModule(NettyChatMsgModule.login.getModuleIndex());
        LoginPo loginPo = new LoginPo();
        loginPo.setPhone("15627595112");
        String token = JWTUtil.sign("c1f7aa7816fd11eaaa08fa3c3d7fc663");
        loginPo.setToken(token);
        String body = JacksonUtil.defaultInstance().pojo2json(loginPo);
        nettyChatMsg.setBody(body);
        echoClient.channel.writeAndFlush(nettyChatMsg);
		
		return token;
	}
	
	
}
