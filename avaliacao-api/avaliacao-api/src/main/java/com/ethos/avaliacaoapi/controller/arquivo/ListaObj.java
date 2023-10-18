  package com.ethos.avaliacaoapi.controller.arquivo;

import com.ethos.avaliacaoapi.model.Avaliacao;
import com.ethos.avaliacaoapi.repository.entity.AvaliacaoEntity;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ListaObj <T>{
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
        }
        else {
            vetor[nroElem++] = elemento;
        }
    }

    public void exibe() {
        if (nroElem == 0) {
            System.out.println("\nA lista está vazia.");
        }
        else {
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

    public boolean removePeloIndice (int indice) {
        if (indice < 0 || indice >= nroElem) {
            System.out.println("\nIndice invalido!");
            return false;
        }

        for (int i = indice; i < nroElem-1; i++) {
            vetor[i] = vetor[i+1];
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
        }
        else {
            return vetor[indice];
        }
    }

    public void limpa() {
        nroElem = 0;
    }


    public static void gravaArquivoCsv(ListaObj<AvaliacaoEntity> lista, String nomeArq){
        FileWriter arq = null; // representa o arquivo que será gravado
        Formatter saida = null; // objeto saída será usado para escrever no arquivo
        Boolean deuRuim = false;

        nomeArq += ".csv"; // concatena a extensão .csv ao nome do arquivo original

        // Bloco try-catch para abrir o arquivo:
        try {
            arq = new FileWriter(nomeArq); // abre o arquivo
            saida = new Formatter(arq); // instancia o obj saída, associando ao arquivo
        }
        catch (IOException erro){
            System.out.println("Erro ao abrir o arquivo");
            System.exit(1);
        }

        // Bloco try-catch para gravar o arquivo
        try {
            for (int i = 0; i < lista.getTamanho(); i++){
                AvaliacaoEntity avaliacao = lista.getElemento(i);

                saida.format("%s;%s;%d;%s;%s;%s\n",
                        avaliacao.getId(),
                        avaliacao.getComentario(),
                        avaliacao.getNota(),
                        avaliacao.getData(),
                        avaliacao.getFkEmpresa(),
                        avaliacao.getFkServico()
                );
            }
        }
        catch (FormatterClosedException erro){
            System.out.println("Erro ao gravar o arquivo");
            erro.printStackTrace();
            deuRuim = true;
        }
        finally {
            saida.close();
            try {
                arq.close();
            }
            catch (IOException erro){
                System.out.println("Erro ao fechar o arquivo");
                erro.printStackTrace();
                deuRuim = true;
            }

            if (deuRuim){
                System.exit(1);
            }
        }
    }

    public static void leExibeArquivoCsv(String nomeArq){
        FileReader arq = null; // representa o arquivo a ser lido
        Scanner entrada = null; // objeto usadp para ler o arquivo
        Boolean deuRuim = false;

        // Acrescenta a extensão .csv ao nome do arquivo
        nomeArq += ".csv";

        // Bloco try-catch para abrir o arquivo
        try {
            arq = new FileReader(nomeArq);
            entrada = new Scanner(arq).useDelimiter(";|\\n");
        }
        catch (FileNotFoundException erro){
            System.out.println("Arquivo inexistente");
            System.exit(1);
        }

        // Bloco try-catch para ler o arquivo
        try {
            // Exibe a linha de títulos das colunas no console
            System.out.printf("%40S %30S %3S %10S %40S %40S\n", "id", "comentario", "nota", "data", "fkEmpresa", "fkServico");

            while (entrada.hasNext()){
                int id = entrada.nextInt();
                String comentario = entrada.next();
                String nota = entrada.next();
                String data = entrada.next();
                String fkEmpresa = entrada.next();
                String fkServico = entrada.next();

                System.out.printf("%40s %30s %3d %10s %40s %40s\n", id, comentario, nota, data, fkEmpresa, fkServico);
            }
        }
        catch (NoSuchElementException erro) {
            System.out.println("Arquivo com problemas");
            erro.printStackTrace();
            deuRuim = true;
        }
        catch (IllegalStateException erro) {
            System.out.println("Erro na leitura do arquivo");
            erro.printStackTrace();
            deuRuim = true;
        }
        finally {
            entrada.close();
            try {
                arq.close();
            }
            catch (IOException erro){
                System.out.println("Erro ao fechar o arquivo");
                erro.printStackTrace();
                deuRuim = true;
            }

            if (deuRuim){
                System.exit(1);
            }
        }
    }
}
