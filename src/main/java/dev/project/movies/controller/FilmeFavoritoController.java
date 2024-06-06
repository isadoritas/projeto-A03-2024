package dev.project.movies.controller;


import dev.project.movies.servico.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping("/favoritos")
public class FilmeFavoritoController {

    @Autowired
    private FilmeService service;

    // CONSTRUTOR
    public FilmeFavoritoController(FilmeService service) {
        this.service = service;
    }


    // FAVORITAR
    @PostMapping("/{id}")
    public ResponseEntity favoritarFilmes(@PathVariable Integer id) {
        service.favoritarFilmes(id);
        return ResponseEntity.status(201).body("Adicionado aos favoritos");
    }

    // LISTAR FILMES FAVORITOS
    @GetMapping()
    public ResponseEntity listarFilmesFavoritados() {
        return ResponseEntity.ok().body(service.listarFilmesFavoritos(service.pegarIdUsuario()));
    }

    // ESCOLHER FILME ALEATORIO
    @GetMapping("/random")
    public ResponseEntity filmeAleatorio() {
        return ResponseEntity.ok().body(service.escolherAleatorio());
    }

    // DELETAR FILME DA LISTA DE FAVORITOS
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletarFavorito(@PathVariable Integer id) {
        return ResponseEntity.status(204).body(service.deletarFilme(id));
    }
}
