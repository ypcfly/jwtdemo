package com.ypc.jwtdemo.service;

import com.ypc.jwtdemo.entity.User;
import com.ypc.jwtdemo.mapper.UserMapper;
import com.ypc.jwtdemo.utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public Map<String,Object> verifyUser(String userName, String password) {
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("success",false);
		if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)){
			resultMap.put("message","用户名或密码不能为空");
			return resultMap;
		}
		// 省略用户名和密码校验

		User user = userMapper.selectByUserName(userName,password);
		if (null == user){
			resultMap.put("message","用户不存在");
			return resultMap;
		}

		String role = "";
		if (StringUtils.isNotBlank(user.getRole()))
			role = user.getRole();
		// 设置过期时间
		String token = JwtUtil.createJWT(role,30 * 60 * 1000);

		resultMap.put("success",true);
		resultMap.put("token",token);
		resultMap.put("message","登录成功");

		return resultMap;
	}

	@Override
	public boolean verifyToken(String token) {
		boolean flag = false;
		Map<String,Object> resultMap = JwtUtil.verifyToken(token);
		String role = (String) resultMap.get("role");
		Date expires = (Date) resultMap.get("exp");
		if (StringUtils.isNotBlank(role) && StringUtils.equals("manager",role)){
			flag = true;
		}

		return flag;
	}

	@Override
	public User selectUserById(Integer id) {
		return userMapper.selectUserById(id);
	}
}
