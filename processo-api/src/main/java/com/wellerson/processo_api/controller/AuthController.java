/*
 * package com.wellerson.processo_api.controller;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.http.HttpStatus; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestBody; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * import com.wellerson.processo_api.dto.AuthRequest; import
 * com.wellerson.processo_api.dto.AuthResponse; import
 * com.wellerson.processo_api.security.JwtUtil;
 * 
 * @RestController
 * 
 * @RequestMapping("/auth") public class AuthController {
 * 
 * @Autowired private JwtUtil jwtUtil;
 * 
 * @PostMapping("/login") public ResponseEntity<?> login(@RequestBody
 * AuthRequest request) { // Exemplo fixo — pode validar contra banco se quiser
 * if ("admin".equals(request.getUsername()) &&
 * "senha123".equals(request.getPassword())) { String token =
 * jwtUtil.gerarToken(request.getUsername()); return ResponseEntity.ok(new
 * AuthResponse(token)); } return
 * ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
 * } }
 * 
 */