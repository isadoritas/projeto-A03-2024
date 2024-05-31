package dev.project.movies.controller;

import dev.project.movies.infra.exception.ValidacaoError;
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


    // DELETAR USUARIO
    @DeleteMapping("/{id}")
    public ResponseEntity deletarUsuario(@PathVariable Integer id) throws ValidacaoError {
        boolean existe = service.excluirUsuario(id);
        if (!existe) {
            throw new ValidacaoError("Usuário não encontrado");
        }
        return ResponseEntity.status(204).build();
    }

}
