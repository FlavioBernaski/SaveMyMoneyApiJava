package com.savemymoney.savemymoneyapi.controllers;

import com.savemymoney.savemymoneyapi.authentication.JwtUtil;
import com.savemymoney.savemymoneyapi.entities.Usuario;
import com.savemymoney.savemymoneyapi.entities.request.LoginRequest;
import com.savemymoney.savemymoneyapi.entities.request.TokenRequest;
import com.savemymoney.savemymoneyapi.entities.response.ErrorResponse;
import com.savemymoney.savemymoneyapi.entities.response.LoginResponse;
import com.savemymoney.savemymoneyapi.repositories.UsuarioRepository;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/auth")
@Slf4j
public class LoginController {
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginController(AuthenticationManager manager, JwtUtil jwtUtil) {
        this.authenticationManager = manager;
        this.jwtUtil = jwtUtil;
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<?> logar(@RequestBody LoginRequest request) {
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha()));
            Usuario usuario = repository.localizarPorEmail(authentication.getName());
            LoginResponse response = new LoginResponse(usuario, jwtUtil.createToken(usuario));
            log.info("Usuário {} logou no sistema", response.getUsuario().getEmail());
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            log.error("Usuário {} tentou logar com credenciais inválidas", request.getEmail());
            ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, "Usuário ou senha incorretos");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @ResponseBody
    @PostMapping("/validate")
    public ResponseEntity<?> validarToken(@RequestBody TokenRequest token) {
        try {
            Claims claims = jwtUtil.parseJwtClaims(token.getToken());
            Usuario usuario = repository.localizarPorEmail(claims.getSubject());
            if (usuario != null) {
                log.info("Usuário {} verificou o token com sucesso", usuario.getEmail());
                return ResponseEntity.ok().body(usuario);
            }
            throw new Exception("Não foi possível validar o token do usuário");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity registrar(@RequestBody LoginRequest request) {
        if (repository.localizarPorEmail(request.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(HttpStatus.BAD_REQUEST, "Email já cadastrado"));
        }
        Usuario usuario = new Usuario();
        usuario.setId(UUID.randomUUID());
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        repository.save(usuario);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
