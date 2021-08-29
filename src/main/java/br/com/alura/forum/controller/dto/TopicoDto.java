package br.com.alura.forum.controller.dto;

import br.com.alura.forum.modelo.Topico;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

public class TopicoDto {
//METODO ANTES DE IMPLEMENTAR PAGINACAO
//   public static List<TopicoDto> converter(List<Topico> topicos) {
//       return topicos.stream().map(TopicoDto::new).collect(Collectors.toList());

    public static Page<TopicoDto> converter(Page<Topico> topicos) {
        return topicos.map(TopicoDto::new);

    }

    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime data;

    public TopicoDto(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.data = topico.getDataCriacao();
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getData() {
        return data;
    }

}
