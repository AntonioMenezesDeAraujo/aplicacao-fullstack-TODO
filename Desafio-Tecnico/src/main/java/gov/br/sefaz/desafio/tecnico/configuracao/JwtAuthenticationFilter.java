package gov.br.sefaz.desafio.tecnico.configuracao;

import gov.br.sefaz.desafio.tecnico.entidades.Usuario;
import gov.br.sefaz.desafio.tecnico.repositorio.UsuarioRepository;
import gov.br.sefaz.desafio.tecnico.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(UsuarioRepository usuarioRepository, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtil.validateToken(token)) {
                String login = jwtUtil.extractUsername(token);
                Usuario usuario = usuarioRepository.findByLogin(login).orElse(null);

                if (usuario != null) {
                    User userDetails = new User(
                            usuario.getLogin(),
                            usuario.getSenha(),
                            List.of(new SimpleGrantedAuthority("USER"))
                    );

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
