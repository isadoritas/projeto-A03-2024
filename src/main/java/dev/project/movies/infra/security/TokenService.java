package dev.project.movies.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import dev.project.movies.model.Usuario;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {

    private String senha = System.getenv("JWT_SECRET");


    // Criar um token relacionado a um usuario
    public String gerarToken(Usuario usuario) {
        try {
            var algoritmo = Algorithm.HMAC256(senha);                       // Senha para criar o token
            return JWT.create()                                            // Criar token
                    .withIssuer("Project_AO3-API-MOVIES")                 // Indentificar a aplicação
                    .withSubject(usuario.getEmail())                     // Relacionar o token a um usuário
                    .withExpiresAt(dataExpiracao())                     // Data de expiração do token
                    .withClaim("Nome", usuario.getNome())        // Relacionar token ao nome do usuário
                    .sign(algoritmo);                                 // fazer a assinatura
        } catch (JWTCreationException exception){
            throw new RuntimeException("erro ao gerar token JWT ", exception);
        }
    }

    // Pegar o Objeto dentro de um token
    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(senha);
            return JWT.require(algoritmo)
                    .withIssuer("Project_AO3-API-MOVIES")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token JWT inválido ou expirado");
        }
    }


    // Data de expiração do token
    private Instant dataExpiracao() {

        // Retornar a duração de 2 horas do token
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }


}
