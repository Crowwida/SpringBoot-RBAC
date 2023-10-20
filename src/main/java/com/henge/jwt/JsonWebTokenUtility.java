package com.henge.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Slf4j
public class JsonWebTokenUtility {
    private SignatureAlgorithm signatureAlgorithm;
    private Key secretKey;

    public JsonWebTokenUtility() {
        // 这里不是真正安全的实践。为了简单，我们存储一个静态key在这里，
        signatureAlgorithm = SignatureAlgorithm.HS512;
        String encodedKey = "L7A/6zARSkK1j7Vd5SDD9pSSqZlqF7mAhiOgRbgv9Smce6tf4cJnvKOjtKPxNNnWQj+2lQEScm3XIUjhW+YVZg==";

        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        secretKey = new SecretKeySpec(decodedKey, signatureAlgorithm.getJcaName());
    }

    public String createJsonWebToken(AuthTokenDetails authTokenDetails) {
        String token = Jwts.builder()
                .setSubject(authTokenDetails.getId().toString())
                .claim("username", authTokenDetails.getUsername())
                .claim("roleNames", authTokenDetails.getRoleNames())
                .setExpiration(authTokenDetails.getExpirationDate())
                .signWith(signatureAlgorithm, secretKey)
                .compact();
        return token;
    }

    public AuthTokenDetails parseAndValidate(String token) {
        AuthTokenDetails authTokenDetails = null;
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
            String userId = claims.getSubject();
            String username = (String) claims.get("username");
            List<String> roleNames = (List) claims.get("roleNames");
            Date expirationDate = claims.getExpiration();

            authTokenDetails = new AuthTokenDetails();
            authTokenDetails.setId(Long.valueOf(userId));
            authTokenDetails.setUsername(username);
            authTokenDetails.setRoleNames(roleNames);
            authTokenDetails.setExpirationDate(expirationDate);
        } catch (JwtException ex) {
            System.out.println(ex.getMessage() + ex);
        }
        return authTokenDetails;
    }

    private String serializeKey(Key key) {
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
        return encodedKey;
    }
}
