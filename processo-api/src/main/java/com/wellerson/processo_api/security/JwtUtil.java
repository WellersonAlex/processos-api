/*
 * package com.wellerson.processo_api.security;
 * 
 * import java.util.Date;
 * 
 * import org.springframework.stereotype.Component;
 * 
 * import io.jsonwebtoken.Jwts; import io.jsonwebtoken.SignatureAlgorithm;
 * 
 * @Component public class JwtUtil {
 * 
 * private static final String SECRET_KEY = "segredo-super-seguro"; private
 * static final long EXPIRATION_TIME = 86400000; // 1 dia em ms
 * 
 * public String gerarToken(String username) { return Jwts.builder()
 * .setSubject(username) .setIssuedAt(new Date()) .setExpiration(new
 * Date(System.currentTimeMillis() + EXPIRATION_TIME))
 * .signWith(SignatureAlgorithm.HS256, SECRET_KEY) .compact(); }
 * 
 * public String validarToken(String token) { return Jwts.parser()
 * .setSigningKey(SECRET_KEY) .parseClaimsJws(token) .getBody() .getSubject(); }
 * }
 */