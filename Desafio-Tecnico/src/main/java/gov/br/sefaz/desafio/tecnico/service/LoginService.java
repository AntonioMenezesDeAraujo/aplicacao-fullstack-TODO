package gov.br.sefaz.desafio.tecnico.service;

import gov.br.sefaz.desafio.tecnico.entidades.Usuario;
import gov.br.sefaz.desafio.tecnico.repositorio.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles("USER")
                .build();
    }
}
