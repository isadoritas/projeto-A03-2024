package dev.project.movies.algoritmos;

import dev.project.movies.model.Filme;

import java.util.List;

public class BubbleSort {


    public void bubbleSort(List<Filme> lista) {
        List<Filme> filmes = retirarNull(lista);
        int n = filmes.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (filmes.get(j).getAvaliacao() < filmes.get(j + 1).getAvaliacao()) {
                    trocar(filmes, j, j + 1);
                }
            }
        }
    }

    public void trocar(List<Filme> filmes, int i, int j) {
        Filme temp = filmes.get(i);
        filmes.set(i, filmes.get(j));
        filmes.set(j, temp);
    }

    public List<Filme> retirarNull(List<Filme> filmesOriginal) {
        for (int i = 0; i < filmesOriginal.size()-1; i++) {
            if (filmesOriginal.get(i).getAvaliacao() == null) {
                filmesOriginal.remove(filmesOriginal.get(i));
            }
        }
        return filmesOriginal;
    }
}
