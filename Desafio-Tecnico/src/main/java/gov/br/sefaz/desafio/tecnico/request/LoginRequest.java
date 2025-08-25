package gov.br.sefaz.desafio.tecnico.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotBlank(message = "O login é obrigatório")
    private String login;

    @NotBlank(message = "A senha é obrigatório")
    private String senha;
}
