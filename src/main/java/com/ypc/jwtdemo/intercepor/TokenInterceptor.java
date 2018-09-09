package com.ypc.jwtdemo.intercepor;

import com.ypc.jwtdemo.utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class TokenInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!(handler instanceof HandlerMethod)){
			return true;
		}
		String token = request.getParameter("token");
		Map<String,Object> resultMap = JwtUtil.verifyToken(token);
		String role = (String) resultMap.get("role");

		if (StringUtils.isNotBlank(role) && StringUtils.equals(role,"manager")){
			return true;
		}

		return false;
	}

//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//	}
//
//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//
//	}
}
