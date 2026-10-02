package com.zhenshu.parent.common.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zhenshu.parent.common.constant.Constants;
import com.zhenshu.parent.common.constant.enums.ErrorEnums;
import com.zhenshu.parent.common.constant.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt 工具类，用于生成及解析JWT
 */
public class JwtUtils {

    private static final String PHONE = "phone";

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    /**
     * 生成 JWT
     */
    public static String createToken(String phone, String password) {
        String token = "";
        try {
            //这里将useName 和 password 存入了Token，在下面的解析中，也会有解析的方法可以获取到Token里面的数据
            //Token过期的时间
            //过期时间
            Date date = new Date(System.currentTimeMillis() + Constants.JWT_EXP_TIME);
            //秘钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(Constants.TOKEN_SECRET);
            //设置头部信息,类型以及签名所用的算法
            Map<String, Object> header = new HashMap<>();
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            //携带username，password信息，存入token，生成签名
            token = JWT.create()
                    .withHeader(header)
                    //存储自己想要留给前端的内容
                    .withClaim("phone", phone)
                    .withClaim("password", password).withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return token;
    }


    /**
     * 验证token
     */
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(Constants.TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            if (StringUtils.isEmpty(jwt.getToken())) {
                throw new ServiceException(ErrorEnums.UNAUTHORIZED.getMsg(), ErrorEnums.UNAUTHORIZED.getCode());
            }
            return true;
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            throw new ServiceException(ErrorEnums.UNAUTHORIZED.getMsg(), ErrorEnums.UNAUTHORIZED.getCode());
        }
    }

    /**
     * 获取token中信息 phone
     */
    public static String getPhone(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(PHONE).asString();
        } catch (JWTDecodeException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 是否需要重新生成token （为了延续token时长）
     *
     * @param decodedJWT
     * @return
     */
    public static boolean needCreate(DecodedJWT decodedJWT) {
        Date timeoutDate = decodedJWT.getExpiresAt();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, Constants.ADD_TIME);
        if (timeoutDate.before(calendar.getTime())) {
            return true;
        }
        return false;
    }

    //测试
    public static void main(String[] args) {
        String username = "13973250691";
        String password = "admin123";
        String token = createToken(username, password);
        System.out.println(token);
        boolean b = verify("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwYXNzd29yZCI6ImFkbWluMTIzIiwicGhvbmUiOiIxMzk3MzI1MDY5MSIsImV4cCI6MTYwODUyMTIyOX0.eTWA2sF7eHDdRkHU9pa2OGbx41xfiXOwI2VDKKogv0I");
        System.out.println(b);
    }

    /**
     * 根据要过期的token获取新token
     *
     * @param jwtToken 上次的JWT经过解析后的对象，其实就是把JWT的Base64解码了
     * @return
     */
    public static String getRefreshToken(DecodedJWT jwtToken, String phone, String password) {
        Instant now = Instant.now();
        Instant exp = jwtToken.getExpiresAt().toInstant();
        //如果当前时间减去JWT过期的时间，大于允许过期时间，说明不允许重新申请了，就得重新登录了，此时返回null，否则就是可以重新申请，开始在后台重新生成新的JWT。
        if ((now.getEpochSecond() - exp.getEpochSecond()) > Constants.ADD_TIME) {
            return null;
        }
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256(Constants.TOKEN_SECRET);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        //在原有的JWT的过期时间的基础上，加上这次的有效时间，得到新的JWT的过期时间
        Instant newExp = exp.plusSeconds(Constants.ADD_TIME);
        //创建JWT
        //设置头部信息,类型以及签名所用的算法
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");

        String token = JWT.create()
                .withHeader(header)
                //存储自己想要留给前端的内容
                .withClaim("phone", phone)
                .withClaim("password", password)
                .sign(algorithm);
        LOGGER.trace("create refresh token [" + token + "]; iat: " + Date.from(exp) + " exp: " + Date.from(newExp));
        return token;
    }

    public static DecodedJWT getTokenInfo(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(Constants.TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            if (StringUtils.isEmpty(jwt.getToken())) {
                throw new ServiceException(ErrorEnums.UNAUTHORIZED.getMsg(), ErrorEnums.UNAUTHORIZED.getCode());
            }
            return jwt;
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            throw new ServiceException(ErrorEnums.UNAUTHORIZED.getMsg(), ErrorEnums.UNAUTHORIZED.getCode());
        }
    }
}
