package gov.br.sefaz.desafio.tecnico.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UsuarioResponse {

    private Long id;

    private String nome;

    private String login;
}
