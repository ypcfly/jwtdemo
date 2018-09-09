package com.ypc.jwtdemo.service;

import com.ypc.jwtdemo.entity.User;

import java.util.Map;

public interface UserService {

	/**
	 *
	 * @param userName
	 * @param password
	 * @return
	 */
	Map<String,Object> verifyUser(String userName, String password);

	boolean verifyToken(String token);

	User selectUserById(Integer id);
}
