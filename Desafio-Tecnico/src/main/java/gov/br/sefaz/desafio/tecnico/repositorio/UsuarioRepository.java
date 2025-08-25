package gov.br.sefaz.desafio.tecnico.repositorio;

import gov.br.sefaz.desafio.tecnico.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByLogin(String login);
    boolean existsByLoginAndSenha(String login, String senha);
    Optional<Usuario> findByLogin(String  login);
}
