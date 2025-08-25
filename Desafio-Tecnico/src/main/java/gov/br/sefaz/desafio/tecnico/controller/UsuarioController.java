package gov.br.sefaz.desafio.tecnico.controller;

import gov.br.sefaz.desafio.tecnico.request.LoginRequest;
import gov.br.sefaz.desafio.tecnico.request.UsuarioRequest;
import gov.br.sefaz.desafio.tecnico.response.LoginResponse;
import gov.br.sefaz.desafio.tecnico.response.UsuarioResponse;
import gov.br.sefaz.desafio.tecnico.service.UsuarioService;
import gov.br.sefaz.desafio.tecnico.swagger.UsuarioApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuario")
public class UsuarioController implements UsuarioApi {

    private final UsuarioService usuarioService;

    @PostMapping
    public UsuarioResponse criarUsuario(@Valid @RequestBody UsuarioRequest request) {
        return usuarioService.cadastrarUsaurio(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        String token = usuarioService.logar(request.getLogin(), request.getSenha());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
