package dev.project.movies.controller;


import dev.project.movies.servico.FilmeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Comparator;


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
       var lista = service.listarFilmesBanco();
       var listaOrdenada = lista.stream()
               .sorted(Comparator.comparing(f->f.getTitulo()));
       return ResponseEntity.ok().body(listaOrdenada);
    }

    // AVALIACAO
    @GetMapping("/ordenar/avaliacao")
    public ResponseEntity listarFilmesPelaAvaliacao() {
        var lista = service.listarFilmesBanco();
        var listaOrdenada = lista.stream()
                .sorted(Comparator.comparing(f->f.getAvaliacao()));
        return ResponseEntity.ok().body(listaOrdenada);
    }

    // PESQUISA
    @GetMapping("/{titulo}")
    public ResponseEntity listarFilmesDaPesquisa(@PathVariable String titulo) throws IOException, InterruptedException {
       return ResponseEntity.ok().body(service.listarFilmesPesquisados(titulo));
    }

    // DETALHES FILME
    @GetMapping("/detalhes/{id}")
    public ResponseEntity detalharFilme(@PathVariable Integer id) {
       return ResponseEntity.ok().body(service.detalharFilme(id));
    }


}
