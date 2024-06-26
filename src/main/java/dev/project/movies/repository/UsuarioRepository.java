package dev.project.movies.repository;

import dev.project.movies.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;


// REPOSITORIO PARA ACESSAR O BANCO DE DADOS
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    UserDetails findByEmail(String email);

    boolean existsByEmail(String email);
}
