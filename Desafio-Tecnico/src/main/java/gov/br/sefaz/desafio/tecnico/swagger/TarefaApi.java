package gov.br.sefaz.desafio.tecnico.swagger;

import gov.br.sefaz.desafio.tecnico.request.TarefaRequest;
import gov.br.sefaz.desafio.tecnico.response.TarefaPageResponse;
import gov.br.sefaz.desafio.tecnico.response.TarefaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

public interface TarefaApi {

    @Operation(summary = "Lista tarefas paginadas", description = "Busca tarefas pelo título, podendo paginar e ordenar por data de criação ou status")
    @GetMapping
    ResponseEntity<TarefaPageResponse> listarTarefas(
            @Parameter(description = "Título da tarefa para filtrar") @RequestParam(required = false) String titulo,
            @AuthenticationPrincipal UserDetails userDetails,
            @Parameter(description = "Número da página") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo para ordenar") @RequestParam(defaultValue = "dataCriacao") String sortBy,
            @Parameter(description = "Direção da ordenação: asc ou desc") @RequestParam(defaultValue = "asc") String direction
    );

    @Operation(summary = "Cria uma nova tarefa")
    @PostMapping
    ResponseEntity<TarefaResponse> salvarTarefa(@RequestBody @Valid TarefaRequest request, @AuthenticationPrincipal UserDetails userDetails);

    @Operation(summary = "Atualiza uma tarefa existente")
    @PutMapping
    ResponseEntity<TarefaResponse> atualizarTarefa(@RequestBody @Valid TarefaRequest request);

    @Operation(summary = "Deleta uma tarefa pelo ID")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletarTarefa(@PathVariable Long id);
}
