package io.httpmurilo.forum.controller;

import io.httpmurilo.forum.dto.TopicoDto;
import io.httpmurilo.forum.model.Topico;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/topicos")
public class TopicosController {

    @RequestMapping("/")
    @ResponseBody
    public List<TopicoDto> listar() {
        return null;
    }
}
