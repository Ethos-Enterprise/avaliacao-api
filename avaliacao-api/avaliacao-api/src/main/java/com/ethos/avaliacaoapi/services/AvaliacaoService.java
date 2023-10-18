package com.ethos.avaliacaoapi.services;

import com.ethos.avaliacaoapi.controller.request.AvaliacaoRequest;
import com.ethos.avaliacaoapi.controller.response.AvaliacaoResponse;
import com.ethos.avaliacaoapi.exception.AvaliacaoNaoExisteException;
import com.ethos.avaliacaoapi.mapper.AvaliacaoEntityMapper;
import com.ethos.avaliacaoapi.mapper.AvaliacaoMapper;
import com.ethos.avaliacaoapi.mapper.AvaliacaoResponseMapper;
import com.ethos.avaliacaoapi.model.Avaliacao;
import com.ethos.avaliacaoapi.repository.AvaliacaoRepository;
import com.ethos.avaliacaoapi.repository.entity.AvaliacaoEntity;
import com.ethos.avaliacaoapi.services.arquivo.ListaObj;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    @Autowired
    private final AvaliacaoRepository repository;
    private final AvaliacaoMapper avaliacaoModelMapper;
    private final AvaliacaoEntityMapper avaliacaoEntityMapper;
    private final AvaliacaoResponseMapper avaliacaoResponseMapper;

    public AvaliacaoResponse postAvaliacao(AvaliacaoRequest request) {
        Avaliacao model = avaliacaoModelMapper.from(request);
        AvaliacaoEntity entity = avaliacaoEntityMapper.from(model);
        AvaliacaoEntity savedEntity = saveAvaliacao(entity);
        return avaliacaoResponseMapper.from(savedEntity);
    }


    private AvaliacaoEntity saveAvaliacao(AvaliacaoEntity entity) {
        AvaliacaoEntity avaliacaoSaved = null;
        avaliacaoSaved = repository.save(entity);
        return avaliacaoSaved;
    }

    public List<AvaliacaoResponse> getAllAvaliacao() {
        List<AvaliacaoEntity> avaliacoes = (repository.findAll());
        return avaliacoes.stream().map(avaliacaoResponseMapper::from).toList();
    }

    public AvaliacaoResponse getAvaliacaoById(UUID id) {
        Optional<AvaliacaoEntity> avaliacao = repository.findById(id);
        if (avaliacao.isEmpty()) {
            throw new AvaliacaoNaoExisteException("Avaliação com id %s não existe".formatted(id.toString()));
        }
        return avaliacao.map(avaliacaoResponseMapper::from).get();
    }

    public AvaliacaoResponse putAvaliacaoById(UUID id, AvaliacaoRequest request) {

        if (!repository.existsById(id)) {
            throw new AvaliacaoNaoExisteException("Avaliação com o id %s não existe".formatted(id));
        }
        if (request.comentario() != null && !request.comentario().isEmpty()) {
            repository.updateComentario(request.comentario(), id);
        }
        if (request.nota() >= 1 || request.nota() <= 5) {
            repository.updateNota(request.nota(), id);
        }

        if (request.data() != null) {
            repository.updateData(request.data(), id);
        }

        return repository.findById(id).map(avaliacaoResponseMapper::from).get();
    }


    public String deleteAvaliacaoById(UUID id) {
        Optional<AvaliacaoEntity> avaliacoes = this.repository.findById(id);
        if (avaliacoes.isEmpty()) {
            throw new AvaliacaoNaoExisteException("Avaliação com o id %s não existe".formatted(id));
        } else {
            this.repository.deleteById(id);
            return "Avaliação deletada com sucesso";
        }
    }


    public List<AvaliacaoResponse> getAvaliacaoNota(Integer nota) {
        List<AvaliacaoEntity> avaliacoes = repository.findByNota(nota);
        return avaliacoes.stream().map(avaliacaoResponseMapper::from).toList();
    }

    public List<AvaliacaoResponse> getAvaliacaoComentario(String comentario) {
        List<AvaliacaoEntity> avaliacoes = repository.findByComentario(comentario);
        return avaliacoes.stream().map(avaliacaoResponseMapper::from).toList();
    }

    public List<AvaliacaoResponse> getAvaliacaoData(LocalDate data) {
        List<AvaliacaoEntity> avaliacoes = repository.findByData(data);
        return avaliacoes.stream().map(avaliacaoResponseMapper::from).toList();
    }

    public List<AvaliacaoResponse> getAvaliacaoFkEmpresa(UUID fk) {
        List<AvaliacaoEntity> avaliacoes = repository.findByFkEmpresa(fk);
        return avaliacoes.stream().map(avaliacaoResponseMapper::from).toList();
    }

    public List<AvaliacaoResponse> getAvaliacaoFkServico(UUID fk) {
        List<AvaliacaoEntity> avaliacoes = repository.findByFkServico(fk);
        return avaliacoes.stream().map(avaliacaoResponseMapper::from).toList();
    }

    public void gerarListaObj() {
        List<AvaliacaoEntity> avaliacoes = repository.findAll();
        ListaObj<AvaliacaoEntity> listaObj = new ListaObj<>(avaliacoes.size());

        for (AvaliacaoEntity avaliacao : avaliacoes) {
            listaObj.adiciona(avaliacao);
        }

        ordenaListaObj(listaObj);
    }

    private void ordenaListaObj(ListaObj<AvaliacaoEntity> listaObj) {
        for (int i = 0; i < listaObj.getTamanho() - 1; i++) {
            int indMenor = i;
            for (int j = i + 1; j < listaObj.getTamanho(); j++) {
                AvaliacaoEntity avaliacaoI = listaObj.getElemento(indMenor);
                AvaliacaoEntity avaliacaoJ = listaObj.getElemento(j);

                if (avaliacaoJ.getNota() < avaliacaoI.getNota()) {
                    indMenor = j;
                }
            }

            if (indMenor != i) {
                listaObj.trocaElementos(i, indMenor);
            }
        }
        gravaArquivoCsv(listaObj, "Avaliacoes");
    }


    public static void gravaArquivoCsv(ListaObj<AvaliacaoEntity> lista, String nomeArq) {
        FileWriter arq = null;
        Formatter saida = null;
        Boolean deuRuim = false;

        nomeArq += ".csv";

        // Bloco try-catch para abrir o arquivo:
        try {
            arq = new FileWriter(nomeArq);
            saida = new Formatter(arq);
        } catch (IOException erro) {
            System.out.println("Erro ao abrir o arquivo");
            System.exit(1);
        }

        // Bloco try-catch para gravar o arquivo
        try {
            for (int i = 0; i < lista.getTamanho(); i++) {
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
        } catch (FormatterClosedException erro) {
            System.out.println("Erro ao gravar o arquivo");
            erro.printStackTrace();
            deuRuim = true;
        } finally {
            saida.close();
            try {
                arq.close();
            } catch (IOException erro) {
                System.out.println("Erro ao fechar o arquivo");
                erro.printStackTrace();
                deuRuim = true;
            }

            if (deuRuim) {
                System.exit(1);
            }
        }
    }


}
