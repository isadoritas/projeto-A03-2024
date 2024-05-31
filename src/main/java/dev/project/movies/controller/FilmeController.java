package dev.project.movies.controller;

import dev.project.movies.servico.FilmeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@CrossOrigin("*")  // LIBERAR TODAS PORTAS
@RestController
@RequestMapping("/filmes")
public class FilmeController {

   private FilmeService service;

   // CONSTRUTOR
   public FilmeController(FilmeService service) {
       this.service = service;
   }

   // ENDPOINTS

    // CATALOGO DOS FILMES
    @GetMapping
    public ResponseEntity catalogo() {
       return ResponseEntity.ok().body(service.listarFilmesBanco());
    }

    // FILTRAR POR GENERO
    @GetMapping("/gen/{genero}")
    public ResponseEntity listarFilmespeloGenero(@PathVariable int genero) throws IOException, InterruptedException {
       return ResponseEntity.ok().body(service.listarFilmesPeloGenero(genero));
    }

    // ORDEM ALFABETICA
    @GetMapping("/ordem/alfabetica")
    public ResponseEntity listaFilmesOrdemAlfabetica() {
       return ResponseEntity.ok().body(service.ordenarTitulos());
    }

    // AVALIACAO
    @GetMapping("/ordenar/avaliacao")
    public ResponseEntity listarFilmesPelaAvaliacao() {
        return ResponseEntity.ok().body(service.ordenarAvaliacao());
    }

    // PESQUISA
    @GetMapping("/{titulo}")
    public ResponseEntity listarFilmesDaPesquisa(@PathVariable String titulo) throws IOException, InterruptedException {
       return ResponseEntity.ok().body(service.listarFilmesPesquisados(titulo));
    }

    // FAVORITAR
    @PostMapping("/favoritar/{id}")
    public ResponseEntity favoritarFilmes(@PathVariable Integer id) {
       service.favoritarFilmes(id);
       return ResponseEntity.status(201).body("Adicionado aos favoritos");
    }

    // LISTAR FILMES FAVORITOS
    @GetMapping("/lista/favoritos")
    public ResponseEntity listarFilmesFavoritados() {
       return ResponseEntity.ok().body(service.listarFilmesFavoritos());
    }

    // ESCOLHER FILME ALEATORIO
    @GetMapping("/favoritos/random")
    public ResponseEntity filmeAleatorio() {
       return ResponseEntity.ok().body(service.escolherAleatorio());
    }

    // DELETAR FILME DA LISTA DE ALEATORIOS
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deletarFavorito(@PathVariable Integer id) {
       return ResponseEntity.status(204).body(service.deletarFilme(id));
    }

}
