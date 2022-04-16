package io.httpmurilo.forum.repository;

import io.httpmurilo.forum.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> FindByEmail(String email);
}
