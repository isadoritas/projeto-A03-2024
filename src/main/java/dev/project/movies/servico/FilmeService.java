package dev.project.movies.servico;

import dev.project.movies.exception.ValidacaoError;
import dev.project.movies.model.Filme;
import dev.project.movies.model.Results;
import org.springframework.beans.factory.annotation.Autowired;
import dev.project.movies.repository.FilmeRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository repositorio;

    private ObterJson buscar = new ObterJson();
    private ExtrairJson comsumir = new ExtrairJson();

    // CONSTRUTOR
    public FilmeService(FilmeRepository repositorio) {
        this.repositorio = repositorio;
    }

    // SERVICES


    // Filmes por Genero
    public List<Filme> listarFilmesPeloGenero(int genero) throws IOException, InterruptedException {

        // PEGANDO O JSON DA API E CONVERTENDO EM UMA LISTA DE DADOS FILMES
        String endereco = "https://api.themoviedb.org/3/discover/movie?with_genres="
                + genero +"&api_key=" + System.getenv("TMDB_API_KEY");
        var json = buscar.obterDados(endereco);
        var lista = comsumir.extrairDados(json, Results.class);


        // TRANSFORMANDO A LISTA DE DADOS FILMES EM OBJETO FILME
        List<Filme> listaFilmes = lista.listaDadosFilmes().stream()
                .map(d -> new Filme(d.titulo(), d.paraMaiores(), d.sinopse(),
                        d.avaliacao(), d.dataDeLancamento(), d.poster()))
                .collect(Collectors.toList());


        // ALTERANDO ALGUNS ATRIBUTOS E SALVANDO NO BANCO
        for (Filme f  : listaFilmes) {
            if (f.getClassificacaoIndicativa().equals("false")) {
                f.setClassificacaoIndicativa("Livre");

            } else if (f.getClassificacaoIndicativa().equals("true")) {
                f.setClassificacaoIndicativa("+18");
            }
            f.setPoster("https://image.tmdb.org/t/p/w500/" + f.getPoster());

            if (!repositorio.existsByTitulo(f.getTitulo())) {
                repositorio.save(f);
            }
        }
        return listaFilmes;
    }


    // Filmes salvos
    public List<Filme> listarFilmesBanco() {
        var lista = repositorio.findAll();
        return lista;
    }


    // Filme especifico
    public Filme detalharFilme(Integer id) {
        Optional<Filme> filme = repositorio.findById(id);
        Filme filmeEncontrado = null;
        if (filme.isPresent()) {
           filmeEncontrado = filme.get();
        } else {
            System.out.println("filme não encontrado");
        }
        return filmeEncontrado;
    }


    // Filmes Pesquisados
    public List<Filme> listarFilmesPesquisados(String titulo) throws IOException, InterruptedException {

        // PEGANDO O JSON DA API E CONVERTENDO EM UMA LISTA DE DADOS FILMES
        String endereco = "https://api.themoviedb.org/3/search/movie?api_key="
                + System.getenv("TMDB_API_KEY") + "&query=" + titulo.replace(" ", "+");
        var json = buscar.obterDados(endereco);
        var lista = comsumir.extrairDados(json, Results.class);


        // TRANSFORMANDO A LISTA DE DADOS FILMES EM OBJETO FILME
        List<Filme> listaFilmesPesquisados = lista.listaDadosFilmes().stream()
                .map(d-> new Filme(d.titulo(), d.paraMaiores(), d.sinopse(),
                        d.avaliacao(), d.dataDeLancamento(), d.poster()))
                .collect(Collectors.toList());


        // ALTERANDO ALGUNS ATRIBUTOS E SALVANDO NO BANCO
        for (Filme f  : listaFilmesPesquisados) {
            if (f.getClassificacaoIndicativa().equals("false")) {
                f.setClassificacaoIndicativa("Livre");

            } else if (f.getClassificacaoIndicativa().equals("true")) {
                f.setClassificacaoIndicativa("+18");
            }
            f.setPoster("https://image.tmdb.org/t/p/w500/" + f.getPoster());

            if (!repositorio.existsByTitulo(f.getTitulo())) {
                repositorio.save(f);
            }
        }
        return listaFilmesPesquisados;
    }
}
