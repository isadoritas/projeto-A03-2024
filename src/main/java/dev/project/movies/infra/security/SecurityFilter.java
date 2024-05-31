package dev.project.movies.infra.security;


import dev.project.movies.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository repository;


    // Filtro
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("chamando filtro");
        var tokenJWT = recuperarToken(request);  // pegar o token

        if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT); // pegar o subject dentro do token
            var usuario = repository.findByEmail(subject);  // recuperar objeto usuario
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);  // setar usuario logado
            System.out.println("logado");
        }

        filterChain.doFilter(request, response);  // Encaminhar para o proximo filtro
    }


    // Pegar o token
    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");

        }
        return null;
    }



}
