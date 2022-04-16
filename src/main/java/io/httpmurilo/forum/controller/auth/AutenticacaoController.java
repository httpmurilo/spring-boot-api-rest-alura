package io.httpmurilo.forum.controller.auth;

import io.httpmurilo.forum.dto.LoginFormDto;
import io.httpmurilo.forum.dto.TokenDto;
import io.httpmurilo.forum.service.auth.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginFormDto dto) {
        UsernamePasswordAuthenticationToken dadosLogin = dto.converter();

        try {
            Authentication authenticate = authManager.authenticate(dadosLogin);
            String tokenJwt = tokenService.gerarToken(authenticate);
            return ResponseEntity.ok(new TokenDto(tokenJwt, "Bearer"));

        } catch (AuthenticationException ex) {
            return ResponseEntity.badRequest().build();
        }

    }
}
