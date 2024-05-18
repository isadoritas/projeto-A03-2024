package dev.project.movies.principal;

import dev.project.movies.model.DadosFilmes;
import dev.project.movies.servico.ExtrairJson;
import dev.project.movies.servico.ObterJson;

import java.io.IOException;
import java.util.Scanner;

public class Principal {

    private Scanner sc = new Scanner(System.in);
    private ObterJson buscar = new ObterJson();
    private ExtrairJson comsumir = new ExtrairJson();


    public void exibirMenu() throws IOException, InterruptedException {
        System.out.println("\nDigite um filme:");
        var nomeFilme = sc.nextLine();

        var json = buscar.obterDados(nomeFilme);
        DadosFilmes dadosFilmes = comsumir.extrairDados(json, DadosFilmes.class);

        System.out.println(dadosFilmes);

    }
}
