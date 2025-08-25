package gov.br.sefaz.desafio.tecnico.controller;

import gov.br.sefaz.desafio.tecnico.request.TarefaRequest;
import gov.br.sefaz.desafio.tecnico.response.TarefaPageResponse;
import gov.br.sefaz.desafio.tecnico.response.TarefaResponse;
import gov.br.sefaz.desafio.tecnico.service.TarefaService;
import gov.br.sefaz.desafio.tecnico.swagger.TarefaApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tarefa")
public class TarefaController implements TarefaApi {

    private final TarefaService tarefaService;

    @GetMapping
    public ResponseEntity<TarefaPageResponse> listarTarefas(
            @RequestParam(required = false) String titulo,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "dataCriacao") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        TarefaPageResponse response = tarefaService.listarPorTitulo(titulo, userDetails.getUsername(), page, size, sortBy, direction);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<TarefaResponse> salvarTarefa(@RequestBody @Valid TarefaRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        TarefaResponse response = tarefaService.salvarTarefa(request, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping
    public ResponseEntity<TarefaResponse> atualizarTarefa(
            @RequestBody @Valid TarefaRequest request
    ) {
        TarefaResponse response = tarefaService.atualizarTarefa(request);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id) {
        tarefaService.deletarTarefa(id);
        return ResponseEntity.noContent().build();
    }
}
