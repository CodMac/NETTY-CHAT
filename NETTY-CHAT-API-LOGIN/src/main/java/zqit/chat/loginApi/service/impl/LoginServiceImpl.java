package zqit.chat.loginApi.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zqit.chat.base.entity.user.User;
import zqit.chat.base.mapper.user.UserMapper;
import zqit.chat.base.po.RespBean;
import zqit.chat.base.pulgins.jwt.JWTUtil;
import zqit.chat.base.pulgins.md5.MD5Util;
import zqit.chat.loginApi.query.LoginQuery;
import zqit.chat.loginApi.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	UserMapper userMapper;

	/**
	 * 登录校验, 成功返回签名token
	 */
	@Override
	public RespBean<Map<String, Object>> loginIn(LoginQuery query) {
		RespBean<Map<String, Object>> result = new RespBean<Map<String, Object>>();
		
		String phone = query.getPhone();
		String password = query.getPassword();
		
		/**
		 * 校验账号
		 * 1. 校验用户是否存在
		 * 2. 校验用户+密码是否正确
		 * 3. 校验用户状态
		 */
		//1. 校验用户是否存在
		User usrQuery = new User();
		usrQuery.setPhone(phone);
		User one = userMapper.selectOne(usrQuery);
		if(one == null){
			result.setSuccess(false);
			result.setCode(201);
			result.setMsg("用户不存在");
			return result;
		}
		//2. 校验用户+密码是否正确
		String pwd = one.getPwd();
		String encodePwd = MD5Util.MD5EncodeUtf8(password, one.getSalt() + "");
		if(!pwd.equals(encodePwd)){
			result.setSuccess(false);
			result.setCode(202);
			result.setMsg("密码不正确");
			return result;
		}
		//3. 校验用户状态 
		Integer status = one.getStatus();//用户状态 1-可用 2-停用
		if(new Integer(2).equals(status)){
			result.setSuccess(false);
			result.setCode(203);
			result.setMsg("用户已停用");
			return result;
		}
		
		/**
		 * 生成签名token
		 */
		String uuid = one.getUuid();
		String signToken = JWTUtil.sign(uuid);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("signToken", signToken);
		data.put("userInfo", one);
		
		result.setSuccess(true);
		result.setData(data);
		return result;
	}

}
