package cn.hgf.common;

import cn.hgf.springdemo.common.CommonParam;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: FanYing
 * @Date: 2018-08-13 20:17
 * @Desciption:
 */
public class JwtUtil {

    public static int calendarField = Calendar.DATE;
    //过期天数  10 天
    public static int calendarInterval = 10;

    public static String createToken(String userName,String password,int age){
        Map<String,Object> headerMap = new LinkedHashMap<>();
        headerMap.put("alg",CommonParam.JWT_ALG);
        headerMap.put("typ","JWT");

        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();

        return
        Jwts.builder()
                .setHeaderParams(headerMap)
                .claim("userName",userName)
                .claim("password",password)
                .claim("age",age)
                .setIssuedAt(nowTime.getTime())                                            //设置创建时间
                .setExpiration(expiresDate)                                                 //设置过期时间
                .signWith(SignatureAlgorithm.HS256,createSecretKey()).compact();                   //根据秘钥生成


    }

    public static SecretKeySpec createSecretKey(){

        byte[] encodedKey = Base64.decodeBase64(CommonParam.JWT_SECRETKEY);

        return new SecretKeySpec(encodedKey, SignatureAlgorithm.HS256.getJcaName());
    }

    public static Claims parseToken(String token){
        return
        Jwts.parser()
                .setSigningKey(createSecretKey())
                .parseClaimsJws(token).getBody();

    }

    public static void main(String[] args){

        String jack = null;
        jack = createToken("jack", "123", 12);
        System.out.println(parseToken(jack).get("userName"));
    }
}
