package gov.br.sefaz.desafio.tecnico.response;

import gov.br.sefaz.desafio.tecnico.entidades.Status;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TarefaResponse {

    private Long id;

    private String titulo;

    private String descricao;

    private Date dataCriacao;

    private String status;
}
