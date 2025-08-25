package gov.br.sefaz.desafio.tecnico.service;

import gov.br.sefaz.desafio.tecnico.entidades.Usuario;
import gov.br.sefaz.desafio.tecnico.excecao.LoginDuplicadoException;
import gov.br.sefaz.desafio.tecnico.excecao.UsuarioNaoEncontradoException;
import gov.br.sefaz.desafio.tecnico.repositorio.UsuarioRepository;
import gov.br.sefaz.desafio.tecnico.request.UsuarioRequest;
import gov.br.sefaz.desafio.tecnico.response.UsuarioResponse;
import gov.br.sefaz.desafio.tecnico.util.JwtUtil;
import gov.br.sefaz.desafio.tecnico.util.UsuarioUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService{

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioResponse cadastrarUsaurio(UsuarioRequest usuarioRequest) {

        if(usuarioRepository.existsByLogin(usuarioRequest.getLogin())) {
            throw new LoginDuplicadoException("Login informado já existe. Favor informar outro");
        }

        usuarioRequest.setSenha(passwordEncoder.encode(usuarioRequest.getSenha()));
        var usuario = UsuarioUtil.converterUsuario(usuarioRequest);

        usuarioRepository.save(usuario);

        return UsuarioUtil.converterUsuarioResponse(usuario);
    }

    public String logar(String login, String senha) {

        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));

        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new UsuarioNaoEncontradoException("Senha inválida");
        }
        return jwtUtil.generateToken(login);
    }

    public Optional<Usuario> buscarUsuarioPorLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }
}
