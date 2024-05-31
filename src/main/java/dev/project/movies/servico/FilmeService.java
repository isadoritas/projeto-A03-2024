package dev.project.movies.servico;

import dev.project.movies.algoritmos.BubbleSort;
import dev.project.movies.algoritmos.QuickSort;
import dev.project.movies.infra.exception.ValidacaoError;
import dev.project.movies.model.Filme;
import dev.project.movies.model.FilmesFavoritos;
import dev.project.movies.model.Results;
import dev.project.movies.repository.FilmesFavoritosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import dev.project.movies.repository.FilmeRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository repositorio;
    @Autowired
    private FilmesFavoritosRepository favRepository;

    private ObterJson buscar = new ObterJson();
    private ExtrairJson comsumir = new ExtrairJson();
    private QuickSort quickSort = new QuickSort();
    private BubbleSort bubbleSort = new BubbleSort();
    private Random random = new Random();

    // CONSTRUTOR
    public FilmeService(FilmeRepository repositorio) {
        this.repositorio = repositorio;
    }



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

            if (f.getPoster() == null) {
                f.setPoster(null);
            } else {
                f.setPoster("https://image.tmdb.org/t/p/w500/" + f.getPoster());
            }

            var filmeExiste = repositorio.findBySinopse(f.getSinopse());
            if (filmeExiste.isEmpty()) {
                repositorio.save(f);
            }
        }
        return listaFilmes;
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

            } else  {
                f.setClassificacaoIndicativa("+18");
            }

            if (f.getPoster() == null) {
                f.setPoster(null);
            } else {
                f.setPoster("https://image.tmdb.org/t/p/w500/" + f.getPoster());
            }

            var filmeExiste = repositorio.findBySinopse(f.getSinopse());
            if (filmeExiste.isEmpty()) {
                repositorio.save(f);
            }

        }
        return listaFilmesPesquisados;
    }


    // Ordenar por titulos
    public List<Filme> ordenarTitulos() {
        var lista = listarFilmesBanco();
        quickSort.quickSort(lista);
        return lista;
    }

    // Ordenar por avaliacao
    public List<Filme> ordenarAvaliacao() {
        var lista = listarFilmesBanco();
        bubbleSort.bubbleSort(lista);
        return lista;
    }

    // Filmes salvos
    public List<Filme> listarFilmesBanco() {
        var lista = repositorio.findAll();
        return lista;
    }

    // Favoritar Filmes
    public void favoritarFilmes(Integer id) {
        var filmeBanco = repositorio.findById(id);
        if (filmeBanco.isPresent()) {
            var filmeExiste = filmeBanco.get();
            FilmesFavoritos filme = new FilmesFavoritos(filmeExiste);
            favRepository.save(filme);
        }
    }

    // Listar filmes favoritos
    public List<FilmesFavoritos> listarFilmesFavoritos() {
        return favRepository.findAll();
    }

    
    // Pegar um filme aleatório
    public FilmesFavoritos escolherAleatorio() {
        var lista = listarFilmesFavoritos();
        return lista.stream()
                .skip(random.nextInt(lista.size()))
                .findFirst()
                .orElse(null);
    }

    // Deletar um filme da lista de favoritos
    public FilmesFavoritos deletarFilme(Integer id) {
        var filme = favRepository.findById(id);
        if (filme.isPresent()) {
            FilmesFavoritos filmesDeletado = filme.get();
            favRepository.deleteById(id);
            return filmesDeletado;
        }
        throw new ValidacaoError("Filme não encontrado");
    }


}
