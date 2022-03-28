package io.httpmurilo.forum.controller;

import io.httpmurilo.forum.dto.DetalhesTopicoDto;
import io.httpmurilo.forum.dto.TopicoDto;
import io.httpmurilo.forum.dto.TopicoEditDto;
import io.httpmurilo.forum.dto.TopicoInputDto;
import io.httpmurilo.forum.model.Topico;
import io.httpmurilo.forum.repository.CursoRepository;
import io.httpmurilo.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public List<TopicoDto> listar(String nomeCurso) {

        List<Topico> topicos = new ArrayList<>();

        if(nomeCurso == null) {
            topicos = topicoRepository.findAll();
            return TopicoDto.converter(topicos);
        }

        topicos = topicoRepository.findByCurso_Nome(nomeCurso);
        return TopicoDto.converter(topicos);

    }

    @PostMapping("/")
    public ResponseEntity<TopicoDto> cadastrar(@Valid @RequestBody TopicoInputDto dto, UriComponentsBuilder uriBuilder) {
        Topico topico = dto.converter(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @GetMapping("/{id}")
    public DetalhesTopicoDto detalhar(@PathVariable Long id) {
        Topico topico = topicoRepository.getById(id);
        return new DetalhesTopicoDto(topico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid TopicoEditDto topicoDto) {
    Topico topico = topicoDto.atualizar(id, topicoRepository);

    //não precisa chamar o metodo de salvamento, pois o JPA já realiza o reconhecimento

    return  ResponseEntity.ok(new TopicoDto(topico));


    }
}