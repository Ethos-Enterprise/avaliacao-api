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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        List<AvaliacaoEntity> avaliacao = (repository.findAll());
        return avaliacao.stream().map(avaliacaoResponseMapper::from).toList();
    }

    public AvaliacaoResponse getAvaliacaoById(UUID id) {
        Optional<AvaliacaoEntity> avaliacao = repository.findById(id);
        if (avaliacao.isEmpty()) {
            throw new AvaliacaoNaoExisteException("Avaliação com id %s não existe".formatted(id.toString()));
        }
        return avaliacao.map(avaliacaoResponseMapper::from).get();
    }

    public AvaliacaoResponse putAvaliacaoById(UUID id, AvaliacaoRequest request) {
        Optional<AvaliacaoEntity> entity;
        entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new AvaliacaoNaoExisteException("Avaliação com o id %s não existe".formatted(id));
        }
        if (request.comentario() != null && !request.comentario().isEmpty()) {
            repository.updateComentario(request.comentario(), id);
        }
        if (request.nota() < 1 || request.nota() > 5) {
            repository.updateNota(request.nota(), id);
        }

        if (request.data() != null) {
            repository.updateData(request.data(), id);
        }

        return repository.findById(id).map(avaliacaoResponseMapper::from).get();
    }

    public String deleteAvaliacaoById(UUID id) {
        Optional<AvaliacaoEntity> avaliacao = repository.findById(id);
        if (avaliacao.isEmpty()) {
            throw new AvaliacaoNaoExisteException("Avaliação com o id %s não existe".formatted(id));
        }
        repository.deleteById(id);
        return "Avaliação deletado com sucesso";
    }

    public List<AvaliacaoResponse> getAvaliacaoNota(Integer nota) {
        List<AvaliacaoEntity> avaliacao = repository.findByNota(nota);
        return avaliacao.stream().map(avaliacaoResponseMapper::from).toList();
    }

    public List<AvaliacaoResponse> getAvaliacaoComentario(String comentario) {
        List<AvaliacaoEntity> avaliacao = repository.findByComentario(comentario);
        return avaliacao.stream().map(avaliacaoResponseMapper::from).toList();
    }

    public List<AvaliacaoResponse> getAvaliacaoData(LocalDate data) {
        List<AvaliacaoEntity> avaliacao = repository.findByData(data);
        return avaliacao.stream().map(avaliacaoResponseMapper::from).toList();
    }

    public List<AvaliacaoResponse> getAvaliacaoFkEmpresa(UUID fk) {
        List<AvaliacaoEntity> avaliacao = repository.findByFkEmpresa(fk);
        return avaliacao.stream().map(avaliacaoResponseMapper::from).toList();
    }

    public List<AvaliacaoResponse> getAvaliacaoFkPrestadoraServico(UUID fk) {
        List<AvaliacaoEntity> avaliacao = repository.findByFkPrestadoraServico(fk);
        return avaliacao.stream().map(avaliacaoResponseMapper::from).toList();
    }
}
