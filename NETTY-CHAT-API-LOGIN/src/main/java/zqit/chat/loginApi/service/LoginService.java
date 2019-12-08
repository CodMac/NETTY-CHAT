package zqit.chat.loginApi.service;

import java.util.Map;

import zqit.chat.base.po.RespBean;
import zqit.chat.loginApi.query.LoginQuery;

public interface LoginService {

	RespBean<Map<String, Object>> loginIn(LoginQuery query);

}
