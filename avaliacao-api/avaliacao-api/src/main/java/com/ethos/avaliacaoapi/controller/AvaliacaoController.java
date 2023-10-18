package com.ethos.avaliacaoapi.controller;

import com.ethos.avaliacaoapi.controller.request.AvaliacaoRequest;
import com.ethos.avaliacaoapi.controller.response.AvaliacaoResponse;
import com.ethos.avaliacaoapi.services.AvaliacaoService;
import lombok.RequiredArgsConstructor;
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

    public final AvaliacaoService avaliacaoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AvaliacaoResponse postAvaliacao(@RequestBody AvaliacaoRequest request) {
        return avaliacaoService.postAvaliacao(request);
    }

    @GetMapping
    public List<AvaliacaoResponse> getAllAvaliacao(

            @RequestParam(value = "comentario", required = false) String comentario,
            @RequestParam(value = "nota", required = false) Integer nota,
            @RequestParam(value = "fkEmpresa", required = false)  UUID fkEmpresa,
            @RequestParam(value = "fkServico", required = false) UUID fkServico) {

        if (comentario != null) {
            return avaliacaoService.getAvaliacaoComentario(comentario);
        } else if (nota  != null ) {
            return avaliacaoService.getAvaliacaoNota(nota);
        }else if (fkEmpresa != null) {
            return avaliacaoService.getAvaliacaoFkEmpresa(fkEmpresa);
        } else if (fkServico != null) {
            return avaliacaoService.getAvaliacaoFkServico(fkServico);
        }
        return avaliacaoService.getAllAvaliacao();
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

}
