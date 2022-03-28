package io.httpmurilo.forum.dto;

import io.httpmurilo.forum.model.Topico;
import io.httpmurilo.forum.repository.TopicoRepository;

import javax.validation.constraints.NotNull;

public class TopicoEditDto {

    @NotNull
    private String titulo;
    @NotNull
    private String mensagem;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Topico atualizar(Long id, TopicoRepository topicoRepository) {
        Topico topico = topicoRepository.getById(id);

        topico.setTitulo(this.titulo);
        topico.setMensagem(this.mensagem);

        return topico;
    }
}