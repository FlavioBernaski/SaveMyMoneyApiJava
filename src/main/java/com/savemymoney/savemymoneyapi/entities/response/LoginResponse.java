package com.savemymoney.savemymoneyapi.entities.response;

import com.savemymoney.savemymoneyapi.entities.Usuario;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoginResponse {
    private Usuario usuario;
    private String token;

    public LoginResponse(Usuario usuario, String token) {
        this.usuario = usuario;
        this.token = token;
    }
}
