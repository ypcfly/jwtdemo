package com.ypc.jwtdemo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

	private static final String PRIVATE_KEY = "com.connext";

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

	/**
	 * 生成token
	 * @param role
	 * @param maxAge
	 * @return
	 */
	public static String createJWT(String role, int maxAge){
		Map<String,Object> map = new HashMap<>();
		map.put("alg","HS256");
		map.put("typ","JWT");

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MILLISECOND,maxAge);
		Date expires = calendar.getTime();
		String token = JWT.create().withHeader(map)
					.withIssuer("wechat")
					.withSubject("ypc")
					.withExpiresAt(expires)
					.withJWTId("test_jwt")
					.withClaim("role",role)
					.sign(Algorithm.HMAC256(PRIVATE_KEY));
		return token;

	}

	/**
	 * 校验token有效性
	 * @param token
	 * @return
	 */
	public static Map<String,Object> verifyToken(String token){
		Map<String,Object> resultMap = new HashMap<>();
		JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(PRIVATE_KEY)).build();

		String role = "";
		Date expires = null;
		try {
			DecodedJWT decodedJWT = jwtVerifier.verify(token);
			Map<String, Claim> result = decodedJWT.getClaims();
			if (result != null && result.containsKey("role") && result.containsKey("exp")){
				role = result.get("role").asString();
				expires = result.get("exp").asDate();
			}
		} catch (Exception e){
			LOGGER.error(e.getMessage(),e);
		}

		resultMap.put("role",role);
		resultMap.put("exp",expires);

		return resultMap;
	}

}
