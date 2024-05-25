package dev.project.movies.controller;


import dev.project.movies.exception.ValidacaoError;
import dev.project.movies.model.DadosLogin;
import dev.project.movies.model.Usuario;
import dev.project.movies.servico.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }


    // LISTAR USUARIOS
    @GetMapping
    public ResponseEntity listarUsuarios() {
        return ResponseEntity.ok().body(service.listarUsuarios());
    }

    // CADASTRAR USUARIOS
    @PostMapping
    public ResponseEntity cadastrarUsuario(@RequestBody @Valid Usuario usuario) {
        return ResponseEntity.status(201).body(service.cadastrarUsuario(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity logarUsuario(@RequestBody DadosLogin dados) throws ValidacaoError {
        boolean valid = service.validarSenha(dados);
       if (!valid) {  // SE FOR FALSO
           throw new ValidacaoError("Email ou senha inválidos");
       }
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarUsuario(@PathVariable Integer id) throws ValidacaoError {
        boolean existe = service.excluirUsuario(id);
        if (!existe) {
            throw new ValidacaoError("Usuário não encontrado");
        }
        return ResponseEntity.status(204).build();
    }

}
