package com.ypc.jwtdemo.mapper;

import com.ypc.jwtdemo.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

	User selectByUserName(@Param("username") String userName,@Param("password") String password);

	User selectUserById(Integer id);
}
