package br.com.alura.forum.repository;

import br.com.alura.forum.modelo.Topico;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TopicoRepository extends JpaRepository<Topico, Long> {
//NOME COM UNDERLINE SERVE PARA SABER QUE A PROPRIEDADE VEM DA ENTIDADE CURSO E 
//    NAO DE UM ITEM DENTRO DA ENTIDADE TOPICO. 
   Page<Topico> findByCurso_Nome(String nomeCurso, Pageable paginacao);
    
// OUTRA FORMA DE CRIAR COM UM NOME ESPECIFICO PARA O METODO, TEM QUE CRIAR A QUERY
//@Query ("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
// List<Topico> carrregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso);    
    
    
}
