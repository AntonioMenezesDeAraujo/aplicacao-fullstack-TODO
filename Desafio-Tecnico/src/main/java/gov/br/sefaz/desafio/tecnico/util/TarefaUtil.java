package gov.br.sefaz.desafio.tecnico.util;

import gov.br.sefaz.desafio.tecnico.entidades.Status;
import gov.br.sefaz.desafio.tecnico.entidades.Tarefa;
import gov.br.sefaz.desafio.tecnico.entidades.Usuario;
import gov.br.sefaz.desafio.tecnico.request.TarefaRequest;
import gov.br.sefaz.desafio.tecnico.response.TarefaResponse;

public class TarefaUtil {

    public static Tarefa converteTarefa(TarefaRequest tarefaRequest) {
        return Tarefa.builder()
                .titulo(tarefaRequest.getTitulo())
                .descricao(tarefaRequest.getDescricao())
                .status(Status.EM_ANDAMENTO)
                .build();
    }

    public static TarefaResponse converteTarefaResponse(Tarefa tarefa) {
        return TarefaResponse.builder()
                .titulo(tarefa.getTitulo())
                .descricao(tarefa.getDescricao())
                .dataCriacao(tarefa.getDataCriacao())
                .status(tarefa.getStatus().name())
                .id(tarefa.getId())
                .build();
    }
}
