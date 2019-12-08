package zqit.chat.base.pulgins.jwt;

import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTUtil {

	private static final String JWT_AUTHC = "jwtAuthc-netty-chat";
	private static final long EXPIRE_TIME = 60000;//1min
	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * 校验token是否正确
	 *
	 * @param token
	 * @param phone
	 * @return
	 */
	public static boolean verify(String token, String uuid) {
		try {
			Map<String, Claim> claimMap = JWT.decode(token).getClaims();
			if (!claimMap.containsKey(JWT_AUTHC)) {
				System.out.println("JWTUtil - 权限认证 - verify : 失败 - token不包含JWT_AUTHC");
				return false;
			}
			
			//使用uuid作为加密串
			Algorithm algorithm = Algorithm.HMAC256(uuid);

			JWTVerifier verifier = JWT.require(algorithm).withClaim(JWT_AUTHC, claimMap.get(JWT_AUTHC).asString())
					.build();
			// 验签
			verifier.verify(token);
			System.out.println("JWTUtil - 权限认证 - verify : 成功");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获取token登录信息
	 *
	 * @param token
	 * @return
	 */
	public static JwtAuthc getJwtAuthc(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			String jwtAuthc = jwt.getClaim(JWT_AUTHC).asString();
			return mapper.readValue(jwtAuthc, JwtAuthc.class);
		} catch (Exception e) {
			System.out.println("JWTUtil - 权限认证 - getJwtAuthc : 失败 - {token: " + token + "}");
			return null;
		}
	}

	/**
	 * 生成签名token
	 *
	 * @return
	 */
	public static String sign(String uuid) {
		try {
			JwtAuthc jwtAuthc = new JwtAuthc();
			jwtAuthc.setUuid(uuid);
			
			// 指定过期时间
			Date expiresAt = new Date(System.currentTimeMillis()+EXPIRE_TIME);
			
			//使用phone作为加密串
			Algorithm algorithm = Algorithm.HMAC256(uuid);
			
			//生成token
			String toekn = JWT.create()
					.withClaim(JWT_AUTHC, mapper.writeValueAsString(jwtAuthc))
					.withExpiresAt(expiresAt)
					.sign(algorithm);
			System.out.println("JWTUtil - 权限认证 - sign : 成功 - {uuid: " + uuid + "}");
			
			return toekn;
		} catch (Exception e) {
			System.out.println("JWTUtil - 权限认证 - sign : 失败 - {uuid: " + uuid + "}");
			return null;
		}
	}

}