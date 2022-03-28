package io.httpmurilo.forum.controller;

import io.httpmurilo.forum.dto.DetalhesTopicoDto;
import io.httpmurilo.forum.dto.TopicoDto;
import io.httpmurilo.forum.dto.TopicoEditDto;
import io.httpmurilo.forum.dto.TopicoInputDto;
import io.httpmurilo.forum.model.Topico;
import io.httpmurilo.forum.repository.CursoRepository;
import io.httpmurilo.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    @Cacheable(value = "listaDeTopicos")
    public Page<TopicoDto> listar(@RequestParam(required = false) String nomeCurso,
                                  @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10)
                                  Pageable paginacao) {

        if(nomeCurso == null) {
            Page<Topico> all = topicoRepository.findAll(paginacao);
            return TopicoDto.converter(all);
        }

        Page<Topico> topicos= topicoRepository.findByCurso_Nome(nomeCurso, paginacao);
        return TopicoDto.converter(topicos);

     }

    @PostMapping("/")
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<TopicoDto> cadastrar(@Valid @RequestBody TopicoInputDto dto, UriComponentsBuilder uriBuilder) {
        Topico topico = dto.converter(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));
    }

    @GetMapping("/{id}")
    public DetalhesTopicoDto detalhar(@PathVariable Long id) {
        Optional<Topico> topico = Optional.ofNullable(topicoRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND)));

        return new DetalhesTopicoDto(topico.get());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid TopicoEditDto topicoDto) {
    Topico topico = topicoDto.atualizar(id, topicoRepository);

    //não precisa chamar o metodo de salvamento, pois o JPA já realiza o reconhecimento

    return  ResponseEntity.ok(new TopicoDto(topico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
        topicoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}