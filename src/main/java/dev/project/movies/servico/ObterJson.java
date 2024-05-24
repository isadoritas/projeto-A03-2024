package dev.project.movies.servico;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ObterJson {

    public String obterDados(String endereco) throws IOException, InterruptedException {

        //https://api.themoviedb.org/3/search/movie?api_key=262d9d3f4737e9758af9e6d47c1168a6&query=batman
        //https://api.themoviedb.org/3/discover/movie?with_genres=16&api_key=262d9d3f4737e9758af9e6d47c1168a6

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();
        HttpResponse<String> response = null;

            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        return json;
    }
}
