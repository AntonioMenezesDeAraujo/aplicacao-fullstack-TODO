package gov.br.sefaz.desafio.tecnico.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequest {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O login é obrigatório")
    private String login;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 5, message = "A senha deve ter pelo menos 5 caracteres")
    private String senha;
}
