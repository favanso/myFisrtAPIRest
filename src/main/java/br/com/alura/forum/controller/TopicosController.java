package br.com.alura.forum.controller;

//import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import java.net.URI;
//import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    //O AUTOWIRED EH UMA INJECAO DE DEPENDENCIA
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    //@RequestMapping("/topicos")  / DEIXA ESSA @ EM CIMA DA CLASSE PORQUE TUDO RELACIONADO AOS TOPICOS ESTA NESSA CLASSE
    // E EM CIMA DOS METODOS COLOCA A @ HTML (POST, GET, ETC) 
    /*@ResponseBody (AO INVES DE USAR @CONTROLLER 
      USA RESTCONTROLLER E NAO PRECISA USAR O RESPONSE BODY*/
    @GetMapping
    public List<TopicoDto> lista(String nomeCurso) {
        if (nomeCurso == null) {
            List<Topico> topicos = topicoRepository.findAll();
            return TopicoDto.converter(topicos);
        } else {
            List<Topico> topicos = topicoRepository.findByCurso_Nome(nomeCurso);
            return TopicoDto.converter(topicos);
        }

    }

    /*  FORMATO RUIM, O MELHOR EH O FORMATO IMPLEMENTADO QUE BUSCA DO BANCO DE DADOS
        Topico topico = new Topico("duvida", "Duvida com Sping",new Curso
        ("Spring", "Programacao"));
        return TopicoDto.converter(Arrays.asList(topico, topico, topico));*/
    //PARA TESTAR PODE USAR O SOFTWARE POSTMAN
    @PostMapping
    @Transactional
    //COM O RETURN VOID ELE DEVOLVE 200 OU 500 COMO RESPOSTA
    // Deve usar o Response Entity e o URI
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody
            @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
        Topico topico = form.converter(cursoRepository);
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}")
                .buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDto(topico));

    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id) {

        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
        }
        return ResponseEntity.notFound().build();

    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody
            @Valid AtualizacaoTopicoForm form) {

        Optional<Topico> optional = topicoRepository.findById(id);
        if (optional.isPresent()) {
            Topico topico = form.atualizar(id, topicoRepository);
            return ResponseEntity.ok(new TopicoDto(topico));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remover(@PathVariable Long id) {
        Optional<Topico> optional = topicoRepository.findById(id);
        if (optional.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();

    }
}