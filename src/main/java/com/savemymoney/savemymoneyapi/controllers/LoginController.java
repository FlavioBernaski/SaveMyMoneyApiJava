package com.savemymoney.savemymoneyapi.controllers;

import com.savemymoney.savemymoneyapi.authentication.JwtUtil;
import com.savemymoney.savemymoneyapi.entities.Usuario;
import com.savemymoney.savemymoneyapi.entities.request.LoginRequest;
import com.savemymoney.savemymoneyapi.entities.response.ErrorResponse;
import com.savemymoney.savemymoneyapi.entities.response.LoginResponse;
import com.savemymoney.savemymoneyapi.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
@RequestMapping("/auth")
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
    public ResponseEntity<Object> logar(@RequestBody LoginRequest request) {
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha()));
            String email = authentication.getName();
            LoginResponse response = new LoginResponse(email, jwtUtil.createToken(new Usuario(email)));
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, "Usuário ou senha incorretos");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
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
