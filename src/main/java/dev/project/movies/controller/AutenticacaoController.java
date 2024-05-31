package dev.project.movies.controller;


import dev.project.movies.infra.security.DadosTokenJWT;
import dev.project.movies.infra.security.TokenService;
import dev.project.movies.model.DadosLogin;
import dev.project.movies.model.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;


    // Validar login e devolver o token de acesso
    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosLogin dados) {
        var tokenSpring = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());  // Converter o DTO  em um token autenticado
        var authentication = manager.authenticate(tokenSpring);

        var tokenCodigo = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenCodigo));
    }









}
