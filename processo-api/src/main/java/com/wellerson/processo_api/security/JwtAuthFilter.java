/*
 * package com.wellerson.processo_api.security;
 * 
 * import java.io.IOException;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; //import
 * org.springframework.security.authentication.
 * UsernamePasswordAuthenticationToken; //import
 * org.springframework.security.core.context.SecurityContextHolder; import
 * org.springframework.stereotype.Component; import
 * org.springframework.web.filter.OncePerRequestFilter;
 * 
 * import jakarta.servlet.FilterChain; import jakarta.servlet.ServletException;
 * import jakarta.servlet.http.HttpServletRequest; import
 * jakarta.servlet.http.HttpServletResponse;
 * 
 * @Component public class JwtAuthFilter extends OncePerRequestFilter {
 * 
 * @Autowired private JwtUtil jwtUtil;
 * 
 * @Override protected void doFilterInternal(HttpServletRequest request,
 * HttpServletResponse response, FilterChain filterChain) throws
 * ServletException, IOException {
 * 
 * String authHeader = request.getHeader("Authorization");
 * 
 * if (authHeader != null && authHeader.startsWith("Bearer ")) { String token =
 * authHeader.substring(7); try { String username = jwtUtil.validarToken(token);
 * } catch (Exception e) {
 * response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
 * "Token inválido ou expirado"); return; } } else {
 * response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token ausente");
 * return; }
 * 
 * filterChain.doFilter(request, response); } }
 * 
 */