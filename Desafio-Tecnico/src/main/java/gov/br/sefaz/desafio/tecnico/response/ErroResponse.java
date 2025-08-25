package gov.br.sefaz.desafio.tecnico.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErroResponse {
    private int status;
    private String mensagem;
}
