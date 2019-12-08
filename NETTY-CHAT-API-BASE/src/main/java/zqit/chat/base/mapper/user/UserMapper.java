package zqit.chat.base.mapper.user;

import zqit.chat.base.entity.user.User;

/**
 * 
 * @author mac
 * 继承 tk.mybatis.mapper.common.Mapper<T> 时 ，自动注册bean到上下文，不需要重复的 @Mapper 扫描注册
 */
public interface UserMapper extends tk.mybatis.mapper.common.Mapper<User>{

}
