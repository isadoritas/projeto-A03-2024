package dev.project.movies.servico;


import dev.project.movies.model.Usuario;
import dev.project.movies.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repositorio;
    private PasswordEncoder passwordEncoder;


    // CONSTRUTOR
    public UsuarioService(UsuarioRepository repositorio) {
        this.repositorio = repositorio;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    // LISTAR USUARIOS SALVOS NO BANCO
    public List<Usuario> listarUsuarios() {
        return repositorio.findAll();
    }


    // CADASTRAR USUARIO
    public Usuario cadastrarUsuario(Usuario usuario) {
        if (!repositorio.existsByEmail(usuario.getEmail())) {
            String encoder = passwordEncoder.encode(usuario.getSenha());
            usuario.setSenha(encoder);
            Usuario novoUsuario = repositorio.save(usuario);
            return novoUsuario;
        } else {
            throw new RuntimeException("email inserido já cadastrado");
        }
    }


    // DELETAR USUARIO DELETE
    public boolean excluirUsuario(Integer id) {
        var usuario = repositorio.findById(id);
        if (usuario.isPresent()) {
            repositorio.deleteById(id);
            return true;
        }
        return false;
    }
}
