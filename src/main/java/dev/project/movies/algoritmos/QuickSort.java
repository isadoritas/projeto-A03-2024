package dev.project.movies.algoritmos;

import dev.project.movies.model.Filme;

import java.util.List;

public class QuickSort {


    public void quickSort(List<Filme> filmes) {
        if (filmes == null || filmes.size() <= 1) {
            return; // Lista vazia ou com apenas um elemento, já está ordenada
        }
        quickSort(filmes, 0, filmes.size() - 1);
    }

    public void quickSort(List<Filme> filmes, int inicio, int fim) {
        if (inicio < fim) {
            int indicePivo = partition(filmes, inicio, fim);
            quickSort(filmes, inicio, indicePivo - 1);
            quickSort(filmes, indicePivo + 1, fim);
        }
    }

    public int partition(List<Filme> filmes, int inicio, int fim) {
        Filme pivo = filmes.get(fim);
        int i = inicio - 1;

        for (int j = inicio; j < fim; j++) {
            if (filmes.get(j).getTitulo().compareToIgnoreCase(pivo.getTitulo()) < 0) {
                i++;
                trocar(filmes, i, j);
            }
        }

        trocar(filmes, i + 1, fim);
        return i + 1;
    }

    public void trocar(List<Filme> filmes, int i, int j) {
        Filme temp = filmes.get(i);
        filmes.set(i, filmes.get(j));
        filmes.set(j, temp);
    }
}
