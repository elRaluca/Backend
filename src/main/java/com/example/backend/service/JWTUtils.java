package com.example.backend.service;

import com.example.backend.entity.OurUsers;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JWTUtils {
    private SecretKey Key;
    private static  final long EXPIRATION_TIME=86400000;


    public JWTUtils(){
        String secreteString="2e7a6b285f27223c6662775a3a50565c2b2e287031734c37586e73293f";
        byte[] keyByte= Base64.getDecoder().decode(secreteString.getBytes(StandardCharsets.UTF_8));
        this.Key= new SecretKeySpec(keyByte,"HmacSHA256");

    }
    public String generateToken(UserDetails userDetails) {
        OurUsers user = (OurUsers) userDetails;
        Collection<String> roles = userDetails.getAuthorities().stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        claims.put("sub", userDetails.getUsername());
        claims.put("userId", user.getId());
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Key, SignatureAlgorithm.HS256) // Semnarea tokenului cu algoritmul HmacSHA256 și cheia secretă
                .compact(); // Compactarea tokenului
    }


    public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails) {
        OurUsers user = (OurUsers) userDetails;
        // Obțineți rolurile utilizatorului și transformați-le într-o listă de șiruri
        Collection<String> roles = userDetails.getAuthorities().stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        // Adăugați rolurile în HashMap-ul de claims
        claims.put("roles", roles);
        claims.put("sub", userDetails.getUsername());
        claims.put("userId", user.getId());


        // Generați refresh tokenul JWT cu claims-urile și alte informații necesare
        return Jwts.builder()
                .setClaims(claims) // Setarea claims-urilor
                .setIssuedAt(new Date(System.currentTimeMillis())) // Setarea momentului de emisie a tokenului
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Setarea momentului de expirare a tokenului
                .signWith(Key, SignatureAlgorithm.HS256) // Semnarea tokenului cu algoritmul HmacSHA256 și cheia secretă
                .compact(); // Compactarea tokenului
    }

    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);

    }
    private <T> T extractClaims(String token, Function<Claims,T> claimsTFunction){
        return claimsTFunction.apply(Jwts.parser().verifyWith(Key).build().parseSignedClaims(token).getPayload());

    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username=extractUsername(token);
        return(username.equals(userDetails.getUsername())&&!isTokenExpired(token));
    }

    public boolean isTokenExpired(String token){
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

    public Integer extractUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(Key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("userId", Integer.class);  // Extrage userId ca Integer
    }

 
}

