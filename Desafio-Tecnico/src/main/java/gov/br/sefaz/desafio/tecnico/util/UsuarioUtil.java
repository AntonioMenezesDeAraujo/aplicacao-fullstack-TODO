package gov.br.sefaz.desafio.tecnico.util;

import gov.br.sefaz.desafio.tecnico.entidades.Usuario;
import gov.br.sefaz.desafio.tecnico.request.UsuarioRequest;
import gov.br.sefaz.desafio.tecnico.response.UsuarioResponse;

public class UsuarioUtil {

    public static Usuario converterUsuario(UsuarioRequest usuarioRequest) {
        return Usuario.builder()
                .login(usuarioRequest.getLogin())
                .nome(usuarioRequest.getNome())
                .senha(usuarioRequest.getSenha())
                .build();
    }

    public static UsuarioResponse converterUsuarioResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .login(usuario.getLogin())
                .nome(usuario.getNome())
                .build();
    }
}
