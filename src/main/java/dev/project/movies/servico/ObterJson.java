package dev.project.movies.servico;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ObterJson {

    public String obterDados(String nomeFilme) throws IOException, InterruptedException {

        String endereco = "http://www.omdbapi.com/?t=" + nomeFilme.replace(" ", "+") + "&apikey=4a6ce6d9";

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
