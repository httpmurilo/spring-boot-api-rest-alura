package io.httpmurilo.forum.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //configurações de autenticação e controle de acesso
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
    //configurações de recursos estastico
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    //configurações de autorizações e url e quem pode acessar cada url
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin();
    }
}
