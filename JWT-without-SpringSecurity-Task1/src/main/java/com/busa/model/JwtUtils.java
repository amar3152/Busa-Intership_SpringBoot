package com.busa.model;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {

    private static String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";

    private  static long expireDuration = 60 * 60;
    public String genratetoken(UserDetails userDetails){

        long millTime = System.currentTimeMillis();
        long expiryTime  = millTime + expireDuration * 1000;
        Date issueDate = new Date(millTime);
        Date expirtAT = new Date(expiryTime);
        Key hKey = new SecretKeySpec(Base64.getDecoder().decode(secret),SignatureAlgorithm.HS256.getJcaName());
       // byte[] keyBytes = Decoders.BASE64.decode(secret);
       // byte[] decode = Base64.getEncoder().encode(secret.getBytes());


        //claims
        Claims claims = Jwts.claims()
                .setIssuer(Integer.toString(userDetails.getId()))
                .setIssuedAt(issueDate)
                .setExpiration(expirtAT);

        //Optional

        claims.put("id", userDetails.getId());
        claims.put("name",userDetails.getUsername());
        claims.put("mobno",userDetails.getMobno());

        //generat jwt using claims
        JwtBuilder builder = Jwts.builder();
        builder.setClaims(claims) ;
        builder.signWith(hKey);
        return builder
                .compact();

    }




}
