package com.ypc.jwtdemo.controller;

import com.ypc.jwtdemo.entity.User;
import com.ypc.jwtdemo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;

	@RequestMapping("/")
	public String index(){
		System.out.println(">>>> index controller <<<<");
		return "login";
	}

	@PostMapping("/login")
	@ResponseBody
	public Map<String,Object> login(String userName, String password,Model model) {
		//  根据用户名和密码校验用户身份
		Map<String,Object> resultMap = userService.verifyUser(userName,password);

		String token = (String) resultMap.get("token");
		System.out.println(token);
		model.addAttribute(token);
		return resultMap;
	}

	@GetMapping("/detail")
	public String userDetail(Model model,HttpServletRequest request){

		Integer id = Integer.parseInt(request.getParameter("id"));
		User user = userService.selectUserById(id);
		model.addAttribute(user);
		return "/user/user";


	}

	@PostMapping("/verify")
	@ResponseBody
	public String verifyToken(HttpServletRequest request,String token){
		Enumeration<String> enumeration = request.getHeaders("token");
		System.out.println(enumeration);
		String result = "权限不足";
		boolean flag = userService.verifyToken(token);
		if (flag){
			result = "用户权限为：manager";
		}

		return result;
	}

}
