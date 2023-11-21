package com.savemymoney.savemymoneyapi.services;

import com.savemymoney.savemymoneyapi.entities.Usuario;
import com.savemymoney.savemymoneyapi.repositories.UsuarioRepository;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository repository;

    public CustomUserDetailsService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = repository.localizarPorEmail(email);
        List<String> roles = new ArrayList<>();
        roles.add("Usuario");
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .roles(roles.toArray(new String[0]))
                .build();
    }

    protected Usuario getUsuarioLogado() {
        SecurityContext context = SecurityContextHolder.getContext();

        if (context == null) {
            return null;
        }
        return repository.localizarPorEmail(context.getAuthentication().getName());
    }
}
