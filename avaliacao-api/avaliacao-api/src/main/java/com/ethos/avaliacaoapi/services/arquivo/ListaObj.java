package com.ethos.avaliacaoapi.services.arquivo;

import com.ethos.avaliacaoapi.repository.entity.AvaliacaoEntity;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ListaObj<T> {
    // Atributos
    private T[] vetor;
    private int nroElem;

    // Construtor
    public ListaObj(int tamanho) {
        vetor = (T[]) new Object[tamanho];   // Cria o vetor
        nroElem = 0;                         // Zera nroElem
    }

    // Metodos
    public void adiciona(T elemento) {
        if (nroElem >= vetor.length) {
            System.out.println("Lista está cheia");
        } else {
            vetor[nroElem++] = elemento;
        }
    }

    public void exibe() {
        if (nroElem == 0) {
            System.out.println("\nA lista está vazia.");
        } else {
            System.out.println("\nElementos da lista:");
            for (int i = 0; i < nroElem; i++) {
                System.out.println(vetor[i]);
            }
        }
    }

    public int busca(T elementoBuscado) {
        for (int i = 0; i < nroElem; i++) {
            if (vetor[i].equals(elementoBuscado)) {
                return i;
            }
        }
        return -1;
    }

    public boolean removePeloIndice(int indice) {
        if (indice < 0 || indice >= nroElem) {
            System.out.println("\nIndice invalido!");
            return false;
        }

        for (int i = indice; i < nroElem - 1; i++) {
            vetor[i] = vetor[i + 1];
        }

        nroElem--;
        return true;
    }

    public boolean removeElemento(T elementoARemover) {
        return removePeloIndice(busca(elementoARemover));
    }

    public int getTamanho() {
        return nroElem;
    }

    public T getElemento(int indice) {
        if (indice < 0 || indice >= nroElem) {
            return null;
        } else {
            return vetor[indice];
        }
    }

    public void limpa() {
        nroElem = 0;
    }

    public void trocaElementos(int indice1, int indice2) {
        if (indice1 < 0 || indice1 >= getTamanho() || indice2 < 0 || indice2 >= getTamanho()) {
            throw new IndexOutOfBoundsException("Índices fora dos limites da lista.");
        }

        T temp = vetor[indice1];
        vetor[indice1] = vetor[indice2];
        vetor[indice2] = temp;
    }

}
