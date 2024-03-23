package com.ethos.avaliacaoapi.controller;

import com.ethos.avaliacaoapi.controller.request.AvaliacaoRequest;
import com.ethos.avaliacaoapi.controller.response.AvaliacaoResponse;
import com.ethos.avaliacaoapi.repository.entity.AvaliacaoEntity;
import com.ethos.avaliacaoapi.services.AvaliacaoService;
import com.ethos.avaliacaoapi.services.arquivo.ListaObj;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1.0/avaliacoes")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AvaliacaoController {

    @Autowired
    public final AvaliacaoService avaliacaoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AvaliacaoResponse postAvaliacao(@RequestBody @Valid AvaliacaoRequest request) {
        return avaliacaoService.postAvaliacao(request);
    }

    @GetMapping
    public List<AvaliacaoResponse> getAllAvaliacao(

            @RequestParam(value = "comentario", required = false) String comentario,
            @RequestParam(value = "nota", required = false) Integer nota,
            @RequestParam(value = "fkEmpresa", required = false) UUID fkEmpresa,
            @RequestParam(value = "fkServico", required = false) UUID fkServico) {

        if (comentario != null) {
            return avaliacaoService.getAvaliacaoComentario(comentario);
        } else if (nota != null) {
            return avaliacaoService.getAvaliacaoNota(nota);
        } else if (fkEmpresa != null) {
            return avaliacaoService.getAvaliacaoFkEmpresa(fkEmpresa);
        } else if (fkServico != null) {
            return avaliacaoService.getAvaliacaoFkServico(fkServico);
        }
        return avaliacaoService.getAllAvaliacao();
    }

    @GetMapping("/servico/{fkServico}")
    public List<AvaliacaoResponse> getAvaliacaoByFkAvaliacao(@PathVariable UUID fkServico){
        return avaliacaoService.getAvaliacaoByFkServico(fkServico);
    }


    @GetMapping("/{id}")
    public AvaliacaoResponse getAvaliacaoById(@PathVariable UUID id) {
        return avaliacaoService.getAvaliacaoById(id);
    }

    @PutMapping("/{id}")
    public AvaliacaoResponse putAvaliacaoById(@PathVariable UUID id, @RequestBody AvaliacaoRequest request) {
        return avaliacaoService.putAvaliacaoById(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteAvaliacaoById(@PathVariable UUID id) {
        return avaliacaoService.deleteAvaliacaoById(id);
    }

    @PostMapping("/historico")
    public void postHistorico() {
        avaliacaoService.gerarListaObj();
    }

    @GetMapping("/data")
    public int getAvaliacaoDataOrdenada(@RequestParam LocalDate data) {
        ListaObj<AvaliacaoEntity> listaObj = avaliacaoService.gerarListaObj();
        ListaObj<AvaliacaoEntity> listaObjOrdenada = avaliacaoService.ordenaDataListaObj(listaObj);
        return avaliacaoService.pesquisaBinaria(listaObjOrdenada, data);
    }

}
