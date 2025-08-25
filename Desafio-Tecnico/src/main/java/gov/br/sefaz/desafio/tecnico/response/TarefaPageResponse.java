package gov.br.sefaz.desafio.tecnico.response;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TarefaPageResponse {
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private List<TarefaResponse> tarefas;
}
