package io.httpmurilo.forum.dto;

public class TokenDto {

    public TokenDto(String tokenJwt, String tipo) {
    }

    private String token;
    private String tipo;

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }
}
