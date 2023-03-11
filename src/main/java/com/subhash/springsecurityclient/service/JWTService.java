package com.subhash.springsecurityclient.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    private final static String SIGNING_KEY="jK8r2TYB8JGCYnClzc9Exl/LgEoQHy0XDNs9f0nGzzADB1TLVUpJm3Y5SMAk6fXmnJ0Ab851FDYQ5YpeMfISXIBBjYocIwWvY01nOJ9zOASvJDNOcZuHbzMQm4StfdZVAgtg67TGRFmtEei7WopaPqhGjEiz2lUQqKkjgdw1gAWzvRqvtNGvgHvyJzQYKcKONRSArWrCE2v8r7ePzJ8MIO6ZIb43TMDElJYy2SxLtYT2j1trEBk8MPw/8pAI7psSX1QHKthaSbIhySUbXTsYdlMjC6NW/rstN2PTQp2iP3vG8wtMMXjfsgtcTOr7tmFwl7sqf7jlYkHBLBk7aBMqXLRvhKLMgYoJfNQSJiz7l3Q=";
    public String extractUserName(String jwtToken) {
        return extractClaim(jwtToken, Claims:: getSubject);
    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

    }

    private Key getSignInKey(){
            byte[] keyBytes = Base64.getDecoder().decode(SIGNING_KEY);
            return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T>  T extractClaim ( String jwtToken, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims( jwtToken);
        return claimResolver.apply(claims);
    }


    // -----------------------------

    public String generateJwtToken(Map<String, Object> extraClaims, UserDetails userDetails) {

        return  Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt( new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateJwtToken( UserDetails userDetails) {

        return  Jwts
                .builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt( new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        String userName = extractUserName( jwtToken);
        return userName.equalsIgnoreCase(userDetails.getUsername()) && isTokenExpired(jwtToken);

    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return  extractClaim(jwtToken, Claims::getExpiration);
    }

}
