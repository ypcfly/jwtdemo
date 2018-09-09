package com.ypc.jwtdemo.intercepor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


	/**
	 * 注册拦截器
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getInterceptor()).addPathPatterns("/**")
				.excludePathPatterns("/user/login")
				.excludePathPatterns("/user/");
	}

	@Bean
	public TokenInterceptor getInterceptor(){
		return new TokenInterceptor();
	}

}
