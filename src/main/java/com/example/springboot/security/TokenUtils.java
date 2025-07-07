package com.example.springboot.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TokenUtils {
    private static String SIGNING_KEY;
    private static Long TOKEN_VALIDITY;
    private static String AUTHORITIES_KEY;

    @Value("${jwt.signing.key}")
    public void setSIGNING(String value) { this.SIGNING_KEY = value; }
    @Value("${jwt.token.validity}")
    public void setVALIDITY(Long value) { this.TOKEN_VALIDITY = value; }
    @Value("${jwt.authorities.key}")
    public void setAUTHORITIES(String value) { this.AUTHORITIES_KEY = value; }

    public TokenUtils() {}

    // =========================
    // get claims from token  //
    // =========================

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // ==============================
    //   encrypt and decrypt token //
    // ==============================

    public static String createToken(Authentication authentication){
        long expirationTime = TOKEN_VALIDITY*1000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        // save in the token the authorities of the user
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    public static String createTokenUser(String email, Set<SimpleGrantedAuthority> authoritiesList){
        long expirationTime = TOKEN_VALIDITY*1000;
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);

        // save in the token the authorities of the user
        String authorities = authoritiesList.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication( String token ){
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(SIGNING_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            // get email form token
            String email = claims.getSubject();

            // get authorities from token
            final Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            return new UsernamePasswordAuthenticationToken(email, "", authorities);
        }catch (JwtException e){
            return null;
        }
    }
}
