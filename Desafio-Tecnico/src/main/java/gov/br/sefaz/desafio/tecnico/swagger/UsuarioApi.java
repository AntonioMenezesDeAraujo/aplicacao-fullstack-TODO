package gov.br.sefaz.desafio.tecnico.swagger;

import gov.br.sefaz.desafio.tecnico.request.LoginRequest;
import gov.br.sefaz.desafio.tecnico.request.UsuarioRequest;
import gov.br.sefaz.desafio.tecnico.response.LoginResponse;
import gov.br.sefaz.desafio.tecnico.response.UsuarioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UsuarioApi {
    @Operation(summary = "Cria um novo usuário")
    @PostMapping
    UsuarioResponse criarUsuario(@RequestBody @Valid @Parameter(description = "Dados do usuário") UsuarioRequest request);

    @Operation(summary = "Realiza login e retorna token JWT")
    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestBody @Valid @Parameter(description = "Credenciais de login") LoginRequest request);
}
