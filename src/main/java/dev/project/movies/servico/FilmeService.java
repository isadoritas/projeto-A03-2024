package dev.project.movies.servico;

import dev.project.movies.algoritmos.BubbleSort;
import dev.project.movies.algoritmos.QuickSort;
import dev.project.movies.infra.exception.ValidacaoError;
import dev.project.movies.infra.security.SecurityFilter;
import dev.project.movies.infra.security.TokenService;
import dev.project.movies.model.Filme;
import dev.project.movies.model.FilmesFavoritos;
import dev.project.movies.model.Results;
import dev.project.movies.model.Usuario;
import dev.project.movies.repository.FilmesFavoritosRepository;
import dev.project.movies.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import dev.project.movies.repository.FilmeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository repositorio;
    @Autowired
    private FilmesFavoritosRepository favRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SecurityFilter securityFilter;

    private ObterJson buscar = new ObterJson();
    private ExtrairJson comsumir = new ExtrairJson();
    private QuickSort quickSort = new QuickSort();
    private BubbleSort bubbleSort = new BubbleSort();
    private Random random = new Random();


    // CONSTRUTOR
    public FilmeService(FilmeRepository repositorio) {
        this.repositorio = repositorio;
    }


    // Pegar token do cabeçalho Authorization
    public String processarComAuthorization() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        } else {
            throw new RuntimeException("Valor do cabeçalho é nulo");
        }
    }


    // Pegar id do usuario logado
    public Integer pegarIdUsuario() {
        var subject = tokenService.getSubject(processarComAuthorization());
        Usuario usuario = (Usuario) usuarioRepository.findByEmail(subject);
        var  usuarioID = usuario.getId();
        return usuarioID;
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
            if (filmeExiste.isEmpty() && f.getPoster() != null) {
                repositorio.save(f);
            }
        }
        List<Filme> listaInteira = repositorio.findAll();
        List<Filme> listaDeRetorno = new ArrayList<>();
        for (Filme f : listaInteira) {
            for (Filme fv : listaFilmes) {
                if(f.getPoster().equals(fv.getPoster())) {
                    listaDeRetorno.add(f);
                }
            }
        }
        return listaDeRetorno;
    }


    // Filmes Pesquisados
    public List<Filme> listarFilmesPesquisados(String titulo) throws IOException, InterruptedException {

        // PEGANDO O JSON DA API E CONVERTENDO EM UMA LISTA DE DADOS FILMES
        String endereco = "https://api.themoviedb.org/3/search/movie?api_key="
                + System.getenv("TMDB_API_KEY") + "&query=" + titulo.replace(" ", "+");
        var json = buscar.obterDados(endereco);
        var lista = comsumir.extrairDados(json, Results.class);

        if (lista.listaDadosFilmes().isEmpty()) {
            throw new RuntimeException("Filme não encontrado");
        }
        else {

            // TRANSFORMANDO A LISTA DE DADOS FILMES EM OBJETO FILME
            List<Filme> listaFilmesPesquisados = lista.listaDadosFilmes().stream()
                    .map(d -> new Filme(d.titulo(), d.paraMaiores(), d.sinopse(),
                            d.avaliacao(), d.dataDeLancamento(), d.poster()))
                    .collect(Collectors.toList());


            // ALTERANDO ALGUNS ATRIBUTOS E SALVANDO NO BANCO
            for (Filme f : listaFilmesPesquisados) {
                if (f.getClassificacaoIndicativa().equals("false")) {
                    f.setClassificacaoIndicativa("Livre");

                } else {
                    f.setClassificacaoIndicativa("+18");
                }

                if (f.getPoster() == null) {
                    f.setPoster(null);
                } else {
                    f.setPoster("https://image.tmdb.org/t/p/w500/" + f.getPoster());
                }

                var filmeExiste = repositorio.findBySinopse(f.getSinopse());
                if (filmeExiste.isEmpty() && f.getPoster() != null) {
                    repositorio.save(f);
                }

            }
            List<Filme> listaInteira = repositorio.findAll();
            List<Filme> listaDeRetorno = new ArrayList<>();
            for (Filme f : listaInteira) {
                for (Filme fv : listaFilmesPesquisados) {
                    if(f.getPoster().equals(fv.getPoster())) {
                        listaDeRetorno.add(f);
                    }
                }
            }
            return listaDeRetorno;
        }
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
        var listaSortida = lista.stream().collect(Collectors.toList());
        Collections.shuffle(listaSortida);
        return listaSortida;
    }


    // Favoritar Filmes
    public void favoritarFilmes(Integer id) {
        var filmeBanco = repositorio.findById(id);
        if (filmeBanco.isPresent()) {
            var filmeExiste = filmeBanco.get();
            FilmesFavoritos filme = new FilmesFavoritos(filmeExiste);
            Integer usuarioID = pegarIdUsuario();
            filme.setUsuarioId(usuarioID);
            favRepository.save(filme);
        }
    }


    // Listar filmes favoritos
    public List<FilmesFavoritos> listarFilmesFavoritos(Integer usuarioID) {
       usuarioID = pegarIdUsuario();
        return favRepository.findByUsuarioId(usuarioID);
    }


    // Pegar um filme aleatório
    public FilmesFavoritos escolherAleatorio() {
        Integer usuarioID = pegarIdUsuario();
        var lista = listarFilmesFavoritos(usuarioID);
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
