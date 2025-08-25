package gov.br.sefaz.desafio.tecnico.repositorio;

import gov.br.sefaz.desafio.tecnico.entidades.Tarefa;
import gov.br.sefaz.desafio.tecnico.entidades.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    Page<Tarefa> findByTituloContainingIgnoreCaseAndUsuario(
            String titulo,
            Usuario usuario,
            Pageable pageable
    );

    Page<Tarefa> findByUsuario(Usuario usuario, Pageable pageable);
}
