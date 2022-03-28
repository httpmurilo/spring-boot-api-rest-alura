package io.httpmurilo.forum.dto;

public class ErrorDto {

    private String campo;
    private String mensagem;

    public ErrorDto(String campo, String mensagem) {
        this.campo = campo;
        this.mensagem = mensagem;
    }

    public String getCampo() {
        return campo;
    }

    public String getMensagem() {
        return mensagem;
    }
}
