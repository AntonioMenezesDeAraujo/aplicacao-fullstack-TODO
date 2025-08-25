package gov.br.sefaz.desafio.tecnico.request;

import gov.br.sefaz.desafio.tecnico.entidades.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TarefaRequest {

    private Long id;

    private Status status;

    @NotBlank(message = "O titulo é obrigatório")
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;
}
