package io.httpmurilo.forum.repository;

import io.httpmurilo.forum.model.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    //underline relacionamento ->
    Page<Topico> findByCurso_Nome(String nomeCurso, Pageable paginacao);
}
