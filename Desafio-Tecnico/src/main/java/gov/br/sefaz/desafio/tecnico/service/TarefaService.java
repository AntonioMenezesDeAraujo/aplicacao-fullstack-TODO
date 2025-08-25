package gov.br.sefaz.desafio.tecnico.service;

import gov.br.sefaz.desafio.tecnico.entidades.Tarefa;
import gov.br.sefaz.desafio.tecnico.entidades.Usuario;
import gov.br.sefaz.desafio.tecnico.repositorio.TarefaRepository;
import gov.br.sefaz.desafio.tecnico.request.TarefaRequest;
import gov.br.sefaz.desafio.tecnico.response.TarefaPageResponse;
import gov.br.sefaz.desafio.tecnico.response.TarefaResponse;
import gov.br.sefaz.desafio.tecnico.util.TarefaUtil;
import gov.br.sefaz.desafio.tecnico.util.UsuarioUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final UsuarioService usuarioService;

    public TarefaResponse salvarTarefa(TarefaRequest tarefaRequest, String login) {

        var usuario = usuarioService.buscarUsuarioPorLogin(login).orElseThrow(() -> new RuntimeException("Usuario não encontrada"));

        var tarefa = TarefaUtil.converteTarefa(tarefaRequest);
        tarefa.setDataCriacao(new Date());
        tarefa.setDataAtualizacao(new Date());
        tarefa.setUsuario(usuario);
        var tarefaDB = tarefaRepository.save(tarefa);
        return TarefaUtil.converteTarefaResponse(tarefaDB);
    }

    public TarefaResponse atualizarTarefa(TarefaRequest tarefaRequest) {
        var tarefaDB = tarefaRepository.findById(tarefaRequest.getId())
                .map(tarefa -> {
                    tarefa.setTitulo(tarefaRequest.getTitulo());
                    tarefa.setDescricao(tarefaRequest.getDescricao());
                    tarefa.setStatus(tarefaRequest.getStatus());
                    tarefa.setDataAtualizacao(new Date());
                    return tarefaRepository.save(tarefa);
                })
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));  //TODO

        return  TarefaUtil.converteTarefaResponse(tarefaDB);
    }

    public void deletarTarefa(Long id) {
        if (!tarefaRepository.existsById(id)) {
            throw new RuntimeException("Tarefa não encontrada"); //TODO
        }
        tarefaRepository.deleteById(id);
    }

    public TarefaPageResponse listarPorTitulo(String titulo, String login, int page, int size, String sortBy, String direction) {

        var usuario = usuarioService.buscarUsuarioPorLogin(login).orElseThrow(() -> new RuntimeException("Usuario não encontrada"));

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<TarefaResponse> paginasResultado;

        if (titulo == null || titulo.trim().isEmpty()) {
            paginasResultado = tarefaRepository.findByUsuario(usuario, pageable)
                    .map(TarefaUtil::converteTarefaResponse);
        } else {
            paginasResultado = tarefaRepository.findByTituloContainingIgnoreCaseAndUsuario(titulo, usuario, pageable)
                    .map(TarefaUtil::converteTarefaResponse);
        }

        return new TarefaPageResponse(
                paginasResultado.getTotalElements(),
                paginasResultado.getTotalPages(),
                paginasResultado.getNumber(),
                paginasResultado.getContent() // aqui pegamos a lista de TarefaResponse
        );
    }
}
